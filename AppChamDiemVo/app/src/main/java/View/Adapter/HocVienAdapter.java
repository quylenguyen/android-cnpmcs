package View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quyle.appchamdiemvo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.HocVien;

public class HocVienAdapter extends RecyclerView.Adapter<HocVienAdapter.ViewHolder> {
    List<HocVien> lsHV;
    public OnClickHVListener listener;
    Context context;

    public HocVienAdapter(List<HocVien> lsHV, OnClickHVListener listener,Context context) {
        this.lsHV = lsHV;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public HocVienAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hocvien, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HocVienAdapter.ViewHolder holder, int position) {
        holder.bind(lsHV.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return lsHV.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtTen,txtNgaySing,txtCapDai,txtDonVi;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgPerson);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtNgaySing = itemView.findViewById(R.id.txtNgaySinh);
            txtDonVi = itemView.findViewById(R.id.txtDonVi);
            txtCapDai = itemView.findViewById(R.id.txtCapDaiHienTai);
        }
        public void bind(final HocVien hv,final OnClickHVListener onClickHVListener){
            txtDonVi.setText(hv._DonVi);
            txtCapDai.setText(hv._DaiHienTai);
            txtNgaySing.setText(hv._NgaySinh);
            txtTen.setText(hv._Ten);
            Picasso.with(context).load(hv.URLImage).into(img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickHVListener.onItemClick(hv);
                }
            });
        }
    }
    public interface OnClickHVListener{
        void onItemClick(HocVien hv);
    }
}
