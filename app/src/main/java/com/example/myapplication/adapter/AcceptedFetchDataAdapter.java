package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ResponseModel.AcceptedDataRequestModel;
import com.example.myapplication.ResponseModel.matchesresponse.Result;
import com.example.myapplication.database.DBHelper;
import com.example.myapplication.utils.Prefs;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class AcceptedFetchDataAdapter extends RecyclerView.Adapter<AcceptedFetchDataAdapter.MyViewHolder> {
    private List<AcceptedDataRequestModel> acceptedDataRequestModelList;
    private Context mContext;
    DBHelper dbHelper;
    AcceptedDataRequestModel acceptedDataRequestModel;


    public AcceptedFetchDataAdapter(Context context, List<AcceptedDataRequestModel> acceptedDataRequestModelList, DBHelper dbHelper) {
        this.mContext = context;
        this.acceptedDataRequestModelList = acceptedDataRequestModelList;
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
                + acceptedDataRequestModelList.get(position).getFirst()
                + " , "
                + acceptedDataRequestModelList.get(position).getLast());
        holder.txt_age.setText("" + acceptedDataRequestModelList.get(position).getAge());
        holder.txt_address.setText(""+ acceptedDataRequestModelList.get(position).getCity()
                + " , " + acceptedDataRequestModelList.get(position).getState()
                + " , " + acceptedDataRequestModelList.get(position).getCountry());

        Picasso.with(mContext).load(acceptedDataRequestModelList.get(position).getPicture_large())
                .fit().centerCrop()
                .error(R.drawable.error_image)
                .into(holder.img_profile);
    }

    @Override
    public int getItemCount() {
        return acceptedDataRequestModelList.size();
    }

    public abstract void onItemClick(int Position, View view, Result responseListData);

}
