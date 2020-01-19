package com.nibm.rwp.gms.dto;

public class UcVehicleList {
    private String id;
    private String type_code;

    public UcVehicleList(String id, String type_code) {
        this.id = id;
        this.type_code = type_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }


}
