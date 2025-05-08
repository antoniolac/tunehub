package com.example.tunehub.POJO;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String nomePlaylist;
    private List<String> listaCanzoni;

    public Playlist() {
        this.listaCanzoni = new ArrayList<>();
    }

    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.listaCanzoni = new ArrayList<>();
    }

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

    public void aggiungiCanzone(String canzone) {
        if (listaCanzoni == null) {
            listaCanzoni = new ArrayList<>();
        }
        listaCanzoni.add(canzone);
    }
}