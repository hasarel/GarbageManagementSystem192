package com.nibm.rwp.gms.dto;

public class GarbageRequest {
    private String category;
    private String description;
    private String longtiute;
    private String latitiute;
    private String name;
    private String area;
    private String address1;
    private String address2;
    private String address3;
    private String contact;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongtiute() {
        return longtiute;
    }

    public void setLongtiute(String longtiute) {
        this.longtiute = longtiute;
    }

    public String getLatitiute() {
        return latitiute;
    }

    public void setLatitiute(String latitiute) {
        this.latitiute = latitiute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public GarbageRequest(String category, String description, String longtiute, String latitiute, String name, String area, String address1, String address2, String address3, String contact) {
        this.category = category;
        this.description = description;
        this.longtiute = longtiute;
        this.latitiute = latitiute;
        this.name = name;
        this.area = area;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.contact = contact;
    }

}
