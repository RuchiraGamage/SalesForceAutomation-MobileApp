package com.example.salinda.salseforseautomation.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.salinda.salseforseautomation.model.OutletModel;

import java.util.ArrayList;
import java.util.List;

public class OutletSession {
    SharedPreferences pref; // Shared Preferences
    SharedPreferences.Editor editor;  // Editor for Shared preferences
    Context _context;   // Context
    int PRIVATE_MODE = 0;   // Shared pref mode
    private static final String PREF_NAME = "Outlet";   // Sharedpref file name
    public static final String KEY_ID = "Id";
    public static final String KEY_NAME = "Name";
    public static final String KEY_CONTACTNO = "ContactNo";
    public static final String KEY_BARCODE = "Barcode";
    public static final String KEY_LONGITUDE = "Longitude";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_OWNERNAME = "OwnerName";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_ROUTEID = "RouteId";
    public static final String KEY_LINE_COUNTER = "LineCounter";
    public int modelSize;

    public OutletSession(Context context){
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
    public void createOutletSession(List<OutletModel> outletModels){
        modelSize = outletModels.size();
        setLineCounter(modelSize);
        for(int i=0; i < outletModels.size(); i++){
            editor.putInt(KEY_ID+String.valueOf(i), outletModels.get(i).getId());
            editor.putString(KEY_NAME+String.valueOf(i), outletModels.get(i).getName());
            editor.putString(KEY_CONTACTNO+String.valueOf(i), outletModels.get(i).getContactNo());
            editor.putString(KEY_BARCODE+String.valueOf(i), outletModels.get(i).getBarcode());
            editor.putFloat(KEY_LONGITUDE+String.valueOf(i), outletModels.get(i).getLongitude());
            editor.putFloat(KEY_LATITUDE+String.valueOf(i), outletModels.get(i).getLatitude());
            editor.putString(KEY_OWNERNAME+String.valueOf(i), outletModels.get(i).getOwnerName());
            editor.putString(KEY_EMAIL+String.valueOf(i), outletModels.get(i).getEmail());
            editor.putString(KEY_STATUS+String.valueOf(i), String.valueOf(outletModels.get(i).getStatus()));
            editor.putInt(KEY_ROUTEID+String.valueOf(i), outletModels.get(i).getRouteId());
        }
        editor.commit();
    }

    /**
     * Clear product session
     */
    public void clearOutletSession(){
        editor.clear();
        editor.commit();
    }

    /**
     * get product session data
     */
    public List<OutletModel> getAllOutlet(){
        List<OutletModel> outletModels = new ArrayList<>();
        for(int i=0; i<getLineCounter(); i++){
            OutletModel outletModel = new OutletModel(pref.getInt(KEY_ID+i, 0), pref.getString(KEY_NAME+i, null),
                    pref.getString(KEY_CONTACTNO+i, null), pref.getString(KEY_BARCODE+i, null), pref.getFloat(KEY_LONGITUDE+i, 0.0000f),
                    pref.getFloat(KEY_LATITUDE+i, 0.0000f), pref.getString(KEY_OWNERNAME+i, null), pref.getString(KEY_EMAIL+i, null),
                    pref.getString(KEY_STATUS+i, null), pref.getInt(KEY_ROUTEID+i, 0));
            outletModels.add(i, outletModel);
        }
        return outletModels;
    }

}
