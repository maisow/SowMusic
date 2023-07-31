package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.ArtistDaoDB.ArtistMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sg.Music.dao.LabelDaoDB.LabelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AlbumDaoDB implements AlbumDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    ArtistDao artistDao;

/*
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
        for (Artist artist : ArtistAlbum) {
           artist=artistDao.getArtistByID(artist.getId());
        }
        return ArtistAlbum;
    } */

    @Override
    public Album getAlbumByID(int id) {
        try {
            String sql = "SELECT * FROM Album WHERE albumId = ?";
            Album album = jdbc.queryForObject(sql, new AlbumMapper(), id);
            album.setArtists(getArtistsForAlbum(id));
            return album;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Artist> getArtistsForAlbum(int albumId) {
        String sql = "SELECT a.* FROM Artist a " +
                "INNER JOIN ArtistAlbum aa ON a.artistId = aa.artistId " +
                "WHERE aa.albumId = ?";
        List<Artist> artists = jdbc.query(sql, new ArtistMapper(), albumId);
        for (Artist artist : artists) {
            artist.setAlbums(artistDao.getAlbumsForArtist(artist.getId()));
            artist.setLabel(artistDao.getLabelForArtist(artist.getId()));
        }
        return artists;
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
        if (album.getArtists() != null) {
            for (Artist artist : album.getArtists()) {
                jdbc.update(sql, artist.getId(), album.getId());
            }
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
        // Delete from ArtistAlbum table first
        String deleteArtistAlbumSQL = "DELETE FROM ArtistAlbum WHERE albumId = ?";
        jdbc.update(deleteArtistAlbumSQL, id);

        // Delete from Genre table first
        String deleteGenreSongsSQL = "DELETE FROM Genre WHERE songId IN (SELECT songId FROM Song WHERE albumId = ?)";
        jdbc.update(deleteGenreSongsSQL, id);

        // Delete from PlaylistSong table
        String deletePlaylistSongsSQL = "DELETE FROM PlaylistSong WHERE songId IN (SELECT songId FROM Song WHERE albumId = ?)";
        jdbc.update(deletePlaylistSongsSQL, id);

        // Delete from Song table
        String deleteSongsSQL = "DELETE FROM Song WHERE albumId = ?";
        jdbc.update(deleteSongsSQL, id);

        // Delete album record from Album table
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

    @Override
    public Artist getArtistByName(String artistName) {
        String sql = "SELECT * FROM Artist WHERE artistName = ?";
        try {
            return jdbc.queryForObject(sql, new ArtistMapper(), artistName);
        } catch (DataAccessException ex) {
            return null; // Return null if artist not found
        }
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
