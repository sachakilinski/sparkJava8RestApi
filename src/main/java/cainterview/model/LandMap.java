package cainterview.model;

import cainterview.init.LandMapCsvInitializer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LandMap<K,W> {
    private Map<K, Map<K, W>> adjacenciesMap;

    public LandMap() {
        this.adjacenciesMap = new HashMap<K, Map<K, W>>();
    }

    public LandMap(String sitesPath, String connectionsPath){
        this.adjacenciesMap = new LandMapCsvInitializer<K,W>(sitesPath, connectionsPath).init().getAdjacenciesMap();
    }

    public Void createSite(K k) {
        adjacenciesMap.put(k, new HashMap<K, W>());
        return null;
    }

    public Void createConnection(K from, K to, W weight) {
        Map<K, W> fromConnections = adjacenciesMap.get(from);
        Map<K, W> toConnections = adjacenciesMap.get(to);

        fromConnections.put(to, weight);
        toConnections.put(from, weight);
        return null;
    }

    public Void updateConnection(K from, K to, W weight) {
        Map<K, W> fromConnections = adjacenciesMap.get(from);
        Map<K, W> toConnections = adjacenciesMap.get(to);

        fromConnections.replace(to, weight);
        toConnections.replace(from, weight);
        return null;
    }

    public Void deleteConnection(K from, K to) {
        Map<K, W> fromConnections = adjacenciesMap.get(from);
        Map<K, W> toConnections = adjacenciesMap.get(to);

        fromConnections.remove(to);
        toConnections.remove(from);
        return null;
    }

    public Void renameSite(K site, K newSite){
        Map<K,W> siteConnections = adjacenciesMap.get(site);
        adjacenciesMap.entrySet().stream()
                .map(replaceKeyName(site, newSite))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        adjacenciesMap.put(newSite, siteConnections);
        adjacenciesMap.remove(site);
        return null;
    }

    public Void deleteSite(K site){
        adjacenciesMap.entrySet().stream()
                .map(removeSite(site))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        adjacenciesMap.remove(site);
        return null;
    }

    public Map<K, Map<K, W>> getAdjacenciesMap() {
        return adjacenciesMap;
    }

    private Function<Map.Entry<K,Map<K,W>>,Map.Entry<K,Map<K,W>>> replaceKeyName(K oldKey, K newKey) {
        return p -> {
            W value = p.getValue().get(oldKey);
            if (value != null){
                p.getValue().put(newKey, value);
                p.getValue().remove(oldKey);
            }
          return p;
        };
    }

    private Function<Map.Entry<K,Map<K,W>>,Map.Entry<K,Map<K,W>>> removeSite(K site) {
        return p -> {p.getValue().remove(site);
        return p;
        };
    }

}