package com.example.tunehub.POJO;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlaylistManager {

    private static final String FILE_PATH = "playlists.csv";

    public static List<Playlist> caricaPlaylist() throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Se il file non esiste, lo crea
        if (!file.exists()) {
            file.createNewFile();
            return playlists;
        }

        // Se il file è vuoto, restituisce una lista vuota
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

                // Aggiungi canzoni solo se ce ne sono
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

    public static void aggiungiCanzoneAllaPlaylist(String nomePlaylist, String trackName, String trackArtist) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        Playlist playlist = playlists.stream()
                .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                .findFirst()
                .orElseThrow(() -> new IOException("Playlist non trovata"));

        playlist.getListaCanzoni().add(trackName + " - " + trackArtist);
        salvaPlaylist(playlists);
    }

    public static void rimuoviCanzoneDallaPlaylist(String nomePlaylist, String trackName) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        Playlist playlist = playlists.stream()
                .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                .findFirst()
                .orElseThrow(() -> new IOException("Playlist non trovata"));

        playlist.getListaCanzoni().remove(trackName);
        salvaPlaylist(playlists);
    }

    public static void eliminaPlaylist(String nomePlaylist) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        boolean removed = playlists.removeIf(p -> p.getNomePlaylist().equals(nomePlaylist));

        if (!removed) {
            throw new IOException("Playlist non trovata");
        }

        salvaPlaylist(playlists);
    }
}