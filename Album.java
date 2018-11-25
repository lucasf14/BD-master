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

public class Album implements Serializable {

    public String artist;
    private int album_id;
    private String title;
    private String launch;
    private int duration;
    private int track_number;

    public Album(int id,  String title, int duration, String launch, int trackNumber) {
        this.album_id = id;
        this.duration = duration;
        this.title = title;
        this.launch = launch;
        this.track_number= trackNumber;
    }


    public String getTitle() {
        return title;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public int getDuration() {
        return duration;
    }

    public int getTrackNumber() {
        return track_number;
    }

    public String getLaunch() {
        return launch;
    }
}