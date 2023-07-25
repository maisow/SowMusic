package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AlbumDaoDB implements AlbumDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Album getAlbumByID(int id) {
        try {
            String sql = "SELECT * FROM Album WHERE albumId = ?";
            Album album = jdbc.queryForObject(sql, new AlbumMapper(), id);
            album.setArtists(getArtistsForAlbum(id));
            return album;
        }  catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Album> getArtistsForAlbum(int albumId) {
        String sql = "SELECT a.* FROM Artist a " +
                "INNER JOIN ArtistAlbum aa ON a.artistId = aa.artistId " +
                "WHERE aa.albumId = ?";
        List<Album> ArtistAlbum = jdbc.query(sql, new AlbumMapper(), artistId);
        return ArtistAlbum;
    }

    @Override
    public List<Album> getAllAlbums() {
        return null;
    }

    @Override
    public Album addAlbum(Album album) {
        return null;
    }

    @Override
    public void updateAlbum(Album album) {

    }

    @Override
    public void deleteAlbumByID(int id) {

    }

    @Override
    public List<Album> getAlbumsByArtist(Artist artist) {
        return null;
    }

    public static final class AlbumMapper implements RowMapper<Album> {
        @Override
        public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
            Album album = new Album();
            album.setId(rs.getInt("albumId"));
            album.setName(rs.getString("albumName"));
            album.setDescription(rs.getString("albumDescription"));
            album.setGrammy(rs.getBoolean("isGrammy"));
            return album;
        }
    }
}
