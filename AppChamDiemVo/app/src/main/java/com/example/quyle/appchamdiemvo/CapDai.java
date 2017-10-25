package com.example.quyle.appchamdiemvo;

/**
 * Created by quyle on 10/21/2017.
 */

public class CapDai {
    String daiId;
    String tenDai;
    public CapDai(){

    }

    public CapDai(String daiId, String tenDai) {
        this.daiId = daiId;
        this.tenDai = tenDai;
    }

    public String getDaiId() {
        return daiId;
    }

    public String getTenDai() {
        return tenDai;
    }
}
