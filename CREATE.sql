-- Creating Tables SQL Script--


CREATE TABLE IF NOT EXISTS "Users"(Email VARCHAR(255) PRIMARY KEY NOT NULL, -- Sql para criar tabela "Users" com atributos Email (PRIMARY KEY), Password, Nome e Permit (Permissões)
               Password VARCHAR(255) NOT NULL,
               Nome VARCHAR(255) NOT NULL,
               Permit INT NOT NULL);

CREATE TABLE IF NOT EXISTS "Playlists"(Playlist_id INT PRIMARY KEY NOT NULL, -- Sql para criar tabela "Playlists" com atributos Playlist_id (PRIMARY KEY), Email , Password, Nome e Permit (Permissões)
               Email VARCHAR(255) REFERENCES "Users"(Email),
               Titulo VARCHAR(255) NOT NULL,
               Musics_m INT NOT NULL,
               Privacidade INT NOT NULL);

CREATE TABLE IF NOT EXISTS "Artists"(Artistic_name VARCHAR(255) PRIMARY KEY NOT NULL, -- Sql para criar tabela "Artists" com atributos Artistic_name (PRIMARY KEY), Nome ,Origin, Active_years, Biography, Concertos
               Nome VARCHAR(255) NOT NULL,
               Origin VARCHAR(255) NOT NULL,
               Active_years INT NOT NULL,
               Biography VARCHAR(255) NOT NULL,
               Concertos VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS "Bands"(Band VARCHAR(255) NOT NULL, -- Sql para criar tabela "Bands" com atributos Band (PRIMARY KEY), Artistic_name (FOREIGN KEY)
               Artistic_name VARCHAR(255) REFERENCES "Artists"(Artistic_name));

CREATE TABLE IF NOT EXISTS "Genres"(Genre VARCHAR(255) PRIMARY KEY NOT NULL); -- Sql para criar tabela "Genres" com atributos Genre (PRIMARY KEY)

CREATE TABLE IF NOT EXISTS "Labels"(Label VARCHAR(255) PRIMARY KEY NOT NULL); -- Sql para criar tabela "Labels" com atributos Label (PRIMARY KEY)

CREATE TABLE IF NOT EXISTS "Musics"(Music_id INT PRIMARY KEY NOT NULL, -- Sql para criar tabela "Musics" com atributos Music_id (PRIMARY KEY), Title, Duration, Launch_Date, Upvotes, Lyrics, Format
               Title VARCHAR(255) NOT NULL,
               Duration INT NOT NULL,
               Launch_Date VARCHAR(255) NOT NULL,
               Upvotes INT NOT NULL,
               Lyrics VARCHAR(255) NOT NULL,
               Format VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS "Albums"(Album_id INT PRIMARY KEY NOT NULL, -- Sql para criar tabela "Albums" com atributos Album_id (PRIMARY KEY), Title, Duration, Launch_Date, Track_Number
               Title VARCHAR(255) NOT NULL,
               Duration INT NOT NULL,
               Launch_Date VARCHAR(255) NOT NULL,
               Track_Number INT NOT NULL);

CREATE TABLE IF NOT EXISTS "Album_Critics"(Critic_id INT PRIMARY KEY NOT NULL, -- Sql para criar tabela "Album_Critics" com atributos Critic_id (PRIMARY KEY), Critic, Email (FOREIGN KEY), Album_id (FOREIGN KEY)
               Critic VARCHAR(255) NOT NULL,
               Email VARCHAR(255) REFERENCES "Users"(Email),
               Album_id INT REFERENCES "Albums"(Album_id));

CREATE TABLE IF NOT EXISTS "Music_Critics"(Critic_id INT PRIMARY KEY NOT NULL, -- Sql para criar tabela "Music_Critics" com atributos Critic_id (PRIMARY KEY), Critic, Email (FOREIGN KEY), Music_id (FOREIGN KEY)
               Critic VARCHAR(255) NOT NULL,
               Email VARCHAR(255) REFERENCES "Users"(Email),
               Music_id INT REFERENCES "Musics"(Music_id));

CREATE TABLE IF NOT EXISTS "Composers"(Music_id INT REFERENCES "Musics"(Music_id), -- Sql para criar tabela "Composers" com atributos Music_id (FOREIGN KEY), Artistic_name (FOREIGN KEY)
               Artistic_name VARCHAR(255) REFERENCES "Artists"(Artistic_name));

CREATE TABLE IF NOT EXISTS "Composers_Album"(Album_id INT REFERENCES "Albums"(Album_id), -- Sql para criar tabela "Composers_Album" com atributos Album_id (FOREIGN KEY), Artistic_name (FOREIGN KEY)
               Artistic_name VARCHAR(255) REFERENCES "Artists"(Artistic_name));

CREATE TABLE IF NOT EXISTS "Genres_Music"(Music_id INT REFERENCES "Musics"(Music_id), -- Sql para criar tabela "Genres_Music" com atributos Music_id (FOREIGN KEY), Genre (FOREIGN KEY)
               Genre VARCHAR(255) REFERENCES "Genres"(genre));

CREATE TABLE IF NOT EXISTS "Genres_Album"(Album_id INT REFERENCES "Albums"(Album_id), -- Sql para criar tabela "Genres_Album" com atributos Album_id (FOREIGN KEY), Genre (FOREIGN KEY)
               Genre VARCHAR(255) REFERENCES "Genres"(genre));

CREATE TABLE IF NOT EXISTS "Labels_Music"(Music_id INT REFERENCES "Musics"(Music_id), -- Sql para criar tabela "Labels_Music" com atributos Music_id (FOREIGN KEY), label (FOREIGN KEY)
               label VARCHAR(255) REFERENCES "Labels"(label));

CREATE TABLE IF NOT EXISTS "Labels_Album"(Album_id INT REFERENCES "Albums"(Album_id), -- Sql para criar tabela "Labels_Album" com atributos Album_id (FOREIGN KEY), label (FOREIGN KEY)
               label VARCHAR(255) REFERENCES "Labels"(label));

CREATE TABLE IF NOT EXISTS "Albums_Music"(Music_id INT REFERENCES "Musics"(Music_id), -- Sql para criar tabela "Albums_Music" com atributos Music_id (FOREIGN KEY), Album_id (FOREIGN KEY)
               Album_id INT REFERENCES "Albums"(Album_id));

CREATE TABLE IF NOT EXISTS "Albums_Artists"(Album_id INT REFERENCES "Albums"(Album_id), -- Sql para criar tabela "Albums_Artists" com atributos Album_id (FOREIGN KEY), Artistic_name (FOREIGN KEY)
               Artistic_name VARCHAR(255) REFERENCES "Artists"(Artistic_name));

CREATE TABLE IF NOT EXISTS "Playlists_Musics"(Playlist_id INT REFERENCES "Playlists"(Playlist_id), -- Sql para criar tabela "Playlists_Musics" com atributos Playlist_id (FOREIGN KEY), Music_id (FOREIGN KEY)
              Music_id INT REFERENCES "Musics"(Music_id));
