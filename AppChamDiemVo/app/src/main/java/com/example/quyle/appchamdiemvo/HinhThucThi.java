package com.example.quyle.appchamdiemvo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by quyle on 10/21/2017.
 */

@IgnoreExtraProperties
public class HinhThucThi{
    private String id;
    private String tenHinhThucThi;
    HinhThucThi(){

    }

    public HinhThucThi(String id, String tenHinhThucThi) {
        this.id = id;
        this.tenHinhThucThi = tenHinhThucThi;
    }

    public String getId() {
        return id;
    }

    public String getTenHinhThucThi() {
        return tenHinhThucThi;
    }
}

