package com.example.tunehub.POJO;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private List<Song> listaCanzoni;  // Lista di canzoni che rappresenta la playlist
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
    public void aggiungiCanzone(Song song) {
        listaCanzoni.add(song);  // Aggiungi la canzone alla lista
    }

    // Metodo per rimuovere una canzone dalla playlist
    public void rimuoviCanzone(Song song) {
        if (listaCanzoni.contains(song)) {
            listaCanzoni.remove(song);  // Rimuovi la canzone dalla lista
        } else {
            System.out.println("Canzone non trovata nella playlist.");
        }
    }

    // Metodo per visualizzare la playlist (titoli delle canzoni)
    public String visualizzaPlaylist() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist: ").append(nomePlaylist).append("\n");
        for (Song song : listaCanzoni) {
            sb.append(song.getTitle()).append(" -> ");
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
    public List<Song> getListaCanzoni() {
        return listaCanzoni;
    }
}
