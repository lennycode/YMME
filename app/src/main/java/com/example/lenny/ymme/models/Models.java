package com.example.lenny.ymme.models;


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Models extends YMMEElement implements Serializable {

    @SerializedName("models")
    @Expose
    private List<String> models = null;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("time")
    @Expose
    private String time;

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    @Override
    public List<String> getData() {
        return models;
    }
}