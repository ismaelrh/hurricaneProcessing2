import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ismaro3 on 22/07/16.
 */
public class DataObtainer {

    public static void main(String[] args){
        List<Hurricane> hurricanes  = getHurricaneList();
        for(Hurricane h: hurricanes){
            System.out.println(h.URI);
        }
    }




    public static List<Hurricane> getHurricaneList(){

        //1.- Obtener una lista de instancias de huracanes

        /*String query = "select ?instance ?abstract ?areas ?season ?label" +
                "{" +
                "?instance <http://dbpedia.org/ontology/abstract> ?abstract ." +
                "?instance <http://dbpedia.org/property/areas> ?areas ." +
                "?instance <http://dbpedia.org/property/hurricaneSeason> ?season ." +
                "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?label ." +
                "FILTER (lang(?abstract) = 'en')" +
                "FILTER (lang(?label) = 'en')" +
                "}";*/

        String obtenerHuracanes = "select DISTINCT ?instance { ?instance a <http://dbpedia.org/class/yago/Hurricane111467018>}";


        try{
            List<String> uriList = new ArrayList<String>();
            Scanner scanner = ejecutarConsultaSparql(obtenerHuracanes);
            scanner.next();

            while(scanner.hasNext()){
                String uri = scanner.next();
                uriList.add(uri.substring(1,uri.length()-1));
            }


            //2.- Ya tenemos la lista de URIs, ahora para cada uno obtenemos sus datos
            List<Hurricane> result = new ArrayList<>();
            for(String uri:uriList){
                result.add(getHurricane(uri));

            }

            return result;

        }
        catch(Exception ex){
            ex.printStackTrace();
        }



return null;


    }


    public static Hurricane getHurricane(String uri) throws IOException{

        String obtenerDatosHuracan = "select  ?abstract ?areas ?label" +
                "{" +
                "<" + uri + "> <http://dbpedia.org/ontology/abstract> ?abstract ." +
                "<" + uri + ">  <http://dbpedia.org/property/areas> ?areas ." +
                "<" + uri + ">  <http://www.w3.org/2000/01/rdf-schema#label> ?label ." +
                "FILTER (lang(?abstract) = 'en')" +
                "FILTER (lang(?label) = 'en')" +
                "}";

        Scanner detallado = ejecutarConsultaSparql(obtenerDatosHuracan);
        detallado.nextLine();

        Hurricane h = new Hurricane();
        if(detallado.hasNextLine()){
            //System.out.println(detallado.nextLine());
            String line = detallado.nextLine();
            String[] terms = line.split("\t");
            String abstract_ = null;
            String areas = null;
            String label = null;
            if(terms.length>0){
                abstract_ = terms[0];
            }
            if(terms.length>1){
                areas = terms[1];
            }
            if(terms.length>2){
                label = terms[2];
            }

            h.URI = uri;
            h.areas = areas;
            h.abstract_ = abstract_;
            h.label = label;


        }

        return h;
    }

    private static Scanner ejecutarConsultaSparql(String consulta) throws IOException {

        String url_path = "http://dbpedia.org/sparql/query?output=tsv6format=text%2Ftab-separated-values&query=" + URLEncoder.encode(consulta, "UTF-8");
        URL url = new URL(url_path);
        return new Scanner(url.openStream());


    }
}
