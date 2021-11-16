package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ResponseModel.AcceptedDataRequestModel;
import com.example.myapplication.ResponseModel.matchesresponse.Result;
import com.example.myapplication.adapter.AcceptedFetchDataAdapter;
import com.example.myapplication.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AcceptedFetchDataFromDBFragment extends Fragment {

    RecyclerView mRecyclerView;
    AcceptedFetchDataAdapter acceptedFetchDataAdapter;
    DBHelper dbHelper;
    Context mContext;
    TextView txt_emptydata;
    List<AcceptedDataRequestModel> acceptedDataRequestModelArrayList = new ArrayList<AcceptedDataRequestModel>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accepted_fetch_data_from_db_fragment, container, false);
        bindViews(rootView);
        return rootView;
    }

    private void bindViews(View rootView) {
        mContext = getActivity();
        dbHelper = new DBHelper(mContext);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_accepted_fetchdata);
        txt_emptydata = (TextView) rootView.findViewById(R.id.txt_emptydata);
        loadAcceptedFetchDataListFromDB();
    }

    private void loadAcceptedFetchDataListFromDB() {
        acceptedDataRequestModelArrayList.clear();
        acceptedDataRequestModelArrayList = dbHelper.fetchAcceptedDataBySqlite(DBHelper.DataTable.TABLE_REQUEST_ACCEPTED_DATA);
        generateDataList(acceptedDataRequestModelArrayList);

    }

    private void generateDataList(List<AcceptedDataRequestModel> acceptedDataRequestModelArrayList) {
        acceptedFetchDataAdapter = new AcceptedFetchDataAdapter(mContext, acceptedDataRequestModelArrayList, dbHelper) {
            @Override
            public void onItemClick(int Position, View view, Result responseListData) {
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(acceptedFetchDataAdapter);
    }
}