package com.example.tunehub.POJO;

import java.util.ArrayList;
import java.util.List;

/*
    Classe che gestisce playlist
*/
public class Playlist {
    private String nomePlaylist;
    private List<String> listaCanzoni;

    //costruttori
    public Playlist() {
        this.listaCanzoni = new ArrayList<>();
    }

    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.listaCanzoni = new ArrayList<>();
    }
    //getters & setters
    public String getNomePlaylist() {
        return nomePlaylist;
    }

    public void setNomePlaylist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
    }

    public List<String> getListaCanzoni() {
        return listaCanzoni;
    }

    public void setListaCanzoni(List<String> listaCanzoni) {
        this.listaCanzoni = listaCanzoni;
    }

    //metodo che aggiunge canzoni
    public void aggiungiCanzone(String canzone) {
        if (listaCanzoni == null) {
            listaCanzoni = new ArrayList<>();
        }
        listaCanzoni.add(canzone);
    }
}