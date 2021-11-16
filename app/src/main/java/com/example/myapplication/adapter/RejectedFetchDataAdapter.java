package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ResponseModel.RejectedDataRequestModel;
import com.example.myapplication.ResponseModel.matchesresponse.Result;
import com.example.myapplication.database.DBHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class RejectedFetchDataAdapter extends RecyclerView.Adapter<RejectedFetchDataAdapter.MyViewHolder> {
    private List<RejectedDataRequestModel> rejectedDataRequestModelList;
    private Context mContext;
    DBHelper dbHelper;
    RejectedDataRequestModel RejectedDataRequestModel;


    public RejectedFetchDataAdapter(Context context, List<RejectedDataRequestModel> rejectedDataRequestModelList, DBHelper dbHelper) {
        this.mContext = context;
        this.rejectedDataRequestModelList = rejectedDataRequestModelList;
        this.dbHelper = dbHelper;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView txt_name;
        private TextView txt_age;
        private TextView txt_address;
        private CircleImageView img_profile;

        MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txt_name = mView.findViewById(R.id.txt_name);
            txt_age = mView.findViewById(R.id.txt_age);
            txt_address = mView.findViewById(R.id.txt_address);
            img_profile = mView.findViewById(R.id.img_profile);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.accepted_fetch_item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txt_name.setText(""
                + rejectedDataRequestModelList.get(position).getFirst()
                + " , "
                + rejectedDataRequestModelList.get(position).getLast());
        holder.txt_age.setText("" + rejectedDataRequestModelList.get(position).getAge());
        holder.txt_address.setText("" + rejectedDataRequestModelList.get(position).getCity()
                + " , " + rejectedDataRequestModelList.get(position).getState()
                + " , " + rejectedDataRequestModelList.get(position).getCountry());

        Picasso.with(mContext).load(rejectedDataRequestModelList.get(position).getPicture_large())
                .fit().centerCrop()
                .error(R.drawable.error_image)
                .into(holder.img_profile);

    }

    @Override
    public int getItemCount() {
        return rejectedDataRequestModelList.size();
    }

    public abstract void onItemClick(int Position, View view, Result responseListData);

}
