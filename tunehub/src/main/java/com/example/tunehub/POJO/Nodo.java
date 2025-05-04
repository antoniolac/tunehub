package com.example.tunehub.POJO;

public class Nodo {
    protected Song info;
    protected Nodo link;

    public Nodo(Song pInfo) {
        this.info = pInfo;
        this.link = null; // Imposta il link inizialmente a null
    }

    public void setLink(Nodo pLink) {
        this.link = pLink;
    }

    public Nodo getLink() {
        return link;
    }

    public Song getInfo() {
        return info;
    }
}

