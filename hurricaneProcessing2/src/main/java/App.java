import java.util.List;

/**
 * Created by b3j90 on 22/07/16.
 */
public class App {

    public static void main(String[] args){
        NERProcessing ner = new NERProcessing();
        List<Hurricane> hurricanes = DataObtainer.getHurricaneList();

        for (Hurricane hurricane: hurricanes){
            System.out.println(ner.process(hurricane.abstract_));
        }
    }

}
