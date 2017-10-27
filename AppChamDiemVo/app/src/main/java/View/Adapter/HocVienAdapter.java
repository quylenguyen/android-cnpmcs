package View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quyle.appchamdiemvo.R;

import java.util.List;

import Model.HocVien;

/**
 * Created by vtnhan on 10/27/2017.
 */

public class HocVienAdapter extends ArrayAdapter<HocVien> {
    Activity context;
    List<HocVien> lsHV;
    public HocVienAdapter(@NonNull Activity context, @NonNull List<HocVien> objects) {
        super(context, R.layout.row_hocvien, objects);
        this.context = context;
        this.lsHV = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_hocvien, null, true);

        TextView textViewName =  listViewItem.findViewById(R.id.txtNameHV);
        TextView txtNgaySinh = listViewItem.findViewById(R.id.txtNgaySinh);
        TextView txtDaiHienTai = listViewItem.findViewById(R.id.txtDaiHienTai);
        TextView txtDonVi = listViewItem.findViewById(R.id.txtDonvi);

        HocVien hv = lsHV.get(position);
        textViewName.setText(hv._Ten);
        txtNgaySinh.setText(hv._NgaySinh);
        txtDonVi.setText(hv._DonVi);
        txtDaiHienTai.setText(hv._DaiHienTai);

        return listViewItem;
    }
}
