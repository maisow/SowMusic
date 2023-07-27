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
class ArtistDaoDBTest {

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

    public ArtistDaoDBTest() {
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
    void AddAndgetArtistByID() {
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Album album = new Album();
        album.setName("Country Album");
        album.setDescription("Best country songs");
        album.setGrammy(false);
        album = albumDao.addAlbum(album);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        artist.setAlbums(albums);
        artist = artistDao.addArtist(artist);

        Artist added = artistDao.getArtistByID(artist.getId());
        assertEquals(artist, added);

    }

    @Test
    void getAllArtists() {
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Label label2 = new Label();
        label2.setName("Fun Company");
        label2.setDescription("Worlds Best");
        label2 = labelDao.addLabel(label2);

        Album album = new Album();
        album.setName("Country Album");
        album.setDescription("Best country songs");
        album.setGrammy(false);
        album = albumDao.addAlbum(album);

        Album album2 = new Album();
        album2.setName("Canada album");
        album2.setDescription("canadian songs");
        album2.setGrammy(false);
        album2 = albumDao.addAlbum(album2);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        artist.setAlbums(albums);
        artist = artistDao.addArtist(artist);

        Artist artist2 = new Artist();
        artist2.setName("Maimouna Sow");
        artist2.setAge(21);
        artist2.setLabel(label2);
        List<Album> albums2 = new ArrayList<>();
        albums2.add(album2);
        artist2.setAlbums(albums2);
        artist2 = artistDao.addArtist(artist2);

        List<Artist> artists = artistDao.getAllArtists();
        assertEquals(2, artists.size());
        assertTrue(artists.contains(artist));
        assertTrue(artists.contains(artist2));
    }



    @Test
    void updateArtist() {
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Album album = new Album();
        album.setName("Country Album");
        album.setDescription("Best country songs");
        album.setGrammy(false);
        album = albumDao.addAlbum(album);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        artist.setAlbums(albums);
        artist = artistDao.addArtist(artist);

        Artist updated = artistDao.getArtistByID(artist.getId());
        assertEquals(artist, updated);

        artist.setName("Guinea");
        artistDao.updateArtist(artist);
        assertNotEquals(artist, updated);

        updated = artistDao.getArtistByID(artist.getId());
        assertEquals(artist, updated);

    }

    @Test
    void deleteArtistByID() {
        Label label = new Label();
        label.setName("WorldRecords");
        label.setDescription("Represents best country artist");
        label = labelDao.addLabel(label);

        Album album = new Album();
        album.setName("Country Album");
        album.setDescription("Best country songs");
        album.setGrammy(false);
        album = albumDao.addAlbum(album);

        Artist artist = new Artist();
        artist.setName("John Doe");
        artist.setAge(35);
        artist.setLabel(label);
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        artist.setAlbums(albums);
        artist = artistDao.addArtist(artist);

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

        /*Artist deleted = artistDao.getArtistByID(artist.getId());
        assertEquals(artist, deleted);

        artistDao.deleteArtistByID(artist.getId());

        deleted = artistDao.getArtistByID(artist.getId());
        assertNull(deleted); */


    }

    @Test
    void getArtistsByAlbum() {
    }
}