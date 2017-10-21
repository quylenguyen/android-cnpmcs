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

public class DanhSachThiSinh extends ArrayAdapter<ThiSinh> {
    private Activity context;
    List<ThiSinh> danhSachThiSinh;

    public DanhSachThiSinh(Activity context, List<ThiSinh> danhSachThiSinh) {
        super(context, R.layout.list_layout_thi_sinh, danhSachThiSinh);
        this.context = context;
        this.danhSachThiSinh = danhSachThiSinh;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_thi_sinh, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        ThiSinh thiSinh = danhSachThiSinh.get(position);
        textViewName.setText(thiSinh.getHoTen());
//        textViewGenre.setText(capdai.getArtistGenre());

        return listViewItem;
    }
}