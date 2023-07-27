package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sg.Music.dao.LabelDaoDB.LabelMapper;
import com.sg.Music.dao.AlbumDaoDB.AlbumMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class ArtistDaoDB implements ArtistDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Artist getArtistByID(int id) {
        try {
            String sql = "SELECT * FROM Artist WHERE artistId = ?";
            Artist artist = jdbc.queryForObject(sql, new ArtistMapper(), id);
            artist.setLabel(getLabelForArtist(id));
            artist.setAlbums(getAlbumsForArtist(id));
            return artist;
        }  catch (DataAccessException ex) {
            return null;
        }
    }

    private Label getLabelForArtist(int artistId) {
        String sql = "SELECT l.* FROM Label l " +
                "INNER JOIN Artist a ON l.labelId = a.labelId " +
                "WHERE a.artistId = ?";
        try {
            return jdbc.queryForObject(sql, new LabelMapper(), artistId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private List<Album> getAlbumsForArtist(int artistId) {
        String sql = "SELECT a.* FROM Album a " +
                "INNER JOIN ArtistAlbum aa ON a.albumId = aa.albumId " +
                "WHERE aa.artistId = ?";
        List<Album> ArtistAlbum = jdbc.query(sql, new AlbumMapper(), artistId);
        return ArtistAlbum;
    }

    @Override
    public List<Artist> getAllArtists() {
        String sql = "SELECT * FROM Artist";
        List<Artist> artists = jdbc.query(sql, new ArtistMapper());
        for (Artist artist : artists) {
            artist.setLabel(getLabelForArtist(artist.getId()));
            artist.setAlbums(getAlbumsForArtist(artist.getId()));
        }
        return artists;
    }

    @Override
    @Transactional
    public Artist addArtist(Artist artist) {
        String sql = "INSERT INTO Artist (artistName, artistAge, labelId) VALUES (?, ?, ?)";
        jdbc.update(sql, artist.getName(), artist.getAge(), artist.getLabel().getId());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        artist.setId(id);
        addArtistToArtistAlbum(artist);
        return artist;
    }

    private void addArtistToArtistAlbum(Artist artist) {
        String sql = "INSERT INTO ArtistAlbum (artistId,albumId) VALUES (?,?)";
        for (Album alb : artist.getAlbums()) {
            jdbc.update(sql, artist.getId(), alb.getId());
        }
    }

    @Override
    public void updateArtist(Artist artist) {
        String sql = "UPDATE Artist SET artistName = ?, artistAge = ?, labelId = ? WHERE artistId = ?";
        jdbc.update(sql, artist.getName(), artist.getAge(), artist.getLabel().getId(), artist.getId());
        jdbc.update("DELETE FROM ArtistAlbum WHERE artistId = ?", artist.getId());
        addArtistToArtistAlbum(artist);

    }

    @Override
    public void deleteArtistByID(int id) {
        // Delete from Genre table first
        String deleteGenreSongsSQL = "DELETE FROM Genre WHERE songId IN (SELECT songId FROM Song WHERE artistId = ?)";
        jdbc.update(deleteGenreSongsSQL, id);

        // Delete from Song table
        String deleteSongsSQL = "DELETE FROM Song WHERE artistId = ?";
        jdbc.update(deleteSongsSQL, id);

        // Delete from PlaylistSong table
        String deletePlaylistSongsSQL = "DELETE FROM PlaylistSong WHERE songId IN (SELECT songId FROM Song WHERE artistId = ?)";
        jdbc.update(deletePlaylistSongsSQL, id);

        // Delete from ArtistAlbum table
        String deleteArtistAlbumSQL = "DELETE FROM ArtistAlbum WHERE artistId = ?";
        jdbc.update(deleteArtistAlbumSQL, id);

        // Delete artist record from Artist table
        String deleteArtistSQL = "DELETE FROM Artist WHERE artistId = ?";
        jdbc.update(deleteArtistSQL, id);

    }

    @Override
    public List<Artist> getArtistsByAlbum(Album album) {
        String sql = "SELECT DISTINCT a.* FROM Artist a INNER JOIN ArtistAlbum aa " +
                " ON a.artistId = aa.artistId WHERE aa.albumId = ?";

        List<Artist> artists = jdbc.query(sql, new ArtistMapper(), album.getId());
        for (Artist artist : artists) {
            artist.setLabel(getLabelForArtist(artist.getId()));
            artist.setAlbums(getAlbumsForArtist(artist.getId()));
        }
        return artists;
    }

    public static final class ArtistMapper implements RowMapper<Artist> {
        @Override
        public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Artist artist = new Artist();
            artist.setId(rs.getInt("artistId"));
            artist.setName(rs.getString("artistName"));
            artist.setAge(rs.getInt("artistAge"));
            return artist;
        }
    }
}
