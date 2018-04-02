package com.example.salinda.salseforseautomation.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.salinda.salseforseautomation.model.OutstandingModel;

import java.util.ArrayList;
import java.util.List;


public class SQLiteOutstandingHandler extends SQLiteDatabaseHandler {
    Context context;
    public SQLiteOutstandingHandler(Context context) {
        super(context);
        context = context;
    }

    public void deleteAll(){
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_OUTSTANDING);
        db.close();
    }

    public void addOutstandingList(List<OutstandingModel> outstandingModels) {
        for(int i=0;i<outstandingModels.size();i++) {
            Log.i("Tag Set Outstanding", String.valueOf(outstandingModels.get(i).getAmount()));

            android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_OUTLETID, outstandingModels.get(i).getOutletId());
            values.put(KEY_AMOUNT, outstandingModels.get(i).getAmount());
            long chack = db.insertOrThrow(TABLE_OUTSTANDING, null, values);
            if(chack == -1){i--;}
            db.close();

        }
    }

    public List<OutstandingModel> getAllOutstanding() {
        List<OutstandingModel> outstandingList = new ArrayList<OutstandingModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OUTSTANDING;

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OutstandingModel outstandingModel = new OutstandingModel();
                outstandingModel.setOutletId(cursor.getInt(0));
                outstandingModel.setAmount(cursor.getFloat(1));

            } while (cursor.moveToNext());
        }
        // return contact list
        return outstandingList;
    }

    public float getOutstandingByOutletId(int OutletId) {
        float result = 0.00f;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OUTSTANDING + " WHERE " + KEY_OUTLETID + "=?";

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(OutletId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getFloat(1);
            } while (cursor.moveToNext());
        }
        // return contact list
        Log.i("Tag get Outstanding", String.valueOf(result));
        return result;
    }
}
