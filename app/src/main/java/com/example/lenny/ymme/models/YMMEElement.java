package com.example.lenny.ymme.models;

import java.util.List;

/**
 * Created by lenny on 5/22/17.
 */

public abstract class YMMEElement {
    private List<String> dataList = null;

    public List<String> getData(){
        return dataList;
    }
}
