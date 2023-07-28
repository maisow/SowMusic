package com.sg.Music.dao;

import com.sg.Music.entities.*;
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

    @Autowired
    PlaylistDao playlistDao;
    @Autowired
    ArtistDao artistDao;

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

            Song song = jdbc.queryForObject(sql, new SongMapper(), genreId);
            song.setAlbum(getAlbumForSong(song.getId()));
            song.setPlaylists(getPlaylistsForSong(song.getId()));
            song.setArtist(getArtistForSong(song.getId()));
            return song;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private Artist getArtistForSong(int songId) {
        String sql = "SELECT A.* FROM Artist A " +
                "INNER JOIN Song s ON A.artistId = s.artistId " +
                "WHERE s.songId = ?";
        try {
            Artist artist = jdbc.queryForObject(sql, new ArtistDaoDB.ArtistMapper(), songId);
            artist.setLabel(artistDao.getLabelForArtist(artist.getId()));
            artist.setAlbums(artistDao.getAlbumsForArtist(artist.getId()));
            return artist;
        } catch(DataAccessException ex) {
            return null;
        }
    }


    private Album getAlbumForSong(int songId) {
        String sql = "SELECT l.* FROM Album l " +
                "INNER JOIN Song a ON l.albumId = a.albumId " +
                "WHERE a.songId = ?";
        try {
            return jdbc.queryForObject(sql, new AlbumDaoDB.AlbumMapper(), songId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private List<Playlist> getPlaylistsForSong(int songId) {
        String sql = "SELECT a.* FROM Playlist a " +
                "INNER JOIN PlaylistSong aa ON a.playlistId = aa.playlistId " +
                "WHERE aa.songId = ?";
        List<Playlist> PlaylistSong = jdbc.query(sql, new PlaylistDaoDB.PlaylistMapper(), songId);
        return PlaylistSong;
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
