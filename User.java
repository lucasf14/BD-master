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

public class User implements Serializable {

    private int editor; /* 0 -> normal , 1 -> editor */
    private String password;
    private String mail;
    private String name;

    public User(String user, String pass, String name){
        this.editor = 0;
        this.password = pass;
        this.mail = user;
        this.name = name;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public int getEditor() {
        return editor;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return mail;
    }

    public String getName() {
        return name;
    }
}
