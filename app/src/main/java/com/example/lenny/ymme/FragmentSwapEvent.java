package com.example.lenny.ymme;

/**
 * Created by lenny on 5/24/17.
 */

public class FragmentSwapEvent {
    public String selection;
    public String[] packet;

    public FragmentSwapEvent (String[] s, String selection){
        packet = s;
        this.selection = selection;

    }
}
