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

public class User implements Serializable {

    private boolean editor; /* 0 -> normal , 1 -> editor */
    private String password;
    private String mail;
    private List<Music> playlist;
    private List<String> OfflineMess;

    public User(String user, String pass){
        this.editor = false;
        this.password = pass;
        this.mail = user;
        this.playlist = new ArrayList<>();
        this.OfflineMess = new ArrayList<>();
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public void addToPlaylist(Music music) {
        this.playlist.add(music);
    }

    public boolean getEditor() {
        return editor;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return mail;
    }

    public List<Music> getPlaylist() {
        return playlist;
    }

    public List<String> getOfflineMess() {
        return OfflineMess;
    }

    public void setOfflineMess(String offlineMess) {
        OfflineMess.add(offlineMess);
    }
}
