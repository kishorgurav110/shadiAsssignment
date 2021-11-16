package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ResponseModel.matchesresponse.Result;
import com.example.myapplication.database.DBHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Result> notificationList;
    private Context mContext;
    DBHelper dbHelper;
    int row_index = -1;

    public HomeAdapter(Context context, List<Result> notificationList, DBHelper dbHelper) {
        this.mContext = context;
        this.notificationList = notificationList;
        this.dbHelper = dbHelper;
//        Prefs.video_upload_position = -1;
//        Prefs.video_upload_successfully_text_visibility = false;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView txt_name;
        private TextView txt_age;
        //        private TextView txt_height;
        private TextView txt_address;
        private TextView txt_designation;
        private TextView txt_accept;
        private TextView txt_decline;
        private CircleImageView img_profile;


        MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txt_name = mView.findViewById(R.id.txt_name);
            txt_age = mView.findViewById(R.id.txt_age);
//            txt_height = mView.findViewById(R.id.txt_height);
            txt_address = mView.findViewById(R.id.txt_address);
            txt_designation = mView.findViewById(R.id.txt_designation);
            txt_accept = mView.findViewById(R.id.txt_accept);
            txt_decline = mView.findViewById(R.id.txt_decline);
            img_profile = mView.findViewById(R.id.img_profile);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dashboard_item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt_name.setText(""
                + notificationList.get(position).getName().getFirst()
                + " , "
                + notificationList.get(position).getName().getLast());
        holder.txt_age.setText("" + notificationList.get(position).getDob().getAge());
        holder.txt_address.setText(""
                + " , " + notificationList.get(position).getLocation().getCity()
                + " , " + notificationList.get(position).getLocation().getState()
                + " , " + notificationList.get(position).getLocation().getCountry());

        Picasso.with(mContext).load(notificationList.get(position).getPicture().getLarge())
                .fit().centerCrop()
                .error(R.drawable.error_image)
                .into(holder.img_profile);

        int random_position = holder.getAbsoluteAdapterPosition();
//        row_index;
//        if(Prefs.video_upload_successfully_text_visibility == true &&
//                Prefs.video_upload_position == position){
//            holder.txt_accept.setVisibility(View.VISIBLE);
//        }else {
//            holder.txt_accept.setVisibility(View.GONE);
//        }

        holder.txt_designation.setText(notificationList.get(position).getLocation().getTimezone().getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position, holder.itemView, notificationList.get(position));

            }
        });

        holder.txt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemAcceptButtonClick(position, holder.itemView, notificationList.get(position));
            }
        });

        holder.txt_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRejectButtonClick(position, holder.itemView, notificationList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public abstract void onItemClick(int Position, View view, Result responseListData);

    public abstract void onItemAcceptButtonClick(int Position, View view, Result responseListData);

    public abstract void onItemRejectButtonClick(int Position, View view, Result responseListData);

}
