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

public class Playlist {

    private int playlist_id;
    private String titulo;
    private int n_musics;
    private int privacy; /* 0 -> publico 1 -> Privado*/
    private ArrayList<Music> musics;

    public Playlist(int playlist_id) {
        this.playlist_id = playlist_id;
        this.titulo = titulo;
        this.n_musics = 0;
        this.privacy = 0;
        this.musics = new ArrayList<>();
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getN_musics() {
        return n_musics;
    }

    public int getPrivacy() {
        return privacy;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void add_music(Music music){
        musics.add(music);
        this.n_musics++;
    }

}
