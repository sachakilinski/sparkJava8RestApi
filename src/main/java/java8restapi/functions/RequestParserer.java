package java8restapi.functions;

import java8restapi.init.BufferedReaderFactory;
import java8restapi.init.CsvParser;
import java8restapi.model.StateTransfer;
import spark.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequestParserer {
    public static  Function<Request, StateTransfer> parseCreateSite = req -> {
        Map map = new HashMap<>();
        map.put("name",req.queryParams("name"));
        return new StateTransfer(StateTransfer.operations.CREATE_SITE,map);
    };

    public static  List<StateTransfer> parseCreateSiteFromText(Request req) {
        CsvParser parser = new CsvParser(new BufferedReaderFactory().fromRawString(req.body()));
        List<List<String>> data = new ArrayList<List<String>>();
        data.addAll(parser.parseAndCloseReader());

        List<StateTransfer> stateTrasnferList = new ArrayList<>();
        stateTrasnferList.add(new StateTransfer(StateTransfer.operations.CREATE_SITE, new HashMap<>()));
        String name =  data.get(0).get(0);
        stateTrasnferList.get(0).getData().put("name", name);
        stateTrasnferList.addAll(data.stream().skip(1).map(line -> {
            Map map = new HashMap();
            map.put("name", name);
            map.put("othersite", line.get(0));
            map.put("distance", line.get(1));
            return new StateTransfer(StateTransfer.operations.CREATE_CONNECTION, map);
        }).collect(Collectors.toList()));
        return stateTrasnferList;
    }

    public static  Function<Request, StateTransfer> parseRenameSite = req -> {
        Map map = new HashMap<>();
        map.put("name",req.params(":name"));
        map.put("newName",req.queryParams("newName"));
        return new StateTransfer(StateTransfer.operations.RENAME_SITE,map);
    };

    public static  Function<Request, StateTransfer> parseDeleteSite = req -> {
        Map map = new HashMap<>();
        map.put("name",req.params(":name"));
        return new StateTransfer(StateTransfer.operations.DELETE_SITE,map);
    };

    public static  Function<Request, StateTransfer> parseCreateConnection = req -> {
        Map map = new HashMap<>();
        map.put("name",req.params(":name"));
        map.put("othersite",req.queryParams("othersite"));
        map.put("distance",req.queryParams("distance"));
        return new StateTransfer(StateTransfer.operations.CREATE_CONNECTION,map);
    };

    public static  Function<Request, StateTransfer> parseUpdateConnection = req -> {
        Map map = new HashMap<>();
        map.put("name",req.params(":name"));
        map.put("othersite",req.params(":othersite"));
        map.put("distance",req.queryParams("distance"));
        return new StateTransfer(StateTransfer.operations.UPDATE_CONNECTION,map);
    };

    public static  Function<Request, StateTransfer> parseDeleteConnection = req -> {
        Map map = new HashMap<>();
        map.put("name",req.params(":name"));
        map.put("othersite",req.params(":othersite"));
        return new StateTransfer(StateTransfer.operations.DELETE_CONECTION,map);
    };
}
