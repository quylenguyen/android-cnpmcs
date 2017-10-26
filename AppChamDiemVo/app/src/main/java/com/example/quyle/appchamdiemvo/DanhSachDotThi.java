package com.example.quyle.appchamdiemvo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Model.DotThi;

/**
 * Created by quyle on 10/25/2017.
 */

public class DanhSachDotThi extends ArrayAdapter<DotThi> {
    private Activity context;
    List<DotThi> danhSachDotThi;

    public DanhSachDotThi(Activity context, List<DotThi> danhSachDotThi) {
        super(context, R.layout.list_layout_dot_thi, danhSachDotThi);
        this.context = context;
        this.danhSachDotThi = danhSachDotThi;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_dot_thi, null, true);

        TextView textViewDotThi = (TextView) listViewItem.findViewById(R.id.textViewDotThi);


        DotThi dotthi = danhSachDotThi.get(position);
        textViewDotThi.setText(dotthi.getTenDotThi());


        return listViewItem;
    }
}
