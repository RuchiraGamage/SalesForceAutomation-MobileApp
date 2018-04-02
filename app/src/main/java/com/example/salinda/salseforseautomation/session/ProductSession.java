package com.example.salinda.salseforseautomation.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.salinda.salseforseautomation.model.ProductsModel;

import java.util.List;

public class ProductSession {
    SharedPreferences pref; // Shared Preferences
    SharedPreferences.Editor editor;  // Editor for Shared preferences
    Context _context;   // Context
    int PRIVATE_MODE = 0;   // Shared pref mode
    private static final String PREF_NAME = "Product";   // Sharedpref file name
    public static final String KEY_ID = "Id";
    public static final String KEY_NAME = "Name";
    public static final String KEY_BRAND = "Brand";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_MANUFACTUREPRICE = "ManufacturePrice";
    public static final String KEY_PRICE = "Price";
    public static final String KEY_QUANTITY = "Quantity";
    public static final String KEY_DISCOUNT_TYPE = "DiscountType";
    public static final String KEY_LINE_COUNTER = "LineCounter";
    public int modelSize;

    public ProductSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLineCounter(int count){
        editor.putInt(KEY_LINE_COUNTER, count);
        editor.commit();
    }
    public int getLineCounter(){return pref.getInt(KEY_LINE_COUNTER, 0);}
    /**
     * Create Product session
     */
    public void createProductSession(List<ProductsModel> productsModel){
        modelSize = productsModel.size();
        setLineCounter(modelSize);
        for(int i=0; i < productsModel.size(); i++){
            editor.putInt(KEY_ID+String.valueOf(i), productsModel.get(i).getId());
            editor.putString(KEY_NAME+String.valueOf(i), productsModel.get(i).getName());
            editor.putString(KEY_BRAND+String.valueOf(i), productsModel.get(i).getBrand());
            editor.putString(KEY_CATEGORY+String.valueOf(i), productsModel.get(i).getCategory());
            editor.putFloat(KEY_MANUFACTUREPRICE+String.valueOf(i),productsModel.get(i).getManufacturePrice());
            editor.putFloat(KEY_PRICE+String.valueOf(i), productsModel.get(i).getPrice());
            editor.putInt(KEY_QUANTITY+String.valueOf(i), productsModel.get(i).getQuantity());
            editor.putString(KEY_DISCOUNT_TYPE+String.valueOf(i), productsModel.get(i).getDiscountType());
        }
        editor.commit();
    }

    /**
     * Clear product session
     */
    public void clearProductSession(){
        editor.clear();
        editor.commit();
    }

    /**
     * get product session data
     */
    /*public HashMap<String, String> getProductData(int position){
        HashMap<String, String> product = new HashMap<String, String>();
        product.put(KEY_ID, Integer.toString(pref.getInt(KEY_ID+position, 0)));
        product.put(KEY_NAME, pref.getString(KEY_NAME+position, null));
        product.put(KEY_BRAND, pref.getString(KEY_BRAND+position, null));
        product.put(KEY_CATEGORY, pref.getString(KEY_CATEGORY+position, null));
        product.put(KEY_MANUFACTUREPRICE, Integer.toString(pref.getInt(KEY_MANUFACTUREPRICE+position, 0)));
        product.put(KEY_PRICE, Float.toString(pref.getFloat(KEY_PRICE+position, 0.00f)));
        product.put(KEY_QUANTITY, Integer.toString(pref.getInt(KEY_QUANTITY+position, 0)));
        product.put(KEY_DISCOUNT_TYPE, pref.getString(KEY_DISCOUNT_TYPE+position, null));

        return product;
    }*/
    /*public List<ProductsModel> getAllProduct(){
        List<ProductsModel> productsModels = new ArrayList<>();

        for (int i=0; i<getLineCounter(); i++){
            ProductsModel productsModel = new ProductsModel(pref.getInt(KEY_ID+i, 0), pref.getString(KEY_NAME+i, null),
                    pref.getString(KEY_BRAND+i, null), pref.getString(KEY_CATEGORY+i, null), pref.getInt(KEY_MANUFACTUREPRICE+i, 0),
                    pref.getFloat(KEY_PRICE+i, 0.00f), pref.getInt(KEY_QUANTITY+i, 0), pref.getString(KEY_DISCOUNT_TYPE+i, null));
            productsModels.add(i,productsModel);
        }
        return productsModels;
    }*/

}
