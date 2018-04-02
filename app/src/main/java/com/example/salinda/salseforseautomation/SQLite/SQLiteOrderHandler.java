package com.example.salinda.salseforseautomation.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.salinda.salseforseautomation.model.SQLiteOrderModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteOrderHandler extends SQLiteDatabaseHandler {
    Context context;
    public SQLiteOrderHandler(Context context){
        super(context);
        this.context = context;
    }

    public void deleteAll(){
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_ORDER);
        db.close();
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public int getLastId(String date){
        String selectQuery = "SELECT " + KEY_ID +" FROM " + TABLE_ORDER + " WHERE " + KEY_DATE + "=?";

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{date});
        int result=0;

        if(cursor.moveToFirst()){
            do{
                result = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            }while (cursor.moveToNext());
        }

        return result;
    }

    public boolean addOrder(SQLiteOrderModel orderModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try{
            for(int i=1; i<2; i++){
                // values.put(KEY_ID, orderModel.getId());
                values.put(KEY_DATE, orderModel.getDate());
                values.put(KEY_DESCRIPTION, orderModel.getDescription());
                values.put(KEY_USERID, orderModel.getUserId());
                values.put(KEY_OUTLETID, orderModel.getOutletId());
                values.put(KEY_LONGITUDE, orderModel.getLongitude());
                values.put(KEY_LATITUDE, orderModel.getLatitude());
                values.put(KEY_PAYMENT_TYPE, orderModel.getPaymentType());
                values.put(KEY_AMOUNT, orderModel.getAmount());
                values.put(KEY_DELIVERY_DATE, orderModel.getDeliveryDate());
                values.put(KEY_ORDER_TYPE, orderModel.getOrderType());
                long chack = db.insertOrThrow(TABLE_ORDER, null, values);
                if(chack == -1){i--;}
                db.close();
            }
            Log.i("Tag ", "Table Inserted");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<SQLiteOrderModel> getAllOrder(){
        List<SQLiteOrderModel> orderList = new ArrayList<SQLiteOrderModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER;

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SQLiteOrderModel orderModel = new SQLiteOrderModel();
                orderModel.setId(cursor.getInt(0));
                orderModel.setDate(cursor.getString(1));
                orderModel.setDescription(cursor.getString(2));
                orderModel.setUserId(cursor.getInt(3));
                orderModel.setOutletId(cursor.getInt(4));
                orderModel.setLongitude(cursor.getFloat(5));
                orderModel.setLatitude(cursor.getFloat(6));
                orderModel.setPaymentType(cursor.getString(7));
                orderModel.setAmount(cursor.getFloat(8));
                orderModel.setDeliveryDate(cursor.getString(9));
                orderModel.setOrderType(cursor.getString(10));
                orderList.add(orderModel);
            } while (cursor.moveToNext());
        }
        // return contact list
        return orderList;
    }
}
