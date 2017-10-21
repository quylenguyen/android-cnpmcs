package com.example.quyle.appchamdiemvo;

/**
 * Created by quyle on 10/21/2017.
 */

public class ThiSinh{
    String id;
    String hoTen;
    String noiDungThi;
    String diemSo;
    ThiSinh(){}

    public ThiSinh(String id, String hoTen, String noiDungThi, String diemSo) {
        this.id = id;
        this.hoTen = hoTen;
        this.noiDungThi = noiDungThi;
        this.diemSo = diemSo;
    }

    public String getId() {
        return id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getNoiDungThi() {
        return noiDungThi;
    }

    public String getDiemSo() {
        return diemSo;
    }
}
