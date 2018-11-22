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

public class Album implements Serializable {

    private List<Music> setlist;
    private List<Critics> critics;
    private List<Genre> genres;
    private List<Artist> producers;
    private Artist artist;
    private String title;
    private String launch;

    public Album(Artist artist, String title, String launch) {
        this.setlist = new ArrayList<>();
        this.critics = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.artist = artist;
        this.title = title;
        this.launch = launch;
    }

    public void addProducer(Artist artist) {
        this.producers.add(artist);
    }

    public void addGenres(Genre genre) {
        this.genres.add(genre);
    }

    public void addMusic(Music music) {
        this.setlist.add(music);
    }

    public void addCritic(Critics critics) {
        this.critics.add(critics);
    }

    public List<Music> getSetlist() {
        return setlist;
    }

    public List<Critics> getCritics() {
        return critics;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Artist> getProducers() {
        return producers;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getLaunch() {
        return launch;
    }
}
