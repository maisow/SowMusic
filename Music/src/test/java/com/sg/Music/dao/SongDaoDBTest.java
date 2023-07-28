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
class SongDaoDBTest {

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

    public SongDaoDBTest() {
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
    void AddSongAndgetSongByID() {

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


        Song added = songDao.getSongByID(song.getId());
        assertEquals(song, added);

    }

    @Test
    void getAllSongs() {

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

        Song song2 = new Song();
        song2.setName("Best of both worlds");
        song2.setGrammy(false);
        song2.setArtist(artist);
        song2.setAlbum(album);
        song2.setPlaylists(playlists);
        song2 = songDao.addSong(song2);

        List<Song> songs = songDao.getAllSongs();
        assertEquals(2, songs.size());
        assertTrue(songs.contains(song));
        assertTrue(songs.contains(song2));
    }


    @Test
    void updateSong() {

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

        Song updated = songDao.getSongByID(song.getId());
        assertEquals(song, updated);

        song.setName("I wanna know you");
        songDao.updateSong(song);
        assertNotEquals(song, updated);

        updated = songDao.getSongByID(song.getId());
        assertEquals(song, updated);
    }

    @Test
    void deleteSongByID() {
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

        Genre genre = new Genre();
        genre.setName("Pop America");
        genre.setDescription("Los Angelos");
        genre.setSong(song);
        genre = genreDao.addGenre(genre);

        Song deleted = songDao.getSongByID(song.getId());
        assertEquals(song, deleted);

        songDao.deleteSongByID(song.getId());

        deleted = songDao.getSongByID(song.getId());
        Genre genreDeleted = genreDao.getGenreByID(genre.getId());
        assertNull(deleted);
        assertNull(genreDeleted);



    }
}