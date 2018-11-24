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

public class Critics implements Serializable {

    private String critic;
    private int id;

    public Critics(String critic,int id) {
        this.critic = critic;
        this.id = id;
    }

    public void setCritic(String critic) {
        this.critic = critic;
    }

    public String getCritic() {
        return critic;
    }

    public int getId() {
        return id;
    }
}

