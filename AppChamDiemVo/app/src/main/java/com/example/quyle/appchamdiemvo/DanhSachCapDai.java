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

public class DanhSachCapDai extends ArrayAdapter<CapDai> {
    private Activity context;
    List<CapDai> danhSachCapDai;

    public DanhSachCapDai(Activity context, List<CapDai> danhSachCapDai) {
        super(context, R.layout.list_layout_cap_dai, danhSachCapDai);
        this.context = context;
        this.danhSachCapDai = danhSachCapDai;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_cap_dai, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        CapDai capdai = danhSachCapDai.get(position);
        textViewName.setText(capdai.getTenDai());
//        textViewGenre.setText(capdai.getArtistGenre());

        return listViewItem;
    }
}
