/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author: Kalinka
 */
import java.util.ArrayList;


public class Label {

    private String label_name;
    private ArrayList<Album> albums;
    private ArrayList<Music> musics;

    public Label(String label_name) {
        this.albums = new ArrayList<>();
        this.musics = new ArrayList<>();
        this.label_name = label_name;
    }

    public void insert_music(Music music){
        this.musics.add(music);
    }

    public void insert_albums(Album album){
        this.albums.add(album);
    }


}



