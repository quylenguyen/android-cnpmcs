package View.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quyle.appchamdiemvo.R;

import java.util.List;

import Model.DotThi;


public class DotThiAdapter extends RecyclerView.Adapter<DotThiAdapter.ViewHolder> {
    public List<DotThi> lsDotThi;
    public OnNotificationListener onNotificationListener;
    public OnItemClickListener listener;

    public DotThiAdapter(List<DotThi> lsDotThi, OnNotificationListener onNotificationListener, OnItemClickListener listener) {
        this.lsDotThi = lsDotThi;
        this.onNotificationListener = onNotificationListener;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dot_thi, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(lsDotThi.get(position),listener,onNotificationListener);
    }

    @Override
    public int getItemCount() {
        return lsDotThi.size();
    }

    public interface OnItemClickListener {
        void onItemClick(DotThi item);
    }

    public interface OnNotificationListener{
        void onIconClick(DotThi item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageButton btnNotification;
        private TextView txtTime;
        public ViewHolder(View itemView) {
            super(itemView);
            btnNotification = itemView.findViewById(R.id.btnNotification);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
        public void bind(final DotThi item, final OnItemClickListener listener,final  OnNotificationListener onNotificationListener){
            txtTime.setText(item.getTenDotThi());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            btnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationListener.onIconClick(item);
                }
            });
        }
    }
}