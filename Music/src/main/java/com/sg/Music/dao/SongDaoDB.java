package com.sg.Music.dao;

import com.sg.Music.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.ArtistDaoDB.ArtistMapper;
import com.sg.Music.dao.AlbumDaoDB.AlbumMapper;
import com.sg.Music.dao.PlaylistDaoDB.PlaylistMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class SongDaoDB implements SongDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Song getSongByID(int id) {
        try {
            String sql = "SELECT * FROM Song WHERE songId = ?";
            Song song = jdbc.queryForObject(sql, new SongMapper(), id);
            song.setArtist(getArtistForSong(id));
            song.setAlbum(getAlbumForSong(id));
            song.setPlaylists(getPlaylistsForSong(id));
            return song;
        }  catch (DataAccessException ex) {
            return null;
        }
    }

    private Artist getArtistForSong(int songId) {
        String sql = "SELECT A.* FROM Artist A " +
                "INNER JOIN Song s ON A.artistId = s.artistId " +
                "WHERE s.songId = ?";
        try {
            return jdbc.queryForObject(sql, new ArtistMapper(), songId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private Album getAlbumForSong(int songId) {
        String sql = "SELECT l.* FROM Album l " +
                "INNER JOIN Song a ON l.albumId = a.albumId " +
                "WHERE a.songId = ?";
        try {
            return jdbc.queryForObject(sql, new AlbumMapper(), songId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private List<Playlist> getPlaylistsForSong(int songId) {
        String sql = "SELECT a.* FROM Playlist a " +
                "INNER JOIN PlaylistSong aa ON a.playlistId = aa.playlistId " +
                "WHERE aa.songId = ?";
        List<Playlist> PlaylistSong = jdbc.query(sql, new PlaylistMapper(), songId);
        return PlaylistSong;
    }

    @Override
    public List<Song> getAllSongs() {
        String sql = "SELECT * FROM Song";
        List<Song> songs = jdbc.query(sql, new SongMapper());
        for (Song song : songs) {
            song.setArtist(getArtistForSong(song.getId()));
            song.setAlbum(getAlbumForSong(song.getId()));
            song.setPlaylists(getPlaylistsForSong(song.getId()));
        }
        return songs;
    }

    @Override
    @Transactional
    public Song addSong(Song song) {
        String sql = "INSERT INTO Song (songName, isGrammy, artistId, albumId) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, song.getName(), song.getGrammy(), song.getArtist().getId(), song.getAlbum().getId());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        song.setId(id);
        addSongToPlaylistSong(song);
        return song;
    }

    private void addSongToPlaylistSong(Song song) {
        String sql = "INSERT INTO PlaylistSong (playlistId,songId) VALUES (?,?)";
        for (Playlist play : song.getPlaylists()) {
            jdbc.update(sql, play.getId(), song.getId());
        }
    }

    @Override
    public void updateSong(Song song) {
        String sql = "UPDATE Song SET songName = ?, isGrammy = ?, artistId = ?, albumId =? WHERE songId = ?";
        jdbc.update(sql, song.getName(), song.getGrammy(), song.getArtist().getId(), song.getAlbum().getId(), song.getId());
        jdbc.update("DELETE FROM PlaylistSong WHERE songId = ?", song.getId());
        addSongToPlaylistSong(song);
    }

    @Override
    public void deleteSongByID(int id) {
        String deletePlaylistAlbumSQL = "DELETE FROM PlaylistSong WHERE songId = ?";
        jdbc.update(deletePlaylistAlbumSQL, id);

        // delete from genre
        String deleteSongsSQL = "DELETE FROM Genre WHERE songId = ?";
        jdbc.update(deleteSongsSQL, id);

        String deleteSongSQL = "DELETE FROM Song WHERE songId = ?";
        jdbc.update(deleteSongSQL, id);
    }

    public static final class SongMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            Song song = new Song();
            song.setId(rs.getInt("songId"));
            song.setName(rs.getString("songName"));
            song.setGrammy(rs.getBoolean("isGrammy"));
            return song;
        }
    }
}
