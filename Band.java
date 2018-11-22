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

public class Band extends Artist implements Serializable {

    private List<Artist> members;

    public Band(String years_active, String name, String origin) {
        super(years_active, name, origin);
        this.members = new ArrayList<>();
    }

    public void addMembers(Artist members) {
        this.members.add(members);
    }

    public List<Artist> getMenbers() {
        return members;
    }
}
