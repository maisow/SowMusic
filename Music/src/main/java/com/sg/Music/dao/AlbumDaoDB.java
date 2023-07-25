package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.ArtistDaoDB.ArtistMapper;
import org.springframework.transaction.annotation.Transactional;

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

    private List<Artist> getArtistsForAlbum(int albumId) {
        String sql = "SELECT a.* FROM Artist a " +
                "INNER JOIN ArtistAlbum aa ON a.artistId = aa.artistId " +
                "WHERE aa.albumId = ?";
        List<Artist> ArtistAlbum = jdbc.query(sql, new ArtistMapper(), albumId);
        return ArtistAlbum;
    }

    @Override
    public List<Album> getAllAlbums() {
        String sql = "SELECT * FROM Album";
        List<Album> albums = jdbc.query(sql, new AlbumMapper());
        for (Album album : albums) {
            album.setArtists(getArtistsForAlbum(album.getId()));
        }
        return albums;
    }

    @Override
    @Transactional
    public Album addAlbum(Album album) {
        String sql = "INSERT INTO Album (albumName, albumDescription, isGrammy) VALUES (?, ?, ?)";
        jdbc.update(sql, album.getName(), album.getDescription(), album.getGrammy());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        album.setId(id);
        addAlbumToArtistAlbum(album);
        return album;
    }

    private void addAlbumToArtistAlbum(Album album) {
        String sql = "INSERT INTO ArtistAlbum (artistId,albumId) VALUES (?,?)";
        for (Artist art : album.getArtists()) {
            jdbc.update(sql, art.getId(), album.getId());
        }
    }

    @Override
    public void updateAlbum(Album album) {
        String sql = "UPDATE Album SET albumName = ?, albumDescription = ?, isGrammy = ? WHERE albumId = ?";
        jdbc.update(sql, album.getName(), album.getDescription(), album.getGrammy(), album.getId());
        jdbc.update("DELETE FROM ArtistAlbum WHERE albumId = ?", album.getId());
        addAlbumToArtistAlbum(album);

    }

    @Override
    public void deleteAlbumByID(int id) {
        String deleteArtistAlbumSQL = "DELETE FROM ArtistAlbum WHERE albumId = ?";
        jdbc.update(deleteArtistAlbumSQL, id);

        // delete from song
        String deleteSongsSQL = "DELETE FROM Song WHERE albumId = ?";
        jdbc.update(deleteSongsSQL, id);

        // delete artist record from Artist table
        String deleteAlbumSQL = "DELETE FROM Album WHERE albumId = ?";
        jdbc.update(deleteAlbumSQL, id);


    }

    @Override
    public List<Album> getAlbumsByArtist(Artist artist) {
        String sql = "SELECT DISTINCT a.* FROM Album a INNER JOIN ArtistAlbum aa " +
                " ON a.albumId = aa.albumId WHERE aa.artistId = ?";

        List<Album> albums = jdbc.query(sql, new AlbumMapper(), artist.getId());
        for (Album album : albums) {
            album.setArtists(getArtistsForAlbum(album.getId()));
        }
        return albums;
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
