package com.nibm.rwp.gms.dto;

public class RequestHistory {
   private String customer_name;
   private String category_id;
   private String updated_at;
    private String status;

    public RequestHistory(String customer_name, String category_id, String updated_at, String status) {
        this.customer_name = customer_name;
        this.category_id = category_id;
        this.updated_at = updated_at;
        this.status = status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
