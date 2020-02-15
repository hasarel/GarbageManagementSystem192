package com.nibm.rwp.gms.dto;

public class CustomerRequest {
    private String customer_name;
    private String address_1;
    private String address_2;
    private String address_3;
    private String tele_no;
    private String longitude;
    private String latitude;

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    private String req_id;

    public CustomerRequest(String customer_name, String address_1, String address_2, String address_3, String tele_no, String longitude, String latitude) {
        this.customer_name = customer_name;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.address_3 = address_3;
        this.tele_no = tele_no;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CustomerRequest() {

    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    public String getTele_no() {
        return tele_no;
    }

    public void setTele_no(String tele_no) {
        this.tele_no = tele_no;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public  String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


}
