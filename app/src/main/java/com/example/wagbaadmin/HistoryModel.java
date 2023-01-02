package com.example.wagbaadmin;

public class HistoryModel {

    private String order_name;
    private String order_date;
    private String order_price;
    private String order_status="";

    public HistoryModel() {
    }

    private boolean expanded;

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public HistoryModel(String order_name, String order_date, String order_price, String order_status,/* int restaurant_img_id, int food_type_img_id,*/ String order_item_1, String order_item_2, String order_item_3, String order_item_1_price, String order_item_2_price, String order_item_3_price, String total_price_details, String delivery_area) {
        this.order_name = order_name;
        this.order_date= order_date;
        this.order_price= order_price;
        this.order_status = order_status;



    }


}

