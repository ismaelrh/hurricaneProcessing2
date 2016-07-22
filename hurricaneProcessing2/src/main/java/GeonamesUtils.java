import org.geonames.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b3j90 on 21/07/16.
 */
public class GeonamesUtils {





    public static void getChildren(String nombre){
        WebService.setUserName("piraces");
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();

        searchCriteria.setStyle(Style.FULL);
        ToponymSearchResult searchResult;
        Location foundLocation = new Location();

        try {
            searchResult = WebService.search(searchCriteria);
            List<Toponym> toponyms = searchResult.getToponyms();

            // Gets the first toponym in search (best match)
            if (toponyms.size() > 0) {
                Toponym toponym = toponyms.get(0);
              

                /*if(toponym.getAdminName1() != null && toponym.getAdminName1().length()>1) {
                    // Split feature class name to get the first class
                    location2 = new Location(location, toponym.getCountryName(), toponym.getAdminName1(),
                            toponym.getFeatureClassName(), toponym.getLatitude(),
                            toponym.getLongitude());
                } else {
                    location2 = null;
                }*/
            } else {
                //location2 = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /*public static Location getData(String location){
        ArrayList<String> retrieved = mongo.retrieveGeonames(location);
        if(retrieved != null){
            return new Location(location, retrieved.get(0), retrieved.get(1), retrieved.get(2),
                    new Double(retrieved.get(3)), new Double(retrieved.get(4)));
        } else {
            // User enabled for consulting the Geonames API
            WebService.setUserName("piraces");

            ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
            searchCriteria.setQ(location);
            searchCriteria.setStyle(Style.FULL);
            ToponymSearchResult searchResult;
            Location location2 = new Location();

            try {
                searchResult = WebService.search(searchCriteria);
                List<Toponym> toponyms = searchResult.getToponyms();
                // Gets the first toponym in search (best match)
                if (toponyms.size() > 0) {
                    Toponym toponym = toponyms.get(0);
                    if(toponym.getAdminName1() != null && toponym.getAdminName1().length()>1) {
                        // Split feature class name to get the first class
                        location2 = new Location(location, toponym.getCountryName(), toponym.getAdminName1(),
                                toponym.getFeatureClassName(), toponym.getLatitude(),
                                toponym.getLongitude());
                    } else {
                        location2 = null;
                    }
                } else {
                    location2 = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(location2!=null) {
                mongo.insertGeonames(location2);
            }
            return location2;
        }
    }*/
}
