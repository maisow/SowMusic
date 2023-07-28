package com.sg.Music.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Artist {

    private int id;

    private String Name;

    private int Age;

    private Label label;

    private List<Album> albums;

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return getId() == artist.getId() && getAge() == artist.getAge() && Objects.equals(getName(), artist.getName()) && Objects.equals(getLabel(), artist.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getLabel());
    }
}
