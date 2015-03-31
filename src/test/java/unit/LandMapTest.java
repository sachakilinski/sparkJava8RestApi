package unit;

import java8restapi.model.LandMap;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class LandMapTest {
    @Test
    public void createSiteTest(){
        LandMap<String, Double> landMap = new LandMap<String, Double>();
        landMap.createSite("Site");
        assertNotNull(landMap.getAdjacenciesMap().get("Site"));
    }

    @Test
    public void createConnectionTest(){
        LandMap<String, Double> landMap = new LandMap<String, Double>();
        landMap.createSite("From");
        landMap.createSite("To");
        landMap.createConnection("From", "To", 1.0);
        assertEquals(new HashMap<String, Double>(){{put("To",1.0);}}, landMap.getAdjacenciesMap().get("From"));
        assertEquals(new HashMap<String, Double>(){{put("From",1.0);}}, landMap.getAdjacenciesMap().get("To"));

    }

    @Test
    public void updateConectionTest(){
        LandMap<String, Double> landMap = new LandMap<String, Double>();
        landMap.createSite("From");
        landMap.createSite("To");
        landMap.createConnection("From", "To", 1.0);
        landMap.updateConnection("From", "To", 2.0);
        assertEquals(new HashMap<String, Double>(){{put("To",2.0);}}, landMap.getAdjacenciesMap().get("From"));
        assertEquals(new HashMap<String, Double>(){{put("From",2.0);}}, landMap.getAdjacenciesMap().get("To"));

    }

    @Test
    public void deleteConectionTest(){
        LandMap<String, Double> landMap = new LandMap<String, Double>();
        landMap.createSite("From");
        landMap.createSite("To");
        landMap.createConnection("From", "To", 1.0);
        landMap.deleteConnection("From", "To");
        assertEquals(null , landMap.getAdjacenciesMap().get("To").get("From"));
        assertEquals(null , landMap.getAdjacenciesMap().get("From").get("To"));
    }

    @Test
    public void renameSite(){
        LandMap<String, Double> landMap = new LandMap<String, Double>();
        landMap.createSite("Site");
        landMap.createSite("ConnectedSite");
        landMap.createConnection("Site", "ConnectedSite", 1.0);
        landMap.renameSite("Site", "NewSite");
        assertNotNull(landMap.getAdjacenciesMap().get("NewSite"));
        assertEquals(null, landMap.getAdjacenciesMap().get("Site"));
        assertEquals(new HashMap<String, Double>(){{put("NewSite",1.0);}}, landMap.getAdjacenciesMap().get("ConnectedSite"));
        assertEquals(new HashMap<String, Double>(){{put("ConnectedSite",1.0);}}, landMap.getAdjacenciesMap().get("NewSite"));
    }

    @Test
    public void deleteSiteTest(){
        LandMap<String, Double> landMap = new LandMap<String, Double>();
        landMap.createSite("From");
        landMap.createSite("To");
        landMap.createConnection("From", "To", 1.0);
        landMap.deleteSite("From");
        assertEquals(null, landMap.getAdjacenciesMap().get("From"));
        assertEquals(null , landMap.getAdjacenciesMap().get("To").get("From"));
    }

}
