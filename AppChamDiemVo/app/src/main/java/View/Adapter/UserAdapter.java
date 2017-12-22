package View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quyle.appchamdiemvo.R;

import java.util.List;

import Model.HocVien;
import Model.User;

/**
 * Created by vtnhan on 12/23/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> lsUser;
    public OnLongClickUserListener listener = null;
    Context context;
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.row_user,parent,false);
        return new ViewHolder(rootView);
    }

    public UserAdapter(List<User> lsUser, OnLongClickUserListener listener, Context context) {
        this.lsUser = lsUser;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.bind(lsUser.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return lsUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenUser, txtSoPhone, txtEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenUser = itemView.findViewById(R.id.txtTenUser);
            txtEmail = itemView.findViewById(R.id.txtEmailUser);
            txtSoPhone = itemView.findViewById(R.id.txtPhoneUser);
        }

        public void bind(final User user, final OnLongClickUserListener onLongClickUserListener) {
            txtSoPhone.setText(user.Phone);
            txtEmail.setText(user.Email);
            txtTenUser.setText(user.Name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLongClickUserListener.onItemLongClick(user);
                }
            });

        }
    }
    public interface OnClickUserListener {
        void onItemClick(User user);
    }

    public interface OnLongClickUserListener {
        void onItemLongClick(User hv);
    }
}
