package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ResponseModel.RejectedDataRequestModel;
import com.example.myapplication.ResponseModel.matchesresponse.Result;
import com.example.myapplication.adapter.RejectedFetchDataAdapter;
import com.example.myapplication.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class RejectedFetchDataFromDBFragment extends Fragment {

    RecyclerView mRecyclerView;
    RejectedFetchDataAdapter rejectedFetchDataAdapter;
    DBHelper dbHelper;
    Context mContext;
    List<RejectedDataRequestModel> RejectedDataRequestModelArrayList = new ArrayList<RejectedDataRequestModel>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rejected_fetch_data_from_db_fragment, container, false);
        bindViews(rootView);
        return rootView;
    }

    private void bindViews(View rootView) {
        mContext = getActivity();
        dbHelper = new DBHelper(mContext);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_rejected_fetchdata);
        loadAcceptedFetchDataListFromDB();
    }

    private void loadAcceptedFetchDataListFromDB() {
        RejectedDataRequestModelArrayList.clear();
        RejectedDataRequestModelArrayList = dbHelper.fetchRejectedDataBySqlite(DBHelper.DataTable.TABLE_REQUEST_REJECTED_DATA);
        generateDataList(RejectedDataRequestModelArrayList);
    }

    private void generateDataList(List<RejectedDataRequestModel> RejectedDataRequestModelArrayList) {
        rejectedFetchDataAdapter = new RejectedFetchDataAdapter(mContext, RejectedDataRequestModelArrayList, dbHelper) {
            @Override
            public void onItemClick(int Position, View view, Result responseListData) {
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(rejectedFetchDataAdapter);
    }
}