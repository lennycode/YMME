package com.example.lenny.ymme.models;


import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Years extends YMMEElement implements Serializable {

    @SerializedName("years")
    @Expose
    private List<String> years = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public List<String> getData() {
        return years;
    }
}
