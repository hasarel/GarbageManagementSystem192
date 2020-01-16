package com.nibm.rwp.gms.dto;

public class RequestHistory {
    private String name;
    private String address;
    private String contacNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacNo() {
        return contacNo;
    }

    public void setContacNo(String contacNo) {
        this.contacNo = contacNo;
    }

    public RequestHistory(String name, String address, String contacNo) {
        this.name = name;
        this.address = address;
        this.contacNo = contacNo;
    }
}
