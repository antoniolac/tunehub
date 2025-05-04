package com.example.tunehub.POJO;


public class Playlist {
    private Lista listaCanzoni;  // Lista di canzoni che rappresenta la playlist
    private String nomePlaylist; // Nome della playlist

    // Costruttore che inizializza la lista e il nome della playlist
    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.listaCanzoni = new Lista();  // Crea una nuova lista vuota
    }

    // Metodo per aggiungere una canzone alla playlist
    public void aggiungiCanzone(Song song) {
        listaCanzoni.inserisciInCoda(song);  // Aggiungi la canzone in coda alla playlist
    }

    // Metodo per rimuovere una canzone dalla playlist
    public void rimuoviCanzone(Song song) {
        Nodo corrente = listaCanzoni.getTesta();
        Nodo precedente = null;

        // Scorri la lista per trovare la canzone da rimuovere
        while (corrente != null) {
            if (corrente.getInfo().equals(song)) {
                if (precedente == null) {
                    listaCanzoni.cancInTesta();  // Se la canzone è in testa
                } else {
                    precedente.setLink(corrente.getLink());  // Se la canzone non è in testa
                }
                return;
            }
            precedente = corrente;
            corrente = corrente.getLink();
        }
        System.out.println("Canzone non trovata nella playlist.");
    }

    // Metodo per visualizzare la playlist (titoli delle canzoni)
    public String visualizzaPlaylist() {
        return "Playlist: " + nomePlaylist + "\n" + listaCanzoni.elencoNodi();
    }

    // Getter e Setter per nomePlaylist
    public String getNomePlaylist() {
        return nomePlaylist;
    }

    public void setNomePlaylist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
    }

    // Getter per la lista di canzoni
    public Lista getListaCanzoni() {
        return listaCanzoni;
    }
}

