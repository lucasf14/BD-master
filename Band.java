/**
 *
 * @author: Kalinka
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Band extends Artist implements Serializable {


    public Band(int years_active, String name, String origin, String ar_name, String biography, String concertos) {
        super(ar_name, name, origin,years_active, biography, concertos);
    }

}
