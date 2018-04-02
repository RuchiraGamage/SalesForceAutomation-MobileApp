package com.example.salinda.salseforseautomation.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.salinda.salseforseautomation.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteProductHandler extends SQLiteDatabaseHandler {
    Context context;
    public SQLiteProductHandler(Context context) {
        super(context);
        this.context = context;
    }

    public void deleteAll(){
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_PRODUCT);
        db.close();
    }

    public void addProductList(List<ProductsModel> productsModels) {
        for(int i=0;i<productsModels.size();i++) {

            android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_ID, productsModels.get(i).getId());
            values.put(KEY_NAME, productsModels.get(i).getName());
            values.put(KEY_BRAND, productsModels.get(i).getBrand());
            values.put(KEY_CATEGORY, productsModels.get(i).getCategory());
            values.put(KEY_MANUFACTUREPRICE, productsModels.get(i).getManufacturePrice());
            values.put(KEY_PRICE, productsModels.get(i).getPrice());
            values.put(KEY_QUANTITY, productsModels.get(i).getQuantity());
            values.put(KEY_DISCOUNT,productsModels.get(i).getDiscountType());
            long chack = db.insertOrThrow(TABLE_PRODUCT, null, values);
            if(chack == -1){i--;}
            db.close();

        }
    }

    public List<ProductsModel> getAllProduct() {
        List<ProductsModel> productList = new ArrayList<ProductsModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductsModel productModel = new ProductsModel();
                productModel.setId(Integer.parseInt(cursor.getString(0)));
                productModel.setName(cursor.getString(1));
                productModel.setBrand(cursor.getString(2));
                productModel.setCategory(cursor.getString(3));
                productModel.setManufacturePrice(Float.parseFloat(cursor.getString(4)));
                productModel.setPrice(Float.parseFloat(cursor.getString(5)));
                productModel.setQuantity(Integer.parseInt(cursor.getString(6)));
                productModel.setDiscountType(cursor.getString(7));
                // Adding contact to list
                productList.add(productModel);
            } while (cursor.moveToNext());
        }
        // return contact list
        return productList;
    }
}
