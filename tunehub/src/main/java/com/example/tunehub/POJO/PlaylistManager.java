package com.example.tunehub.POJO;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlaylistManager {

    private static final String FILE_PATH = "playlists.csv";

    public static List<Playlist> caricaPlaylist() throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String nomePlaylist = parts[0];
            List<String> canzoni = Arrays.stream(parts, 1, parts.length).collect(Collectors.toList());
            Playlist playlist = new Playlist(nomePlaylist);
            playlist.setListaCanzoni(canzoni);
            playlists.add(playlist);
        }

        reader.close();
        return playlists;
    }

    public static void salvaPlaylist(List<Playlist> playlists) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        for (Playlist playlist : playlists) {
            String playlistLine = playlist.getNomePlaylist() + "," +
                    String.join(",", playlist.getListaCanzoni());
            writer.write(playlistLine);
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

        playlist.getListaCanzoni().removeIf(canzone -> canzone.equals(trackName));
        salvaPlaylist(playlists);
    }
}
