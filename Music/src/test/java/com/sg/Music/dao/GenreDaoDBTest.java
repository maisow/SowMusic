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
class GenreDaoDBTest {

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

    public GenreDaoDBTest() {
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
    void AddAndgetGenreByID() {
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


    }

    @Test
    void getAllGenres() {

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


        Genre genre1 = new Genre();
        genre1.setName("Rock");
        genre1.setDescription("Classic Rock");
        genre1.setSong(song); // Using the same song as before
        genre1 = genreDao.addGenre(genre1);

        Genre genre2 = new Genre();
        genre2.setName("Pop America");
        genre2.setDescription("Los Angeles");
        genre2.setSong(song); // Using the same song as before
        genre2 = genreDao.addGenre(genre2);

        List<Genre> genres = genreDao.getAllGenres();
        assertEquals(2, genres.size());
        assertTrue(genres.contains(genre1));
        assertTrue(genres.contains(genre2));
    }



    @Test
    void updateGenre() {

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


        Genre updated = genreDao.getGenreByID(genre.getId());
        assertEquals(genre, updated);

        genre.setName("Country Music");
        genreDao.updateGenre(genre);
        assertNotEquals(genre, updated);

        updated = genreDao.getGenreByID(genre.getId());
        assertEquals(genre, updated);
    }

    @Test
    void deleteGenreByID() {

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

        Genre deleted = genreDao.getGenreByID(genre.getId());
        assertEquals(genre, deleted);

        genreDao.deleteGenreByID(genre.getId());

        deleted = genreDao.getGenreByID(genre.getId());
        assertNull(deleted);


    }
}