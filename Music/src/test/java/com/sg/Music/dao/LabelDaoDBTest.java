package com.sg.Music.dao;

import com.sg.Music.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LabelDaoDBTest {

    @Autowired
    AlbumDao albumDao;

    @Autowired
    ArtistDao artistDao;

    @Autowired
    GenreDao genreDao;

    @Autowired
    LabelDao labelDao;

    @Autowired
    PlaylistDao playlistDao;

    @Autowired
    SongDao songDao;

    public LabelDaoDBTest() {

    }

    @BeforeEach
    void setUp() {
        List<Album> albums = albumDao.getAllAlbums();
        albums.forEach(album -> {
            albumDao.deleteAlbumByID(album.getId());
        });

        List<Artist> artists = artistDao.getAllArtists();
        artists.forEach(artist -> {
            artistDao.deleteArtistByID(artist.getId());
        });

        List<Genre> genres = genreDao.getAllGenres();
        genres.forEach(genre -> {
            genreDao.deleteGenreByID(genre.getId());
        });

        List<Label> labels = labelDao.getAllLabels();
        labels.forEach(label -> {
            labelDao.deleteLabelByID(label.getId());
        });

        List<Playlist> playlists = playlistDao.getAllPlaylists();
        playlists.forEach(playlist -> {
            playlistDao.deletePlaylistByID(playlist.getId());
        });

        List<Song> songs = songDao.getAllSongs();
        songs.forEach(song -> {
            songDao.deleteSongByID(song.getId());
        });
    }

    @Test
    void AddLabelAndgetLabelByID() {

        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Label added = labelDao.getLabelByID(label.getId());
        assertEquals(label, added);

    }

    @Test
    void getAllLabels() {
            Label label1 = new Label();
            label1.setName("MainStream");
            label1.setDescription("Best Mainstream artists");
            label1 = labelDao.addLabel(label1);

            Label label2 = new Label();
            label2.setName("Best Label");
            label2.setDescription("Best Label Around");
            label2 = labelDao.addLabel(label2);


            List<Label> labels = labelDao.getAllLabels();
            assertEquals(2, labels.size());
            assertTrue(labels.contains(label1));
            assertTrue(labels.contains(label2));
    }


    @Test
    void updateLabel() {
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);


        Label updated = labelDao.getLabelByID(label.getId());
        assertEquals(label, updated);

        label.setName("New York");
        labelDao.updateLabel(label);
        assertNotEquals(label, updated);

        updated = labelDao.getLabelByID(label.getId());
        assertEquals(label, updated);
    }

    @Test
    void deleteLabelByID() {
        // Create and add a new label to the database
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        // Create and add an album associated with the label
        Album album = new Album();
        album.setName("Country Album");
        album.setDescription("Best country songs");
        album.setGrammy(false);
        album = albumDao.addAlbum(album);

        // Create and add an artist associated with the label and album
        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        artist.setAlbums(albums);
        artist = artistDao.addArtist(artist);

        // Verify that the label, album, and artist have been successfully added
        assertNotNull(label.getId());
        assertNotNull(album.getId());
        assertNotNull(artist.getId());

        // Delete the label by ID
        labelDao.deleteLabelByID(label.getId());

        // Verify that the label and associated artist have been deleted
        assertNull(labelDao.getLabelByID(label.getId()));
        assertNull(artistDao.getArtistByID(artist.getId()));
    }

}