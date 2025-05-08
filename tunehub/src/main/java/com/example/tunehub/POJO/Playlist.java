package com.example.tunehub.POJO;

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
}
