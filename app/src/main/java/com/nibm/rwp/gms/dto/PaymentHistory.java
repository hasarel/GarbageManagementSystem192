package com.nibm.rwp.gms.dto;

public class PaymentHistory {
   private String customer_name;
   private String created_at;
    private String tele_no;

    public PaymentHistory(String customer_name, String created_at, String tele_no) {
        this.customer_name = customer_name;
        this.created_at = created_at;
        this.tele_no = tele_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTele_no() {
        return tele_no;
    }

    public void setTele_no(String tele_no) {
        this.tele_no = tele_no;
    }

}
