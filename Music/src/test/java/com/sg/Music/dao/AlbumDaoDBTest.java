package com.sg.Music.dao;

import com.sg.Music.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        album.setName("Top of the world Burna Boy");
        album.setDescription("Giant");
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
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        artist = artistDao.addArtist(artist);

        Album album1 = new Album();
        album1.setName("Top of the world Burna Boy");
        album1.setDescription("Giant");
        album1.setGrammy(false);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        album1.setArtists(artists);
        album1 = albumDao.addAlbum(album1);

        Album album2 = new Album();
        album2.setName("London");
        album2.setDescription("Taylor Swift");
        album2.setGrammy(true);
        album2.setArtists(artists);
        album2 = albumDao.addAlbum(album2);


        List<Album> albums = albumDao.getAllAlbums();
        assertEquals(2, albums.size());
        assertTrue(albums.contains(album1));
        assertTrue(albums.contains(album2));
    }

    @Test
    void updateAlbum() {
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
        album.setName("Top of the world Burna Boy");
        album.setDescription("Giant");
        album.setGrammy(false);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        album.setArtists(artists);
        album = albumDao.addAlbum(album);

        Album updated = albumDao.getAlbumByID(album.getId());
        assertEquals(album, updated);

        album.setName("Best Song Ever");
        albumDao.updateAlbum(album);
        assertNotEquals(album, updated);

        updated = albumDao.getAlbumByID(album.getId());
        assertEquals(album, updated);
    }

    @Test
    void deleteAlbumByID() {
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
        album.setName("Top of the world Burna Boy");
        album.setDescription("Giant");
        album.setGrammy(false);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        album.setArtists(artists);
        album = albumDao.addAlbum(album);

        Playlist playlist = new Playlist();
        playlist.setName("Lovely");
        playlist.setDescription("summer fun");
        playlist = playlistDao.addPlaylist(playlist);

        Song song = new Song();
        song.setName("Wonderful");
        song.setGrammy(true);
        song.setArtist(artist);
        song.setAlbum(album);
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist);
        song.setPlaylists(playlists);
        song = songDao.addSong(song);

        Genre genre = new Genre();
        genre.setName("Pop America");
        genre.setDescription("Los Angelos");
        genre.setSong(song);
        genre = genreDao.addGenre(genre);

        assertNotNull(genre);
        assertNotNull(song);

        Album deleted = albumDao.getAlbumByID(album.getId());
        assertEquals(album, deleted);

        albumDao.deleteAlbumByID(album.getId());

        deleted = albumDao.getAlbumByID(album.getId());
        assertNull(deleted);
        assertNotNull(song);


    }

    @Test
    void getAlbumsByArtist() {
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        artist = artistDao.addArtist(artist);

        Album album1 = new Album();
        album1.setName("Top of the world Burna Boy");
        album1.setDescription("Giant");
        album1.setGrammy(false);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        album1.setArtists(artists);
        album1 = albumDao.addAlbum(album1);

        Album album2 = new Album();
        album2.setName("London");
        album2.setDescription("Taylor Swift");
        album2.setGrammy(true);
        album2.setArtists(artists);
        album2 = albumDao.addAlbum(album2);

        List<Album> albums = albumDao.getAlbumsByArtist(artist);
        assertEquals(2, albums.size());

        assertTrue(albums.contains(album1));
        assertTrue(albums.contains(album2));
    }
}