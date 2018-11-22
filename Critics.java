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

public class Critics implements Serializable {

    private String critic;

    public Critics(String critic) {
        this.critic = critic;
    }

    public void setCritic(String critic) {
        this.critic = critic;
    }

    public String getCritic() {
        return critic;
    }
}

