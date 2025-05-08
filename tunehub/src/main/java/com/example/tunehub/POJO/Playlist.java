package com.example.tunehub.POJO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private List<String[]> listaCanzoni;  // Lista di array di stringhe, ognuna contenente titolo e artista
    private String nomePlaylist; // Nome della playlist

    // Costruttore che inizializza la lista e il nome della playlist
    public Playlist() {
        this.nomePlaylist = "";
        this.listaCanzoni = new ArrayList<>(); // Inizializza la lista come ArrayList
    }

    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.listaCanzoni = new ArrayList<>();  // Crea una nuova lista vuota
    }

    // Metodo per aggiungere una canzone alla playlist
    public void aggiungiCanzone(String titolo, String artista) {
        listaCanzoni.add(new String[]{titolo, artista});  // Aggiungi un array con titolo e artista
    }

    // Metodo per rimuovere una canzone dalla playlist
    public void rimuoviCanzone(String titolo, String artista) {
        for (String[] canzone : listaCanzoni) {
            if (canzone[0].equals(titolo) && canzone[1].equals(artista)) {
                listaCanzoni.remove(canzone);  // Rimuovi la canzone dalla lista
                return;
            }
        }
        System.out.println("Canzone non trovata nella playlist.");
    }

    // Metodo per visualizzare la playlist (titoli delle canzoni e artisti)
    public String visualizzaPlaylist() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist: ").append(nomePlaylist).append("\n");
        for (String[] canzone : listaCanzoni) {
            sb.append(canzone[0]).append(" - ").append(canzone[1]).append(" -> ");
        }
        return sb.append("null").toString();
    }

    // Getter e Setter per nomePlaylist
    public String getNomePlaylist() {
        return nomePlaylist;
    }

    public void setNomePlaylist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
    }

    // Getter per la lista di canzoni
    public List<String[]> getListaCanzoni() {
        return listaCanzoni;
    }

    // Metodo per caricare le playlist dal file CSV
    public static List<Playlist> caricaPlaylist() throws IOException {
        List<Playlist> playlists = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("data/playlists.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Playlist playlist = new Playlist(data[0]);  // Nome della playlist
            for (int i = 1; i < data.length; i += 2) {
                playlist.aggiungiCanzone(data[i], data[i + 1]);  // Aggiungi titolo e artista della canzone
            }
            playlists.add(playlist);  // Aggiungi la playlist alla lista
        }
        reader.close();
        return playlists;
    }

    // Metodo per salvare le playlist nel file CSV
    public static void salvaPlaylist(List<Playlist> playlists) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/playlists.csv"));
        for (Playlist playlist : playlists) {
            writer.write(playlist.getNomePlaylist());  // Scrivi il nome della playlist
            for (String[] canzone : playlist.getListaCanzoni()) {
                writer.write("," + canzone[0] + "," + canzone[1]);  // Scrivi titolo e artista separati da virgola
            }
            writer.newLine();  // Vai alla riga successiva
        }
        writer.close();
    }

    // Metodo per aggiungere una canzone a una playlist e salvarla nel CSV
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
}
