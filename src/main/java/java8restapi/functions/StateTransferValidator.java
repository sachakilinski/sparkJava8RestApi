package java8restapi.functions;

import java8restapi.model.StateTransfer;
import java8restapi.model.LandMap;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class StateTransferValidator {

    private static BiConsumer<StateTransfer,LandMap> notNullSite = (s,l) -> {
        Optional<String> name = Optional.of((String) s.getData().get("name"));
        if(!name.isPresent())
            s.addError(StateTransfer.errors.NULL_SITE);
    };

    private static Function<String,BiConsumer<StateTransfer,LandMap>> existingSiteFunction = field -> (s,l) -> {
        Optional<String> name = Optional.of((String)s.getData().get(field));
        if(!l.getAdjacenciesMap().containsKey(name.get()))
            if (field.equals("site"))
                s.addError(StateTransfer.errors.NOT_EXISTING_SITE);
            else
                s.addError(StateTransfer.errors.NOT_EXISTING_OTHERSITE);
    };

    private static Function<String,BiConsumer<StateTransfer,LandMap>> notExistingSiteFunction = field -> (s,l) -> {
        Optional<String> name = Optional.of((String)s.getData().get(field));
        if(l.getAdjacenciesMap().containsKey(name.get()))
            if (field.equals("site"))
                s.addError(StateTransfer.errors.EXISTING_SITE);
            else
                s.addError(StateTransfer.errors.EXISTING_OTHERSITE);
    };

    private static BiConsumer<StateTransfer,LandMap> existingSite = existingSiteFunction.apply("name");
    private static BiConsumer<StateTransfer,LandMap> existingOtherSite = existingSiteFunction.apply("othersite");
    private static BiConsumer<StateTransfer,LandMap> notExistingNewName = notExistingSiteFunction.apply("newName");
    private static BiConsumer<StateTransfer,LandMap> notExistingSite = notExistingSiteFunction.apply("name");

    private static BiConsumer<StateTransfer,LandMap> existingConnection = (s,l) -> {
        Optional<String> name = Optional.of((String) s.getData().get("name"));
        Optional<String> othersite = Optional.of((String) s.getData().get("othersite"));
        Optional<Map> site = Optional.ofNullable((Map) l.getAdjacenciesMap().get(name.get()));
        if(site.isPresent() && !site.get().containsKey(othersite.get()))
            s.addError(StateTransfer.errors.NOT_EXISTING_CONNECTION);
    };

    private static BiConsumer<StateTransfer,LandMap> notExistingConnection = (s,l) -> {
        Optional<String> name = Optional.of((String) s.getData().get("name"));
        Optional<String> othersite = Optional.of((String) s.getData().get("othersite"));
        Optional<Map> site = Optional.ofNullable((Map) l.getAdjacenciesMap().get(name.get()));
        if(site.isPresent() && site.get().containsKey(othersite.get()))
            s.addError(StateTransfer.errors.EXISTING_CONNECTION);
    };

    private static BiConsumer<StateTransfer,LandMap> positiveDistance = (s,l) -> {
        Optional<String> distance = Optional.of((String) s.getData().get("distance"));
        try{
            Double d = new Double(distance.get().toString());
            if(! (d > 0))
                s.addError(StateTransfer.errors.NOT_POSIITVE_DISTANCE);
        } catch(Exception e){
            s.addError(StateTransfer.errors.NOT_POSIITVE_DISTANCE);
        }
    };

    private static BiConsumer<StateTransfer,LandMap> notSelfConnection = (s,l) -> {
        Optional<String> name = Optional.of((String) s.getData().get("name"));
        Optional<String> othersite = Optional.of((String) s.getData().get("othersite"));
        if (name.get().equals(othersite.get()))
            s.addError(StateTransfer.errors.SELF_CONNECTION);
    };

    public static BiFunction<LandMap,
            BiFunction<StateTransfer, LandMap,StateTransfer>, Function<StateTransfer,
            StateTransfer>> validate =
            (l, f) -> s -> f.apply(s,l);

    public static BiFunction<StateTransfer, LandMap, StateTransfer> validateCreateSite = (s,l) -> {
        notNullSite.andThen(notExistingSite).accept(s, l);
        return s;
    };

    public static BiFunction<StateTransfer, LandMap, StateTransfer> validateDeleteSite = (s,l) -> {
        existingSite.accept(s, l);
        return s;
    };

    public static BiFunction<StateTransfer, LandMap, StateTransfer> validateRenameSite = (s,l) -> {
        existingSite.andThen(notExistingNewName).accept(s, l);
        return s;
    };

    public static BiFunction<StateTransfer, LandMap, StateTransfer> validateCreateConnection = (s,l) -> {
        existingSite.andThen(notSelfConnection).andThen(existingOtherSite).andThen(notExistingConnection).andThen(positiveDistance).accept(s, l);
        return s;
    };

    public static BiFunction<StateTransfer, LandMap, StateTransfer> validateUpdateConnection = (s,l) -> {
        existingSite.andThen(existingOtherSite).andThen(existingConnection).andThen(positiveDistance).accept(s, l);
        return s;
    };

    public static BiFunction<StateTransfer, LandMap, StateTransfer> validateDeleteConnection = (s,l) -> {
        existingSite.andThen(existingOtherSite).andThen(existingConnection).accept(s, l);
        return s;
    };
}
