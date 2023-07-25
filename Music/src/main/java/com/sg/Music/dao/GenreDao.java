package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Genre;
import com.sg.Music.entities.Song;

import java.util.List;

public interface GenreDao {
    Genre getGenreByID(int id);
    List<Genre> getAllGenres();
    Genre addGenre(Genre genre);
    void updateGenre(Genre genre);
    void deleteGenreByID(int id);

}
