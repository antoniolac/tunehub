package com.example.tunehub.POJO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    private static final String CSV_FILE = "data/playlists.csv";

    public static List<Playlist> caricaPlaylist() throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        File file = new File(CSV_FILE);

        if (!file.exists()) {
            return playlists;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 1) {
                    Playlist p = new Playlist(parts[0]);
                    for (int i = 1; i < parts.length; i++) {
                        p.aggiungiCanzone(parts[i]);
                    }
                    playlists.add(p);
                }
            }
        }
        return playlists;
    }

    public static void salvaPlaylist(List<Playlist> playlists) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE, StandardCharsets.UTF_8))) {
            for (Playlist p : playlists) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getNomePlaylist());
                for (String canzone : p.getListaCanzoni()) {
                    sb.append(";").append(canzone);
                }
                pw.println(sb.toString());
            }
        }
    }

    public static void aggiungiCanzoneAllaPlaylist(String nomePlaylist, String canzone, String artista) throws IOException {
        List<Playlist> playlists = caricaPlaylist();
        boolean trovata = false;

        for (Playlist p : playlists) {
            if (p.getNomePlaylist().equals(nomePlaylist)) {
                String entry = canzone + " - " + artista;
                if (!p.getListaCanzoni().contains(entry)) {
                    p.aggiungiCanzone(entry);
                }
                trovata = true;
                break;
            }
        }

        if (!trovata) {
            throw new IOException("Playlist non trovata");
        }

        salvaPlaylist(playlists);
    }
}