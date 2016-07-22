import com.google.gson.Gson;
import org.geonames.Toponym;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static spark.Spark.*;

/**
 * Created by ismaro3 on 22/07/16.
 */
public class Main {


    public static Gson gson = new Gson();
    public static void main(String[] args){

        port(8080);

        staticFileLocation("/web");


        before((request, response) -> response.type("application/json"));

        get("/hurricanes", (req, res) -> {
            Set<String> params = req.queryParams();
            String name ="";
            Integer season = null;
            for(String param: params){

                if (param.equalsIgnoreCase("name")) {

                    name = req.queryParamsValues(param)[0];

                }




            }

            if(name==null || name.length()==0){
                return "Error, please add at least name";
            }
            else{
                return gson.toJson(hurricanesForPlace(name));


                // return DataRetriever.getHurricanesForKeywords(new String[]{name,season});
            }




        });

        IndexHandler handler = new IndexHandler();
        handler.generateIndex();

    }
    public static Set<Hurricane> hurricanesForPlace(String place){


        //First, we get all the areas
        System.out.println("Getting all places");
        List<Toponym> areas = GeonamesUtils.getChildren(place);

        //Si indicen o construido, construir



        Set<Hurricane> huracanes = new HashSet<>();

        IndexHandler handler = new IndexHandler();
        //Para cada area, sacamos los huracanes

        Set<String> huracanesToCheck = new HashSet<>();

        for(Toponym area: areas){

            System.out.println("Retrieving huracans for area " + area.getName());
            List<String> retrieved = handler.retrieveFromIndex(area.getName());
            for(String uri: retrieved){
                huracanesToCheck.add(uri);
                //Cogemos los huracanes completos


            }

        }

        for(String toCheck: huracanesToCheck){
            try{
                System.out.println("Obtaining information of one huracan " + toCheck + " of " + huracanesToCheck.size());
                Hurricane huracan = DataObtainer.getHurricane(toCheck);
                huracan.abstract_ = "";
                huracan.areas = "";
                huracanes.add(huracan);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return huracanes;


    }
}
