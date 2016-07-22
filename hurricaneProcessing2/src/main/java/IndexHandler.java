import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by ismaro3 on 22/07/16.
 */
public class IndexHandler {

    public static void main(String[] args){
        IndexHandler h = new IndexHandler();
        //h.generateIndex();
    }


    MongoCollection<Document> collection;
    public IndexHandler(){
        MongoClient mongoClient = new MongoClient( "localhost" );
        MongoDatabase database = mongoClient.getDatabase("teamzgz");
        collection = database.getCollection("index");
    }


    public void generateIndex(){
        List<Hurricane> hurricanes = DataObtainer.getHurricaneList();
        System.out.println("Generating index...");
        int i = 0;
        for(Hurricane h: hurricanes){


            i++;
            Set<String> abstractLocations = Nerp.getLocations(h.abstract_);
            Set<String> areasLocations = Nerp.getLocations(h.areas);
            for(String area: areasLocations){
                abstractLocations.add(area); //abstractLocations tiene todas las localizaciones
            }

            for(String location: abstractLocations){
                appendToIndex(normalizeLocation(location),h.URI);
            }

            if(i%100==0){
                System.out.println("processed " + i + " out of " + hurricanes.size());
            }

        }
    }
    public void appendToIndex(String location, String uri){

        location = normalizeLocation(location);

        //First, query if it exists
        Document existing = collection.find(eq("name", location)).first();

        if(existing!=null){//Existe, se actualiza (coge y actualiza)

            List<String> hurricanes = (List<String>)existing.get("hurricanes");
            hurricanes.add(uri);
            //existing.put("hurricanes",hurricanes);
            collection.updateOne(new Document("name", location),
                    new Document("$set", new Document("hurricanes",hurricanes)));

            //collection.updateOne(new BasicDBObject().append("hurricanes",hurricanes), new BasicDBObject(existing));
        }
        else{ //New
            List<String> lista = new ArrayList<String>();
            lista.add(uri);
            Document newDoc = new Document("name",location)
                    .append("hurricanes", lista);

            collection.insertOne(newDoc);
        }



    }

    public String normalizeLocation(String location){
        location = location.toLowerCase();
        location = location.replaceAll(" ","");
        location = location.replaceAll(".","");
        location = location.replaceAll("-","");
        location = location.replaceAll("_","");
        return location;
    }

    public  List<String> retrieveFromIndex(String location){


        Document existing = collection.find(eq("name", location)).first();

        if(existing!=null){
            return (List<String>) existing.get("hurricanes");
        }
        else{
            return new ArrayList<String>();
        }

    }
}
