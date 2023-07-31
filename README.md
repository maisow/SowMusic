# Music Application with Java and Spring


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Welcome to the Music Application, a Java and Spring-based platform for music lovers! This application allows users to manage their favorite music-related entities like albums, artists, labels, genres, songs, and playlists. It offers comprehensive CRUD (Create, Read, Update, Delete) functionality for each entity, allowing users to organize their music preferences effectively.

## Features

- **Album Management:** Add, view, update, and delete albums in the database.
- **Artist Management:** Create, read, update, and delete artist information.
- **Label Management:** Manage music labels, including creation, retrieval, modification, and deletion.
- **Genre Classification:** Organize music by genres and perform CRUD operations on them.
- **Song Catalog:** Create, read, update, and delete songs from the music collection.
- **Playlist Creation:** Allow users to create custom playlists with their favorite songs.

## Technologies Used

- Java
- Spring Framework
- Spring Boot
- Spring Data JPA
- Thymeleaf (or your preferred view templating engine)
- MySQL (or any other supported database)
- HTML, CSS, JavaScript
- Maven (or Gradle) for build and dependency management

## Getting Started

To run this application locally, follow these steps:

1. **Prerequisites:**

   - Java (version XX or higher) should be installed on your system.
   - MySQL (or another supported database) should be set up with the necessary configurations.

2. **Clone the Repository:**

   ```bash
   git clone https://github.com/your-username/music-app.git
   cd music-app
   ```

3. **Database Configuration:**

   - Create a new MySQL database named `SowMusic`.
   - Update the database configuration in `application.properties` with your database credentials.

4. **Build and Run:**

   - Use Maven (or Gradle) to build and run the application:

     ```bash
     mvn spring-boot:run
     ```

   - The application should now be accessible at `http://localhost:8080`.

## API Documentation

The Music Application also provides an API to interact with the backend programmatically. To explore the API endpoints and their usage, please refer to the [API Documentation](/api-docs) (or your preferred URL for API documentation).

## Database Schema

The application utilizes the following entities for managing music-related data:

1. **Label:**
   - labelId (Primary Key)
   - labelName (VARCHAR, 50) (NOT NULL)
   - labelDescription (VARCHAR, 250) (NOT NULL)

2. **Artist:**
   - artistId (Primary Key)
   - artistName (VARCHAR, 50) (NOT NULL)
   - artistAge (INT) (NOT NULL)
   - labelId (Foreign Key referencing Label.labelId)

3. **Album:**
   - albumId (Primary Key)
   - albumName (VARCHAR, 50) (NOT NULL)
   - albumDescription (VARCHAR, 250) (NOT NULL)
   - isGrammy (BOOLEAN) (NOT NULL)

4. **Song:**
   - songId (Primary Key)
   - songName (VARCHAR, 60) (NOT NULL)
   - isGrammy (BOOLEAN) (NOT NULL)
   - artistId (Foreign Key referencing Artist.artistId)
   - albumId (Foreign Key referencing Album.albumId)

5. **Genre:**
   - genreId (Primary Key)
   - genreName (VARCHAR, 50) (NOT NULL)
   - genreDescription (VARCHAR, 250)
   - songId (Foreign Key referencing Song.songId)

6. **Playlist:**
   - playlistId (Primary Key)
   - playlistName (VARCHAR, 50) (NOT NULL)
   - playlistDescription (VARCHAR, 250)

7. **PlaylistSong:**
   - playlistId (Foreign Key referencing Playlist.playlistId)
   - songId (Foreign Key referencing Song.songId)
   - (Primary Key composed of playlistId and songId)

8. **ArtistAlbum:**
   - artistId (Foreign Key referencing Artist.artistId)
   - albumId (Foreign Key referencing Album.albumId)
   - (Primary Key composed of artistId and albumId)

## Usage

1. **Home Page:**

   - The home page displays general information about the Music Application.

2. **Entity Management:**

   - Access the respective pages for album, artist, label, genre, song, and playlist management.
   - Perform CRUD operations on each entity using the provided user interface.

3. **Search and Filter:**

   - Utilize search and filter options to find specific music-related entities.

## Contributing

We welcome contributions to the Music Application! If you find a bug or have an idea for an enhancement, feel free to open an issue or submit a pull request. Please read the [Contributing Guidelines](CONTRIBUTING.md) for more information on how to contribute.

## License

The Music Application is open-source and available under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code. We would appreciate acknowledgment if you find it useful.

---

Enjoy the Music Application and have fun managing your music preferences! If you have any questions or feedback, please don't hesitate to get in touch with us. Let the music play! 🎶
