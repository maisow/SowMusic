package com.sg.Music.dao;

import com.sg.Music.entities.*;

import java.util.List;

public interface GenreDao {
    Genre getGenreByID(int id);
    List<Genre> getAllGenres();
    Genre addGenre(Genre genre);
    void updateGenre(Genre genre);
    void deleteGenreByID(int id);

}
