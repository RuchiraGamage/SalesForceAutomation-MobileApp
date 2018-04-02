package com.example.salinda.salseforseautomation.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.salinda.salseforseautomation.model.OrderArrayModel;
import com.example.salinda.salseforseautomation.model.SQLiteOrderItemModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteOrderItemHandler extends SQLiteDatabaseHandler {
    Context context;
    public SQLiteOrderItemHandler(Context context){
        super(context);
        this.context = context;
    }

    public void deleteAll(){
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_ORDER_ITEM);
        db.close();
    }

    public void deleteByOrderId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER_ITEM, KEY_ORDERID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void addOrderItemList(List<SQLiteOrderItemModel> orderItemArray){

        for(int i=0; i<orderItemArray.size(); i++){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_ORDERID, orderItemArray.get(i).getOrderId());
            values.put(KEY_PRODUCTID, orderItemArray.get(i).getProductId());
            values.put(KEY_PRODUCT_QUANTITY, orderItemArray.get(i).getProductQuantity());
            values.put(KEY_ORDER_TYPE, orderItemArray.get(i).getOrderType());
            long chack = db.insertOrThrow(TABLE_ORDER_ITEM, null, values);
            if(chack == -1){i--;}
            db.close();
        }
    }

    public List<OrderArrayModel> getProductItemByOrderId(int orderId) {
        List<OrderArrayModel> orderItemList = new ArrayList<OrderArrayModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER_ITEM + " WHERE " + KEY_ORDERID + "=?";

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(orderId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OrderArrayModel orderItemModel = new OrderArrayModel();
                orderItemModel.setProductId(cursor.getInt(2));
                orderItemModel.setProductQuantity(cursor.getInt(3));
                orderItemModel.setOrderType(cursor.getString(4));

                orderItemList.add(orderItemModel);
            } while (cursor.moveToNext());
        }
        // return contact list
        return orderItemList;
    }
}
