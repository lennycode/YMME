package com.example.lenny.ymme.models;

/**
 * Created by lenny on 5/21/17.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Makes extends YMMEElement implements Serializable {


    @SerializedName("makes")
    @Expose
    private List<String> makes = null;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("time")
    @Expose
    private String time;

    public List<String> getMakes() {
        return makes;
    }

    public void setMakes(List<String> makes) {
        this.makes = makes;
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
        return makes;
    }
}