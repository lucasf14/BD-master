/**
 *
 * @author: Kalinka
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Music implements Serializable {

    private int upvotes;
    private int time;
    private int id;
    private String title;
    private String lyrics;
    private String format;
    private String artist;
    private String launch_date;

    public Music(int id, String title, int time, String launch_date, int upvotes, String lyrics, String format, String artist) {
        this.upvotes = upvotes;
        this.time = time;
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.launch_date = launch_date;
        this.id = id;
        this.format = format;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getFormat() {
        return format;
    }

    public String getArtist() {
        return artist;
    }

    public String getLaunch_date() {
        return launch_date;
    }
}

