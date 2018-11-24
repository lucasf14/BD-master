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

public class Band extends Artist implements Serializable {

    private List<Artist> members;

    public Band(String years_active, String name, String origin, String ar_name, List<Artist> members, String biografy) {
        super(years_active, name, origin, ar_name, biografy);
        this.members = new ArrayList<>();
    }

    public void addMembers(Artist members) {
        this.members.add(members);
    }

    public List<Artist> getMenbers() {
        return members;
    }
}
