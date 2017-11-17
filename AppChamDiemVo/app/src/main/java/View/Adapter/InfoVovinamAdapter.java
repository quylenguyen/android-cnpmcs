package View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quyle.appchamdiemvo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.InfoVoviNam;


public class InfoVovinamAdapter extends BaseAdapter {
    List<InfoVoviNam> lsCategory;
    private Context context;

    public InfoVovinamAdapter(List<InfoVoviNam> lsCategory, Context context) {
        this.lsCategory = lsCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lsCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return lsCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if(rowView == null)
        {
            rowView = LayoutInflater.from(context).inflate(R.layout.item,null);
            TextView name = rowView.findViewById(R.id.txtLabel);
            ImageView img = rowView.findViewById(R.id.img);
            Picasso.with(context).load(lsCategory.get(i).ImageURL).into(img);
            name.setText(lsCategory.get(i).categoryInfo);
        }
        return rowView;
    }
}
