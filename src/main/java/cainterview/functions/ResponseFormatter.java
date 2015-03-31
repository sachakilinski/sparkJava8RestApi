package cainterview.functions;

import cainterview.model.StateTransfer;
import spark.Response;
import spark.ResponseTransformer;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ResponseFormatter {
    public static BiFunction<Response, BiFunction<StateTransfer, Response, String>,
            Function<StateTransfer,String>> createResponse = (res, f) -> s -> f.apply(s, res);

    public static BiFunction<StateTransfer, Response, String> createSiteResponse = (s,res) -> {
        res.status(200);
        res.body("site " + s.getData().get("name") + " created sucessifully.\n");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> notCreatedSiteResponse = (s,res) -> {
        res.status(400);
        res.body("site " + s.getData().get("name") + " could not be created.\n" + s.getErrorList());
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> renameSiteResponse = (s,res) -> {
        res.status(200);
        res.body("site " + s.getData().get("name") + " sucessifully renamed to " + s.getData().get("newName")+ ".");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> notRenamedSiteResponse = (s,res) -> {
        res.status(400);
        res.body("site " + s.getData().get("name") + " could not be renamed to " + s.getData().get("newName")+ ".\n"+ s.getErrorList());
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> deleteSiteResponse = (s,res) -> {
        res.status(200);
        res.body("site " + s.getData().get("name") + " deleted sucessifully.");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> notDeletedSiteResponse = (s,res) -> {
        res.status(400);
        res.body("site " + s.getData().get("name") + " could not be deleted.\n"+ s.getErrorList());
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> createConnectionResponse = (s,res) -> {
        res.status(200);
        res.body("connection from " + s.getData().get("name") + " to " + s.getData().get("othersite") + " created sucessifully.\n");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> notCreatedConnectionResponse = (s,res) -> {
        res.status(400);
        res.body("connection from " + s.getData().get("name") + " to " + s.getData().get("othersite") + " could not be created.\n"+ s.getErrorList()+ "\n");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> updateConnectionResponse = (s,res) -> {
        res.status(200);
        res.body("connection from " + s.getData().get("name") + " to " + s.getData().get("othersite") + " updated sucessifully.");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> notUpdateConnectionResponse = (s,res) -> {
        res.status(400);
        res.body("connection from " + s.getData().get("name") + " to " + s.getData().get("othersite") + " could not be updated.\n"+ s.getErrorList());
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> deleteConnectionResponse = (s,res) -> {
        res.status(200);
        res.body("connection from " + s.getData().get("name") + " to " + s.getData().get("othersite") + " deleted sucessifully.");
        return res.body();
    };

    public static BiFunction<StateTransfer, Response, String> notDeleteConnectionResponse = (s,res) -> {
        res.status(400);
        res.body("connection from " + s.getData().get("name") + " to " + s.getData().get("othersite") + " could not be deleted.\n"+ s.getErrorList());
        return res.body();
    };

}
