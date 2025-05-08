package com.example.tunehub.POJO;

import java.io.*;
import java.util.*;

public class PlaylistManager {

    private static final String FILE_NAME = "data/playlists.csv";  // Nome del file CSV

    // Metodo per caricare tutte le playlist dal file CSV
    public static List<Playlist> caricaPlaylist() throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");  // La prima colonna è il nome della playlist, le altre sono le canzoni
            Playlist playlist = new Playlist(data[0]);  // Nome della playlist
            // Aggiungi ciascuna canzone separando titolo e artista
            for (int i = 1; i < data.length; i += 2) { // Incremento di 2 per gestire titolo e artista
                String titolo = data[i];
                String artista = data[i + 1];
                playlist.aggiungiCanzone(titolo, artista);  // Aggiungi la canzone alla playlist
            }
            playlists.add(playlist);  // Aggiungi la playlist alla lista
        }
        reader.close();
        return playlists;
    }

    // Metodo per salvare tutte le playlist nel file CSV
    public static void salvaPlaylist(List<Playlist> playlists) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Playlist playlist : playlists) {
            writer.write(playlist.getNomePlaylist());  // Scrivi il nome della playlist
            for (String[] canzone : playlist.getListaCanzoni()) {
                writer.write("," + canzone[0] + "," + canzone[1]);  // Scrivi titolo e artista separati da virgola
            }
            writer.newLine();  // Vai alla riga successiva
        }
        writer.close();
    }

    // Metodo per aggiungere una canzone a una playlist esistente o crearne una nuova
    public static void aggiungiCanzoneAllaPlaylist(String nomePlaylist, String titolo, String artista) throws IOException {
        List<Playlist> playlists = caricaPlaylist();  // Carica le playlist esistenti dal CSV
        boolean playlistTrovata = false;
        for (Playlist playlist : playlists) {
            if (playlist.getNomePlaylist().equals(nomePlaylist)) {
                playlist.aggiungiCanzone(titolo, artista);  // Aggiungi la canzone alla playlist trovata
                playlistTrovata = true;
                break;
            }
        }

        // Se la playlist non esiste, crea una nuova playlist e aggiungi la canzone
        if (!playlistTrovata) {
            Playlist newPlaylist = new Playlist(nomePlaylist);
            newPlaylist.aggiungiCanzone(titolo, artista);  // Aggiungi la canzone alla nuova playlist
            playlists.add(newPlaylist);  // Aggiungi la nuova playlist alla lista
        }

        // Salva le playlist aggiornate nel file CSV
        salvaPlaylist(playlists);
    }

    // Metodo per ottenere una playlist specifica dal nome
    public static Playlist ottieniPlaylist(String nomePlaylist) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        for (Playlist playlist : playlists) {
            if (playlist.getNomePlaylist().equals(nomePlaylist)) {
                return playlist;
            }
        }
        return null;  // Se non trovata, restituisce null
    }
}
