package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.SongDaoDB.SongMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class PlaylistDaoDB implements PlaylistDao{

    @Autowired
    JdbcTemplate jdbc;
    @Override
    public Playlist gePlaylistByID(int id) {
        try {
            String sql = "SELECT * FROM Playlist WHERE playlistId = ?";
            Playlist playlist = jdbc.queryForObject(sql, new PlaylistMapper(), id);
            playlist.setSongs(getSongsForPlaylist(id));
            return playlist;
        }  catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Song> getSongsForPlaylist(int playlistId) {
        String sql = "SELECT s.* FROM Song s " +
                "INNER JOIN PlaylistSong ps ON s.songId = ps.songId " +
                "WHERE ps.playlistId = ?";
        List<Song> playlistSongs = jdbc.query(sql, new SongMapper(), playlistId);
        return playlistSongs;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        String sql = "SELECT * FROM Playlist";
        List<Playlist> playlists = jdbc.query(sql, new PlaylistMapper());
        for (Playlist playlist : playlists) {
            playlist.setSongs(getSongsForPlaylist(playlist.getId()));
        }
        return playlists;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        String sql = "INSERT INTO Playlist (playlistName, playlistDescription) VALUES (?, ?)";
        jdbc.update(sql, playlist.getName(), playlist.getDescription());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        playlist.setId(id);
        addPlaylistToPlaylistSong(playlist);
        return playlist;
    }

    private void addPlaylistToPlaylistSong(Playlist playlist) {
        String sql = "INSERT INTO PlaylistSong (playlistId,SongId) VALUES (?,?)";
        for (Song song : playlist.getSongs()) {
            jdbc.update(sql, playlist.getId(), song.getId());
        }
    }

    @Override
    public void updateSong(Playlist playlist) {
        String sql = "UPDATE Artist SET playlistName = ?, playlistDescription = ? WHERE playlistId = ?";
        jdbc.update(sql, playlist.getName(), playlist.getDescription(), playlist.getId());
        jdbc.update("DELETE FROM PlaylistSong WHERE playlistId = ?", playlist.getId());
        addPlaylistToPlaylistSong(playlist);
    }

    @Override
    public void deletePlaylistByID(int id) {
        String deletePlaylistSongSQL = "DELETE FROM PlaylistSong WHERE playlistId = ?";
        jdbc.update(deletePlaylistSongSQL, id);

        String deletePlaylistSQL = "DELETE FROM Playlist WHERE playlistId = ?";
        jdbc.update(deletePlaylistSQL, id);
    }

    @Override
    public List<Playlist> getPlaylistsBySong(Song song) {
        String sql = "SELECT DISTINCT p.* FROM Playlist p INNER JOIN PlaylistSong ps " +
                " ON p.playlistId = ps.playlistId WHERE ps.songId = ?";

        List<Playlist> playlists = jdbc.query(sql, new PlaylistMapper(), song.getId());
        for (Playlist playlist : playlists) {
            playlist.setSongs(getSongsForPlaylist(playlist.getId()));
        }
        return playlists;


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
