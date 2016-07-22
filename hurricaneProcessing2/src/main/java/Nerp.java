import java.util.List;
import java.util.Set;

/**
 * Created by b3j90 on 22/07/16.
 */
public class Nerp {

    static NERProcessing ner = new NERProcessing();

    public static Set<String> getLocations(String text){
        String anotado = ner.process(text);
        return Utils.getLocations(anotado);
    }



}
