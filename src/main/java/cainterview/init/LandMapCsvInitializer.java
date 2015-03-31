package cainterview.init;

import cainterview.model.LandMap;

import java.util.List;

public class LandMapCsvInitializer<K,W> {
    private String sitesPath, connectionsPath;

    public LandMapCsvInitializer(String sitesPath, String connectionPath){
        this.sitesPath = sitesPath;
        this.connectionsPath = connectionPath;
    }

    public LandMap init(){
        LandMap<K, W> landMap = new LandMap<K, W>();
        try{
            createConnections(
                    createSites(landMap,
                            new CsvParser(new BufferedReaderFactory().fromFile(sitesPath))),
                    new CsvParser(new BufferedReaderFactory().fromFile(connectionsPath)));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return landMap;
    }

    private LandMap<K, W> createSites(LandMap<K, W> landMap, CsvParser parser) {
        List<List<String>> sites = parser.parseAndCloseReader();
        sites.stream().forEach(l -> landMap.createSite((K) l.get(0)));
        return landMap;
    }

    private LandMap<K, W> createConnections(LandMap<K, W> landMap, CsvParser parser){
        List<List<String>> connections = parser.parseAndCloseReader();
        connections.stream().forEach(l -> landMap.createConnection((K) l.get(0), (K) l.get(2), (W) (l.get(1))));
        return landMap;
    }
}
