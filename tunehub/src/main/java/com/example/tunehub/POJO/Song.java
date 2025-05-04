package com.example.tunehub.POJO;

public class Song {
    //attributes
    private String id;
    private String title;
    private String artist;
    private String album;
    private String imageUrl;
    private String previewUrl;

    //constructor
    public Song(String id, String title, String artist, String album, String imageUrl, String previewUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.imageUrl = imageUrl;
        this.previewUrl = previewUrl;
    }

    //Getter & Setter
    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
