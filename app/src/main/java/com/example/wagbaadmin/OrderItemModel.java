package com.example.wagbaadmin;

public class OrderItemModel {
    private String order_name;
    private String order_item_price;

    public OrderItemModel() {
    }

    public OrderItemModel(String order_item, String order_item_price) {
        this.order_name = order_item;
        this.order_item_price = order_item_price;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_item_price() {
        return order_item_price;
    }

    public void setOrder_item_price(String order_item_price) {
        this.order_item_price = order_item_price;
    }
}
