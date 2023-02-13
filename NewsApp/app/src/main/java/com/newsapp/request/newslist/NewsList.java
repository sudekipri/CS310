package com.newsapp.request.newslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NewsList {

    @SerializedName("serviceMessageCode")
    @Expose
    private Integer serviceMessageCode;
    @SerializedName("serviceMessageText")
    @Expose
    private String serviceMessageText;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public Integer getServiceMessageCode() {
        return serviceMessageCode;
    }

    public void setServiceMessageCode(Integer serviceMessageCode) {
        this.serviceMessageCode = serviceMessageCode;
    }

    public String getServiceMessageText() {
        return serviceMessageText;
    }

    public void setServiceMessageText(String serviceMessageText) {
        this.serviceMessageText = serviceMessageText;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
