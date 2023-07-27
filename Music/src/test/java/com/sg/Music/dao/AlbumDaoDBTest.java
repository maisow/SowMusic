package com.sg.Music.dao;

import com.sg.Music.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AlbumDaoDBTest {

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

    public AlbumDaoDBTest() {
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
    void AddAndgetAlbumByID() {

        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        artist = artistDao.addArtist(artist);

        Album album = new Album();
        album.setName("Country Album");
        album.setDescription("Best country songs");
        album.setGrammy(false);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        album.setArtists(artists);
        album = albumDao.addAlbum(album);

        Album added = albumDao.getAlbumByID(album.getId());
        assertEquals(album, added);
    }

    @Test
    void getAllAlbums() {
    }


    @Test
    void updateAlbum() {
    }

    @Test
    void deleteAlbumByID() {
    }

    @Test
    void getAlbumsByArtist() {
    }
}