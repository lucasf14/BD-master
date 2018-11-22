/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ritag
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Artist implements Serializable {

    private String years_active;
    private String name;
    private List<Genre> genre;
    private List<Album> albums;
    private List<Music> musics;
    private String origin;

    public Artist(String years_active, String name, String origin) {
        this.years_active = years_active;
        this.name = name;
        this.origin = origin;
        this.genre = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.musics = new ArrayList<>();
    }


    public void addGenre(Genre genre) {
        this.genre.add(genre);
    }

    public void addAlbum(Album albums) {
        this.albums.add(albums);
    }

    public void addMusic(Music musics) {
        this.musics.add(musics);
    }


    public String getYears_active() {
        return years_active;
    }

    public String getName() {
        return name;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public String getOrigin() {
        return origin;
    }
}

