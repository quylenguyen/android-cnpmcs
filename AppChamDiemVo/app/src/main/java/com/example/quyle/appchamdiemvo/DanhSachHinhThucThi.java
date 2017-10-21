package com.example.quyle.appchamdiemvo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quyle on 10/21/2017.
 */

public class DanhSachHinhThucThi extends ArrayAdapter<HinhThucThi> {
    private Activity context;
    List<HinhThucThi> danhSachHinhThucThi;

    public DanhSachHinhThucThi(Activity context, List<HinhThucThi> danhSachHinhThucThi) {
        super(context, R.layout.list_layout_hinh_thuc_thi, danhSachHinhThucThi);
        this.context = context;
        this.danhSachHinhThucThi = danhSachHinhThucThi;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_hinh_thuc_thi, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        HinhThucThi hinhthucthi = danhSachHinhThucThi.get(position);
        textViewName.setText(hinhthucthi.getTenHinhThucThi());
//        textViewGenre.setText(capdai.getArtistGenre());

        return listViewItem;
    }
}

