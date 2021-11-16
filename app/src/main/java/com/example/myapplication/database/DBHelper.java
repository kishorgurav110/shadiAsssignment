package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.myapplication.ResponseModel.AcceptedDataRequestModel;
import com.example.myapplication.ResponseModel.RejectedDataRequestModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "operator.db";
    private static final int DB_VERSION = 3;
    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DataTable.TABLE_REQUEST_ACCEPTED_DATA + " ("
                + DataTable._ID + " INTEGER PRIMARY KEY,"
                + DataTable.COLUMN_ACCEPTED_FIRST_NAME + " TEXT,"
                + DataTable.COLUMN_ACCEPTED_LAST_NAME + " TEXT,"
                + DataTable.COLUMN_ACCEPTED_AGE + " TEXT,"
                + DataTable.COLUMN_ACCEPTED_CITY + " TEXT,"
                + DataTable.COLUMN_ACCEPTED_STATE + " TEXT,"
                + DataTable.COLUMN_ACCEPTED_COUNTRY + " TEXT,"
                + DataTable.COLUMN_ACCEPTED_PICTURE_LARGE + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + DataTable.TABLE_REQUEST_REJECTED_DATA + " ("
                + DataTable._ID + " INTEGER PRIMARY KEY,"
                + DataTable.COLUMN_REJECTED_FIRST_NAME + " TEXT,"
                + DataTable.COLUMN_REJECTED_LAST_NAME + " TEXT,"
                + DataTable.COLUMN_REJECTED_AGE + " TEXT,"
                + DataTable.COLUMN_REJECTED_CITY + " TEXT,"
                + DataTable.COLUMN_REJECTED_STATE + " TEXT,"
                + DataTable.COLUMN_REJECTED_COUNTRY + " TEXT,"
                + DataTable.COLUMN_REJECTED_PICTURE_LARGE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Logs that the database is being upgraded
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // Kills the table and existing data
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataTable.TABLE_REQUEST_ACCEPTED_DATA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataTable.TABLE_REQUEST_REJECTED_DATA);
    }

    public void insert_REQUEST_ACCEPTED_DATA(AcceptedDataRequestModel operatorList, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataTable.COLUMN_ACCEPTED_FIRST_NAME, operatorList.getFirst());
        contentValues.put(DataTable.COLUMN_ACCEPTED_LAST_NAME, operatorList.getLast());
        contentValues.put(DataTable.COLUMN_ACCEPTED_AGE, operatorList.getAge());
        contentValues.put(DataTable.COLUMN_ACCEPTED_CITY, operatorList.getCity());
        contentValues.put(DataTable.COLUMN_ACCEPTED_STATE, operatorList.getState());
        contentValues.put(DataTable.COLUMN_ACCEPTED_COUNTRY, operatorList.getCountry());
        contentValues.put(DataTable.COLUMN_ACCEPTED_PICTURE_LARGE, operatorList.getPicture_large());

        db.insertWithOnConflict(tableName, null, contentValues, 1);
        db.close();
    }

    public void insert_REQUEST_REJECTED_DATA(RejectedDataRequestModel operatorList, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataTable.COLUMN_REJECTED_FIRST_NAME, operatorList.getFirst());
        contentValues.put(DataTable.COLUMN_REJECTED_LAST_NAME, operatorList.getLast());
        contentValues.put(DataTable.COLUMN_REJECTED_AGE, operatorList.getAge());
        contentValues.put(DataTable.COLUMN_REJECTED_CITY, operatorList.getCity());
        contentValues.put(DataTable.COLUMN_REJECTED_STATE, operatorList.getState());
        contentValues.put(DataTable.COLUMN_REJECTED_COUNTRY, operatorList.getCountry());
        contentValues.put(DataTable.COLUMN_REJECTED_PICTURE_LARGE, operatorList.getPicture_large());

        db.insertWithOnConflict(tableName, null, contentValues, 1);
        db.close();
    }


    public int countNumberOfRecord(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        if (cursor.getCount() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public List<AcceptedDataRequestModel> fetchAcceptedDataBySqlite(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<AcceptedDataRequestModel> acceptedDataArrayList = new ArrayList<AcceptedDataRequestModel>();

        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                AcceptedDataRequestModel operatorList = new AcceptedDataRequestModel();
                operatorList.setFirst(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_FIRST_NAME)));
                operatorList.setLast(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_LAST_NAME)));
                operatorList.setAge(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_AGE)));
                operatorList.setCity(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_CITY)));
                operatorList.setState(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_STATE)));
                operatorList.setCountry(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_COUNTRY)));
                operatorList.setPicture_large(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_ACCEPTED_PICTURE_LARGE)));

                acceptedDataArrayList.add(operatorList);
                cursor.moveToNext();
            }
        }
        return acceptedDataArrayList;
    }

    public List<RejectedDataRequestModel> fetchRejectedDataBySqlite(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<RejectedDataRequestModel> rejectedDataArrayList = new ArrayList<RejectedDataRequestModel>();

        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        if (cursor.moveToFirst()) {


            while (cursor.isAfterLast() == false) {
                RejectedDataRequestModel operatorList = new RejectedDataRequestModel();
                operatorList.setFirst(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_FIRST_NAME)));
                operatorList.setLast(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_LAST_NAME)));
                operatorList.setAge(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_AGE)));
                operatorList.setCity(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_CITY)));
                operatorList.setState(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_STATE)));
                operatorList.setCountry(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_COUNTRY)));
                operatorList.setPicture_large(cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_REJECTED_PICTURE_LARGE)));

                rejectedDataArrayList.add(operatorList);
                cursor.moveToNext();
            }
        }
        return rejectedDataArrayList;
    }


    public void deletePunchInTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DataTable.TABLE_REQUEST_ACCEPTED_DATA);
    }

    public void deletePunchOutTable() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.execSQL("delete from " + DataTable.TABLE_REQUEST_REJECTED_DATA);
    }

    public static final class DataTable implements BaseColumns {

        public static final String TABLE_REQUEST_ACCEPTED_DATA = "accepted_data";
        public static final String COLUMN_ACCEPTED_FIRST_NAME = "COLUMN_ACCEPTED_FIRST_NAME";
        public static final String COLUMN_ACCEPTED_LAST_NAME = "COLUMN_ACCEPTED_LAST_NAME";
        public static final String COLUMN_ACCEPTED_AGE = "COLUMN_ACCEPTED_AGE";
        public static final String COLUMN_ACCEPTED_CITY = "COLUMN_ACCEPTED_CITY";
        public static final String COLUMN_ACCEPTED_STATE = "COLUMN_ACCEPTED_STATE";
        public static final String COLUMN_ACCEPTED_COUNTRY = "COLUMN_ACCEPTED_COUNTRY";
        public static final String COLUMN_ACCEPTED_PICTURE_LARGE = "COLUMN_ACCEPTED_PICTURE_LARGE";


        public static final String TABLE_REQUEST_REJECTED_DATA = "rejected_data";
        public static final String COLUMN_REJECTED_FIRST_NAME = "COLUMN_REJECTED_FIRST_NAME";
        public static final String COLUMN_REJECTED_LAST_NAME = "COLUMN_REJECTED_LAST_NAME";
        public static final String COLUMN_REJECTED_AGE = "COLUMN_REJECTED_AGE";
        public static final String COLUMN_REJECTED_CITY = "COLUMN_REJECTED_CITY";
        public static final String COLUMN_REJECTED_STATE = "COLUMN_REJECTED_STATE";
        public static final String COLUMN_REJECTED_COUNTRY = "COLUMN_REJECTED_COUNTRY";
        public static final String COLUMN_REJECTED_PICTURE_LARGE = "COLUMN_REJECTED_PICTURE_LARGE";

    }
}