/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author: Kalinka
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Music implements Serializable {

    private List<Critics> critics;
    private List<Genre> genre;
    private int upvotes;
    private String time;
    private Album album;
    private String title;
    private Artist artist;
    private Artist composer;
    private String lyrics;
    private String launch_date;

    public Music(String time, String title, Artist artist, Artist composer, String lyrics, String launch_date, Album album) {
        this.upvotes = 0;
        this.time = time;
        this.title = title;
        this.artist = artist;
        this.composer = composer;
        this.lyrics = lyrics;
        this.album = album;
        this.launch_date = launch_date;
        this.genre = new ArrayList<>();
        this.critics = new ArrayList<>();
    }

    public void setCritics(Critics critics) {
        this.critics.add(critics);
    }

    public void setUpvotes() {
        this.upvotes += upvotes;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setComposer(Artist composer) {
        this.composer = composer;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setGenre(Genre genre) {
        this.genre.add(genre);
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }

    public List<Critics> getCritics() {
        return critics;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Artist getComposer() {
        return composer;
    }

    public String getLyrics() {
        return lyrics;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public String getLaunch_date() {
        return launch_date;
    }
}

