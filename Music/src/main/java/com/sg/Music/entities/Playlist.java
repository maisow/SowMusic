package com.sg.Music.entities;

import java.util.List;
import java.util.Objects;

public class Playlist {

    private int id;

    private String name;

    private String description;

    private List<Song> songs;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;
        Playlist playlist = (Playlist) o;
        return getId() == playlist.getId() && Objects.equals(getName(), playlist.getName()) && Objects.equals(getDescription(), playlist.getDescription()) && Objects.equals(getSongs(), playlist.getSongs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getSongs());
    }
}
