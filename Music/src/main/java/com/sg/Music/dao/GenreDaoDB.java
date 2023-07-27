package com.sg.Music.dao;

import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Genre;
import com.sg.Music.entities.Label;
import com.sg.Music.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.SongDaoDB.SongMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class GenreDaoDB implements GenreDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Genre getGenreByID(int id) {
        try {
            String sql = "SELECT * FROM Genre WHERE genreId = ?";
            Genre genre = jdbc.queryForObject(sql, new GenreMapper(), id);
            genre.setSong(getSongForGenre(id));
            return genre;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Song getSongForGenre(int genreId) {
        String sql = "SELECT s.* FROM Song s " +
                "INNER JOIN Genre g ON s.songId = g.songId " +
                "WHERE g.genreId = ?";
        try {
            return jdbc.queryForObject(sql, new SongMapper(), genreId);
        } catch(DataAccessException ex) {
            return null;
        }
    }


    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM Genre";
        List<Genre> genres = jdbc.query(sql, new GenreMapper());
        for (Genre genre : genres) {
            genre.setSong(getSongForGenre(genre.getId()));
        }
        return genres;
    }

    @Override
    @Transactional
    public Genre addGenre(Genre genre) {
        String sql = "INSERT INTO Genre (genreName, genreDescription, songId) VALUES (?, ?, ?)";
        jdbc.update(sql, genre.getName(), genre.getDescription(), genre.getSong().getId());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        genre.setId(id);
        return genre;
    }

    @Override
    public void updateGenre(Genre genre) {
        String sql = "UPDATE Genre SET genreName = ?, genreDescription = ?, songId = ? WHERE genreId = ?";
        jdbc.update(sql, genre.getName(), genre.getDescription(), genre.getSong().getId(), genre.getId());
    }

    @Override
    public void deleteGenreByID(int id) {

        String deleteGenreSQL = "DELETE FROM Genre WHERE genreId = ?";
        jdbc.update(deleteGenreSQL, id);


    }


    public static final class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getInt("genreId"));
            genre.setName(rs.getString("genreName"));
            genre.setDescription(rs.getString("genreDescription"));
            return genre;
        }
    }

}
