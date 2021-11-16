package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ResponseModel.AcceptedDataRequestModel;
import com.example.myapplication.ResponseModel.RejectedDataRequestModel;
import com.example.myapplication.ResponseModel.matchesresponse.Result;
import com.example.myapplication.ResponseModel.matchesresponse.ResultsMatchesResponseModel;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.database.DBHelper;
import com.example.myapplication.webservices.ApiInterface;
import com.example.myapplication.webservices.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Context mContext;
    RecyclerView mRecyclerView;
    ApiInterface apiService;
    HomeAdapter orderBookAdapter;
    DBHelper dbHelper;
    AcceptedDataRequestModel acceptedDataRequestModel;
    RejectedDataRequestModel rejectedDataRequestModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bindViews(rootView);
        return rootView;

    }

    private void bindViews(View rootView) {
        mContext = getActivity();
        dbHelper = new DBHelper(mContext);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_order_book_list);

        APICallLoadMatchesList();
    }

    private void APICallLoadMatchesList() {
        String resultValue = "10";
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<ResultsMatchesResponseModel> call = apiService.getRandomUserRequest(
                resultValue);
        call.enqueue(new Callback<ResultsMatchesResponseModel>() {
            @Override
            public void onResponse(Call<ResultsMatchesResponseModel> call, Response<ResultsMatchesResponseModel> response) {
                //ConstantValues.dismiss_progress_dialog();
                if (!response.body().getResults().isEmpty()) {
                    generateDataList(response.body().getResults());
                } else {
                    Toast.makeText(mContext, "" + response.body().getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultsMatchesResponseModel> call, Throwable t) {
//                ConstantValues.dismiss_progress_dialog();
                Toast.makeText(mContext, "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Result> subscriptionListData) {
        orderBookAdapter = new HomeAdapter(mContext, subscriptionListData, dbHelper) {
            @Override
            public void onItemClick(int Position, View view, Result responseListData) {

            }

            @Override
            public void onItemAcceptButtonClick(int Position, View view, Result responseListData) {
                Toast.makeText(mContext, "Accept", Toast.LENGTH_SHORT).show();
                acceptedDataRequestModel = new AcceptedDataRequestModel();
                acceptedDataRequestModel.setFirst(responseListData.getName().getFirst());
                acceptedDataRequestModel.setLast(responseListData.getName().getLast());
                acceptedDataRequestModel.setAge(responseListData.getDob().getAge());
                acceptedDataRequestModel.setCity(responseListData.getLocation().getCity());
                acceptedDataRequestModel.setState(responseListData.getLocation().getState());
                acceptedDataRequestModel.setCountry(responseListData.getLocation().getCountry());
                acceptedDataRequestModel.setPicture_large(responseListData.getPicture().getLarge());
                dbHelper.insert_REQUEST_ACCEPTED_DATA(acceptedDataRequestModel, DBHelper.DataTable.TABLE_REQUEST_ACCEPTED_DATA);
                Toast.makeText(mContext, "Inserted in Database Successfully", Toast.LENGTH_SHORT).show();
//                Prefs.video_upload_successfully_text_visibility = true;
//                Prefs.video_upload_position = Position;
//                orderBookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onItemRejectButtonClick(int Position, View view, Result responseListData) {
                Toast.makeText(mContext, "reject", Toast.LENGTH_SHORT).show();
                rejectedDataRequestModel = new RejectedDataRequestModel();
                rejectedDataRequestModel.setFirst(responseListData.getName().getFirst());
                rejectedDataRequestModel.setLast(responseListData.getName().getLast());
                rejectedDataRequestModel.setAge(responseListData.getDob().getAge());
                rejectedDataRequestModel.setCity(responseListData.getLocation().getCity());
                rejectedDataRequestModel.setState(responseListData.getLocation().getState());
                rejectedDataRequestModel.setCountry(responseListData.getLocation().getCountry());
                rejectedDataRequestModel.setPicture_large(responseListData.getPicture().getLarge());
                dbHelper.insert_REQUEST_REJECTED_DATA(rejectedDataRequestModel, DBHelper.DataTable.TABLE_REQUEST_REJECTED_DATA);
                Toast.makeText(mContext, "Inserted Declined data in Database Successfully", Toast.LENGTH_SHORT).show();
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(orderBookAdapter);
    }


}