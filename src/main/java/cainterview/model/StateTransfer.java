package cainterview.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StateTransfer {
    private operations operation;
    private Map data;
    private List<errors> errorList = new ArrayList<errors>();

    public static enum operations { CREATE_SITE, RENAME_SITE, DELETE_SITE, CREATE_CONNECTION, UPDATE_CONNECTION, DELETE_CONECTION};

    public static enum errors { NULL_SITE, NOT_EXISTING_SITE, EXISTING_SITE, NOT_EXISTING_OTHERSITE, EXISTING_OTHERSITE,
        NOT_EXISTING_CONNECTION, EXISTING_CONNECTION, SELF_CONNECTION, NOT_POSIITVE_DISTANCE
    };


    public StateTransfer(operations operation, Map data){
        this.data = data;
        this.operation = operation;
    }

    public boolean isValid(){
        return this.errorList.isEmpty();
    }

    public List<errors> getErrorList() {
        return errorList;
    }

    public void addError(errors error) {
        this.errorList.add(error);
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public operations getOperation() {
        return operation;
    }

    public void setOperation(operations operation) {
        this.operation = operation;
    }
}
