package com.sg.Music.entities;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Objects;

public class Genre {

    private int id;

    private String name;

    private String description;

    private Song song;


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

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return getId() == genre.getId() && Objects.equals(getName(), genre.getName()) && Objects.equals(getDescription(), genre.getDescription()) && Objects.equals(getSong(), genre.getSong());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getSong());
    }
}
