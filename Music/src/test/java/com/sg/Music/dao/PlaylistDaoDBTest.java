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
class PlaylistDaoDBTest {

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

    public PlaylistDaoDBTest() {
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
    void AddAndgePlaylistByID() {
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

        Song song = new Song();
        song.setName("Wonderful");
        song.setGrammy(true);
        song.setArtist(artist);
        song.setAlbum(album);
        song = songDao.addSong(song);

        Playlist playlist = new Playlist();
        playlist.setName("Lovely");
        playlist.setDescription("summer fun");
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        playlist.setSongs(songs);
        playlist = playlistDao.addPlaylist(playlist);

        Playlist added = playlistDao.gePlaylistByID(playlist.getId());
        assertEquals(playlist, added);

    }

    @Test
    void getAllPlaylists() {
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

        Song song = new Song();
        song.setName("Wonderful");
        song.setGrammy(true);
        song.setArtist(artist);
        song.setAlbum(album);
        song = songDao.addSong(song);

        Playlist playlist = new Playlist();
        playlist.setName("Lovely");
        playlist.setDescription("summer fun");
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        playlist.setSongs(songs);
        playlist = playlistDao.addPlaylist(playlist);

        Playlist playlist2 = new Playlist();
        playlist2.setName("Style");
        playlist2.setDescription("Eras tour");
        playlist2.setSongs(songs);
        playlist2 = playlistDao.addPlaylist(playlist2);

        List<Playlist> playlists = playlistDao.getAllPlaylists();
        assertEquals(2, playlists.size());
        assertTrue(playlists.contains(playlist));
        assertTrue(playlists.contains(playlist2));
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

        Song song = new Song();
        song.setName("Wonderful");
        song.setGrammy(true);
        song.setArtist(artist);
        song.setAlbum(album);
        song = songDao.addSong(song);

        Playlist playlist = new Playlist();
        playlist.setName("Lovely");
        playlist.setDescription("summer fun");
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        playlist.setSongs(songs);
        playlist = playlistDao.addPlaylist(playlist);

        Playlist updated = playlistDao.gePlaylistByID(playlist.getId());
        assertEquals(playlist, updated);

        playlist.setName("Best Playlist Ever");
        playlistDao.updatePlaylist(playlist);
        assertNotEquals(playlist, updated);

        updated = playlistDao.gePlaylistByID(playlist.getId());
        assertEquals(playlist, updated);
    }

    @Test
    void deletePlaylistByID() {
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

        Song song = new Song();
        song.setName("Wonderful");
        song.setGrammy(true);
        song.setArtist(artist);
        song.setAlbum(album);
        song = songDao.addSong(song);

        Playlist playlist = new Playlist();
        playlist.setName("Lovely");
        playlist.setDescription("summer fun");
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        playlist.setSongs(songs);
        playlist = playlistDao.addPlaylist(playlist);

        Playlist deleted = playlistDao.gePlaylistByID(playlist.getId());
        assertEquals(playlist, deleted);

        playlistDao.deletePlaylistByID(playlist.getId());

        deleted = playlistDao.gePlaylistByID(playlist.getId());
        assertNull(deleted);


    }
    @Test
    void getPlaylistsBySong() {
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

        Song song = new Song();
        song.setName("Wonderful");
        song.setGrammy(true);
        song.setArtist(artist);
        song.setAlbum(album);
        song = songDao.addSong(song);

        Playlist playlist = new Playlist();
        playlist.setName("Lovely");
        playlist.setDescription("summer fun");
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        playlist.setSongs(songs);
        playlist = playlistDao.addPlaylist(playlist);

        Playlist playlist2 = new Playlist();
        playlist2.setName("Style");
        playlist2.setDescription("Eras tour");
        playlist2.setSongs(songs);
        playlist2 = playlistDao.addPlaylist(playlist2);

        List<Playlist> playlists = playlistDao.getPlaylistsBySong(song);
        assertEquals(2, playlists.size());

        assertTrue(playlists.contains(playlist));
        assertTrue(playlists.contains(playlist2));

    }
}