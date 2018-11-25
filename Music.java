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

    private int upvotes;
    private int music_id;
    private int duration;
    private String title;
    private String artist;
    private String lyrics;
    private String launch_date;
    private String format;

    public Music(int music_id, String title, int duration, String launch_date, int upvotes, String lyrics, String format, String artist) {
        this.upvotes = upvotes;
        this.duration = duration;
        this.title = title;
        this.music_id = music_id;
        this.lyrics = lyrics;
        this.artist = artist;
        this.format = format;
        this.launch_date = launch_date;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getMusic_id() {
        return music_id;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getFormat() {
        return format;
    }
}

