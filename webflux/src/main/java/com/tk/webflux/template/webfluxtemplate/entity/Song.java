package com.tk.webflux.template.webfluxtemplate.entity;

/**
 * @Description:
 * @Date : 2023/04/12 19:21
 * @Auther : tiankun
 */
public class Song {

    private String id;

    private String name;

    private String artist;

    private String album;

    public Song() {
    }

    public Song(String id, String name, String artist, String album) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
