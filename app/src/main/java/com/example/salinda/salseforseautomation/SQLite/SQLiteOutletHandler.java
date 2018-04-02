package com.example.salinda.salseforseautomation.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.salinda.salseforseautomation.model.OutletModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteOutletHandler extends SQLiteDatabaseHandler {
    Context context;
    public SQLiteOutletHandler(Context context) {
        super(context);
        this.context = context;
    }

    public void deleteAll(){
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_OUTLETS);
        db.close();
    }

    public void addOutletList(List<OutletModel> outletModels) {
        for(int i=0;i<outletModels.size();i++) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_ID, outletModels.get(i).getId());
            values.put(KEY_NAME, outletModels.get(i).getName());
            values.put(KEY_CONTACTNO, outletModels.get(i).getContactNo());
            values.put(KEY_BARCODE, outletModels.get(i).getBarcode());
            values.put(KEY_LONGITUDE, outletModels.get(i).getLongitude());
            values.put(KEY_LATITUDE, outletModels.get(i).getLatitude());
            values.put(KEY_OWNER_NAME, outletModels.get(i).getOwnerName());
            values.put(KEY_EMAIL, outletModels.get(i).getEmail());
            values.put(KEY_STATUS, outletModels.get(i).getStatus());
            values.put(KEY_ROUTEID, outletModels.get(i).getRouteId());
            long chack = db.insertOrThrow(TABLE_OUTLETS, null, values);
            if(chack == -1){i--;}
            db.close();

        }
    }

    public List<OutletModel> getAllOutlets() {
        List<OutletModel> outletList = new ArrayList<OutletModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OUTLETS;

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OutletModel outletModel = new OutletModel();
                outletModel.setId(cursor.getInt(0));
                outletModel.setName(cursor.getString(1));
                outletModel.setContactNo(cursor.getString(2));
                outletModel.setBarcode(cursor.getString(3));
                outletModel.setLongitude(cursor.getFloat(4));
                outletModel.setLatitude(cursor.getFloat(5));
                outletModel.setOwnerName(cursor.getString(6));
                outletModel.setEmail(cursor.getString(7));
                outletModel.setStatus(cursor.getString(8));
                outletModel.setRouteId(cursor.getInt(9));
                // Adding contact to list
                outletList.add(outletModel);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return outletList;
    }
}
