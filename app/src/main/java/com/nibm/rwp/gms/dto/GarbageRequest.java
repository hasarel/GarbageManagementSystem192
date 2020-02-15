package com.nibm.rwp.gms.dto;

public class GarbageRequest {

    private String customer_name;
    private String email;
    private int area_id;
    private String  longitude;
    private String  latitude;
    private String address_1;
    private String address_2;
    private String address_3;
    private String tele_no;
    private String description;
    private int category_id;
    private int vehicle_type_id;
    private int user_id;


    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public GarbageRequest(){

    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public GarbageRequest( String customer_name, String email, int area_id, String longitude, String latitude,
                          String address_1, String address_2, String address_3, String tele_no, String description,
                           int category_id, int vehicle_type_id) {
        this.customer_name = customer_name;
        this.email = email;
        this.area_id = area_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.address_3 = address_3;
        this.tele_no = tele_no;
        this.description = description;
        this.category_id = category_id;
        this.vehicle_type_id = vehicle_type_id;
    }
}
