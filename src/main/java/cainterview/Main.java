package cainterview;

import cainterview.init.Initializer;
import cainterview.model.LandMap;
import cainterview.model.RouteHandler;
import cainterview.model.StateTransfer;
import com.google.gson.Gson;

import java.util.List;

import static cainterview.functions.RequestParserer.*;
import static cainterview.functions.StateTransferer.*;
import static cainterview.functions.ResponseFormatter.*;
import static cainterview.functions.StateTransferValidator.*;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0] : null;
        LandMap landMap = Initializer.initialize(mode);

        put("/sites/:name", new RouteHandler(parseRenameSite, validateRenameSite, renameSite, renameSiteResponse, notRenamedSiteResponse).using(landMap));

        get("/sites/:name", (req, res) -> landMap.getAdjacenciesMap().get(req.params(":name")));

        post("/sites/:name/connections", new RouteHandler(parseCreateConnection, validateCreateConnection, createConnection, createConnectionResponse, notCreatedConnectionResponse).using(landMap));

        put("/sites/:name/connections/:othersite", new RouteHandler(parseUpdateConnection, validateUpdateConnection, updateConnection, updateConnectionResponse, notUpdateConnectionResponse).using(landMap));

        delete("/sites/:name/connections/:othersite", new RouteHandler(parseDeleteConnection, validateDeleteConnection, deleteConnection, deleteConnectionResponse, notDeleteConnectionResponse).using(landMap));

        delete("/sites/:name", new RouteHandler(parseDeleteSite, validateDeleteSite, deleteSite, deleteSiteResponse, notDeletedSiteResponse).using(landMap));

        get("/sites", (req, res) -> new Gson().toJson(landMap.getAdjacenciesMap().keySet()));

        post("/sites", (req, res) -> {
            if (req.contentType().contains("urlencoded")) {
                return new RouteHandler(parseCreateSite, validateCreateSite, createSite, createSiteResponse, notCreatedSiteResponse).using(landMap).handle(req,res);
            }

            //expecting raw text with new site + connections.
            else {
                String body = "";
                List<StateTransfer> stateTransfers = parseCreateSiteFromText(req);
                validate.apply(landMap, validateCreateSite).apply(stateTransfers.get(0));
                if (stateTransfers.get(0).isValid()) {
                     body += transferState.apply(landMap, createSite).andThen(createResponse.apply(res, createSiteResponse)).apply(stateTransfers.get(0));
                } else {
                     body += createResponse.apply(res, notCreatedSiteResponse).apply(stateTransfers.get(0));
                }
                String name = (String) stateTransfers.get(0).getData().get("name");
                landMap.createSite(name);
                for (StateTransfer s : stateTransfers.subList(1, stateTransfers.size()))
                {
                    validate.apply(landMap, validateCreateConnection).apply(s);
                    if (s.isValid()) {
                        body += transferState.apply(landMap, createConnection).andThen(createResponse.apply(res, createConnectionResponse)).apply(s);
                    } else {
                        body += createResponse.apply(res, notCreatedConnectionResponse).apply(s);
                    }
                };
                return body;
            }
        });

    }



}
