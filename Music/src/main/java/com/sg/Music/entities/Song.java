package com.sg.Music.entities;

import java.util.List;
import java.util.Objects;

public class Song {

    private int id;

    private String name;

    private Boolean isGrammy;

    private Artist artist;
    private Album album;
    private List<Playlist> playlists;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getGrammy() {
        return isGrammy;
    }

    public void setGrammy(Boolean grammy) {
        isGrammy = grammy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getId() == song.getId() && Objects.equals(getName(), song.getName()) && Objects.equals(isGrammy, song.isGrammy) && Objects.equals(getArtist(), song.getArtist()) && Objects.equals(getAlbum(), song.getAlbum()) && Objects.equals(getPlaylists(), song.getPlaylists());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isGrammy, getArtist(), getAlbum(), getPlaylists());
    }
}
