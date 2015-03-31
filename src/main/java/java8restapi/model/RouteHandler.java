package java8restapi.model;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.BiFunction;
import java.util.function.Function;

import static java8restapi.functions.StateTransferer.transferState;
import static java8restapi.functions.ResponseFormatter.createResponse;
import static java8restapi.functions.StateTransferValidator.validate;

public class RouteHandler {
    private Function<Request, StateTransfer> requestParserer;
    private BiFunction<StateTransfer, LandMap, StateTransfer> stateTransferValidator;
    private BiFunction<StateTransfer, LandMap, StateTransfer> stateTransferer;
    private BiFunction<StateTransfer, Response, String> validResponseFormatter;
    private BiFunction<StateTransfer, Response, String> invalidResponseFormatter;


    public RouteHandler(Function<Request, StateTransfer> requestParserer,
                        BiFunction<StateTransfer, LandMap, StateTransfer> stateTransferValidator,
                        BiFunction<StateTransfer, LandMap, StateTransfer> stateTransferer,
                        BiFunction<StateTransfer, Response, String> validResponseFormatter,
                        BiFunction<StateTransfer, Response, String> invalidResponseFormatter
    ){
        this.requestParserer = requestParserer;
        this.stateTransferValidator = stateTransferValidator;
        this.stateTransferer = stateTransferer;
        this.validResponseFormatter = validResponseFormatter;
        this.invalidResponseFormatter = invalidResponseFormatter;
    }

    public Route using(LandMap landMap) {
        return  (req, res)  -> {
            StateTransfer stateTransfer =  requestParserer.andThen(validate.apply(landMap, stateTransferValidator)).apply(req);
            if (stateTransfer.isValid()) {
                return transferState.apply(landMap, stateTransferer).andThen(createResponse.apply(res, validResponseFormatter)).apply(stateTransfer);
            } else {
                return createResponse.apply(res, invalidResponseFormatter).apply(stateTransfer);
            }
        };
    }
}
