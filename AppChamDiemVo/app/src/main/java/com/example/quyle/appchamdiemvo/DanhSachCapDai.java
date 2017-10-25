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
<<<<<<< HEAD
        super(context, R.layout.list_layout_cap_dai, danhSachCapDai);
=======
        super(context, R.layout.list_layout, danhSachCapDai);
>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528
        this.context = context;
        this.danhSachCapDai = danhSachCapDai;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
<<<<<<< HEAD
        View listViewItem = inflater.inflate(R.layout.list_layout_cap_dai, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
=======
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528

        CapDai capdai = danhSachCapDai.get(position);
        textViewName.setText(capdai.getTenDai());
//        textViewGenre.setText(capdai.getArtistGenre());

        return listViewItem;
    }
}
