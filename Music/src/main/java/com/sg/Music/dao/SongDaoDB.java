package com.sg.Music.dao;

import com.sg.Music.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.ArtistDaoDB.ArtistMapper;
import com.sg.Music.dao.AlbumDaoDB.AlbumMapper;
import com.sg.Music.dao.PlaylistDaoDB.PlaylistMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SongDaoDB implements SongDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Song geSongByID(int id) {
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
    public Song addSong(Song song) {
        return null;
    }

    @Override
    public void updateSong(Song song) {

    }

    @Override
    public void deleteSongByID(int id) {

    }

    public static final class SongMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            Song song = new Song();
            song.setId(rs.getInt("songId"));
            song.setName(rs.getString("songName"));
            song.setGrammy(rs.getBoolean("songDescription"));
            return song;
        }
    }
}
