/**
 *
 * @author: Kalinka
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Artist implements Serializable {

    public String band;
    private String artistic_name;
    private String name;
    private String origin;
    private int active_years;
    private String biography;
    private String concertos;

    public Artist(String artistic_name, String name, String origin, int active_years, String biography, String concertos) {
        this.artistic_name = artistic_name;
        this.name = name;
        this.origin = origin;
        this.active_years = active_years;
        this.biography = biography;
        this.concertos = concertos;
    }

    public String getArtistic_name() {
        return artistic_name;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public int getActive_years() {
        return active_years;
    }

    public String getBiography() {
        return biography;
    }

    public String getConcertos() {
        return concertos;
    }
}
