package com.sg.Music.dao;

import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlaylistDaoDB implements PlaylistDao{

    @Autowired
    JdbcTemplate jdbc;
    @Override
    public Playlist gePlaylistByID(int id) {
        return null;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return null;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public void updateSong(Playlist playlist) {

    }

    @Override
    public void deletePlaylistByID(int id) {

    }

    @Override
    public List<Playlist> getPlaylistsBySong(Song song) {
        return null;
    }

    public static final class PlaylistMapper implements RowMapper<Playlist> {
        @Override
        public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Playlist playlist = new Playlist();
            playlist.setId(rs.getInt("playlistId"));
            playlist.setName(rs.getString("playlistName"));
            playlist.setDescription(rs.getString("playlistDescription"));
            return playlist;
        }
    }
}
