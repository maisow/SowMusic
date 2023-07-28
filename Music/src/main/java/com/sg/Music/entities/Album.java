package com.sg.Music.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album {

    private int id;

    private String name;

    private String description;

    private Boolean isGrammy;

    private List<Artist> artists;


    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artist) {
        this.artists = artist;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return getId() == album.getId() && Objects.equals(getName(), album.getName()) && Objects.equals(getDescription(), album.getDescription()) && Objects.equals(isGrammy, album.isGrammy) && Objects.equals(getArtists(), album.getArtists());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), isGrammy, getArtists());
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isGrammy=" + isGrammy +
                ", artists=" + artists +
                '}';
    }

}
