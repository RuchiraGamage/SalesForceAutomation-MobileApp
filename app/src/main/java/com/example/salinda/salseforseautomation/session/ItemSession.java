package com.example.salinda.salseforseautomation.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.salinda.salseforseautomation.model.PQModel;

import java.util.ArrayList;
import java.util.List;

public class ItemSession {
    SharedPreferences pref; // Shared Preferences
    SharedPreferences.Editor editor;  // Editor for Shared preferences
    Context _context;   // Context
    int PRIVATE_MODE = 0;   // Shared pref mode

    private static final String PREF_NAME = "ItemCount";   // Sharedpref file name
    public static final String KEY_PRODUCTID = "ProductId";
    public static final String KEY_QUANTITY = "Quantity";
    public static final String KEY_ORDER_TYPE = "OrderType";
    public static final String KEY_NAME = "Name";
    public static final String KEY_BRAND = "Brand";
    public static final String KEY_PRICE = "Price";
    public static final String KEY_ITEM_COUNT = "ItemCount";
    public static final String KEY_OUTLETID = "outletId";
    //public static final String KEY_T_QUANTITY = "T_Quantity";

    public ItemSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void starItemCount(){
        editor.putInt(KEY_ITEM_COUNT, 0);
        editor.commit();
    }

    public void incrementItemCount(){
        int i = pref.getInt(KEY_ITEM_COUNT, 0)+1;
        editor.putInt(KEY_ITEM_COUNT, i);
        editor.commit();
    }

    public int getItemCount(){
        return pref.getInt(KEY_ITEM_COUNT,0);
    }

    public void setOutletId(int outletId){
        editor.putInt(KEY_OUTLETID, outletId);
        editor.commit();
    }

    public int getOutletId(){return pref.getInt(KEY_OUTLETID, 0);}

    /*public void setQuantity(int quantity){
        editor.putInt(KEY_T_QUANTITY, quantity);
        editor.commit();
    }*/

    //public int getQuantity(){return pref.getInt(KEY_T_QUANTITY, 0);}

    public void setItem(int productId, int quantity, String orderType, String name, String brand, float price){
        editor.putInt(KEY_PRODUCTID+getItemCount(), productId);
        editor.putInt(KEY_QUANTITY+getItemCount(), quantity);
        editor.putString(KEY_ORDER_TYPE+getItemCount(), orderType);
        editor.putString(KEY_NAME+getItemCount(), name);
        editor.putString(KEY_BRAND+getItemCount(), brand);
        editor.putFloat(KEY_PRICE+getItemCount(), price);
        editor.commit();
        incrementItemCount();
    }

    public void editItemSession(ArrayList<PQModel> list1, ArrayList<PQModel> list2){
        int temp = getOutletId();
        clearItemSession();
        setOutletId(temp);
        starItemCount();
        //int i;
        for(int i = 0; i<list1.size(); i++){
            setItem(list1.get(i).getProductId(), list1.get(i).getQuantity(), list1.get(i).getOrderType(), list1.get(i).getName(), list1.get(i).getBrand(), list1.get(i).getPrice());
        }
        for(int i = 0; i<list2.size(); i++){
             setItem(list2.get(i).getProductId(), list2.get(i).getQuantity(), list2.get(i).getOrderType(), list2.get(i).getName(), list2.get(i).getBrand(), list2.get(i).getPrice());
        }
    }

    public void clearItemSession(){
        editor.clear();
        editor.commit();
    }

    /*public HashMap<String, String> getItem(int position){
        HashMap<String, String> item = new HashMap<>();
        item.put(KEY_PRODUCTID, Integer.toString(pref.getInt(KEY_PRODUCTID+position, 0)));
        item.put(KEY_QUANTITY, Integer.toString(pref.getInt(KEY_QUANTITY+position, 0)));
        item.put(KEY_ORDER_TYPE, pref.getString(KEY_ORDER_TYPE+position, null));
        item.put(KEY_NAME, pref.getString(KEY_NAME+position, null));
        item.put(KEY_BRAND, pref.getString(KEY_BRAND+position, null));
        item.put(KEY_PRICE, Float.toString(pref.getFloat(KEY_PRICE+position, 0.0f)));

        return item;
    }*/

    public List<PQModel> getAllItem(){
        List<PQModel> pqModels = new ArrayList<>();
        for(int i=0; i<getItemCount(); i++){
            PQModel pqModel = new PQModel(pref.getInt(KEY_PRODUCTID+i, 0), pref.getInt(KEY_QUANTITY+i, 0),
                    pref.getString(KEY_ORDER_TYPE+i, null), pref.getString(KEY_NAME+i, null), pref.getString(KEY_BRAND+i, null),
                    pref.getFloat(KEY_PRICE+i, 0.00f));
            pqModels.add(i, pqModel);
        }
        return pqModels;
    }
}
