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
    private String email;
    private int n_musics;
    private int privacy; /* 0 -> publico 1 -> Privado*/

    public Playlist(int playlist_id, String titulo, String email, int n_musics, int privacy) {
        this.playlist_id = playlist_id;
        this.titulo = titulo;
        this.email = email;
        this.n_musics = n_musics;
        this.privacy = playlist_id;
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


}
