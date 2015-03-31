package java8restapi.init;

import java8restapi.model.LandMap;

public class Initializer {
    public static LandMap initialize(String mode){
        LandMap landMap;
        if(mode != "test"){
            landMap = new LandMap("/JavaCodingTestData-sites.csv","/JavaCodingTestData-distances.csv");
        } else{
            landMap = new LandMap();
            landMap.createSite("from");
            landMap.createSite("to");
            landMap.createConnection("from","to", 1.0);
            landMap.createSite("other");
        }
        return landMap;
    }
}
