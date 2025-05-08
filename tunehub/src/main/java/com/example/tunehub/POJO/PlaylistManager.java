package com.example.tunehub.POJO;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/*
    Classe che gestisce salvataggio modifiche su file CSV
    tutti i metodi servono per interagire con il file
*/

public class PlaylistManager {

    private static final String FILE_PATH = "playlists.csv";

    //metodo che crea nuova playlist
    public static List<Playlist> caricaPlaylist() throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        File file = new File(FILE_PATH);

        //Se il file non esiste, lo crea
        if (!file.exists()) {
            file.createNewFile();
            return playlists;
        }

        if (file.length() == 0) {
            return playlists;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                String[] parts = line.split(",");
                String nomePlaylist = parts[0];
                List<String> canzoni = new ArrayList<>();

                if (parts.length > 1) {
                    canzoni = Arrays.stream(parts, 1, parts.length).collect(Collectors.toList());
                }

                Playlist playlist = new Playlist(nomePlaylist);
                playlist.setListaCanzoni(canzoni);
                playlists.add(playlist);
            }
        }

        reader.close();
        return playlists;
    }
    //metodo che salva la playlist
    public static void salvaPlaylist(List<Playlist> playlists) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        for (Playlist playlist : playlists) {
            if (playlist.getListaCanzoni().isEmpty()) {
                // Se la playlist è vuota, salva solo il nome
                writer.write(playlist.getNomePlaylist());
            } else {
                // Altrimenti salva nome e canzoni
                String playlistLine = playlist.getNomePlaylist() + "," +
                        String.join(",", playlist.getListaCanzoni());
                writer.write(playlistLine);
            }
            writer.newLine();
        }
        writer.close();
    }
    //metodo che aggiunge canzone
    public static void aggiungiCanzoneAllaPlaylist(String nomePlaylist, String trackName, String trackArtist) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        Playlist playlist = playlists.stream()
                .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                .findFirst()
                .orElseThrow(() -> new IOException("Playlist non trovata"));

        playlist.getListaCanzoni().add(trackName + " - " + trackArtist);
        salvaPlaylist(playlists);
    }

    //metodo che rimuove canzone
    public static void rimuoviCanzoneDallaPlaylist(String nomePlaylist, String trackName) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        Playlist playlist = playlists.stream()
                .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                .findFirst()
                .orElseThrow(() -> new IOException("Playlist non trovata"));

        playlist.getListaCanzoni().remove(trackName);
        salvaPlaylist(playlists);
    }

    //metodo che elimina playlist
    public static void eliminaPlaylist(String nomePlaylist) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        boolean removed = playlists.removeIf(p -> p.getNomePlaylist().equals(nomePlaylist));

        if (!removed) {
            throw new IOException("Playlist non trovata");
        }

        salvaPlaylist(playlists);
    }
}