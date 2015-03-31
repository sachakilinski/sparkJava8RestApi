package java8restapi.functions;

import java8restapi.model.StateTransfer;
import java8restapi.model.LandMap;

import java.util.function.BiFunction;
import java.util.function.Function;

public class StateTransferer {
    public static BiFunction<LandMap, BiFunction<StateTransfer, LandMap, StateTransfer>, Function<StateTransfer, StateTransfer>> transferState = (l,f) -> s -> f.apply(s, l) ;

    public static BiFunction<StateTransfer, LandMap, StateTransfer> createSite =  (s,l)-> { l.createSite(s.getData().get("name")); return s;};

    public static  BiFunction<StateTransfer, LandMap, StateTransfer> deleteSite=  (s,l) -> { l.deleteSite(s.getData().get("name")); return s;};

    public static  BiFunction<StateTransfer, LandMap, StateTransfer> renameSite = (s,l) -> {l.renameSite(s.getData().get("name"), s.getData().get("newName")); return s;};

    public static  BiFunction<StateTransfer, LandMap, StateTransfer> createConnection =(s,l) -> { l.createConnection(s.getData().get("name"), s.getData().get("othersite"), s.getData().get("distance")); return s;};

    public static  BiFunction<StateTransfer, LandMap, StateTransfer> updateConnection =(s,l) -> { l.updateConnection(s.getData().get("name"), s.getData().get("othersite"), s.getData().get("distance")); return s;};

    public static  BiFunction<StateTransfer, LandMap, StateTransfer> deleteConnection = (s,l) -> { l.deleteConnection(s.getData().get("name"), s.getData().get("othersite")); return s;};

}
