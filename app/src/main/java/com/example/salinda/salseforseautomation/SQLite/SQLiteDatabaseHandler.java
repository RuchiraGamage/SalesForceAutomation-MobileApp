package com.example.salinda.salseforseautomation.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper{
    protected static final int DATABASE_VERSION = 1;
    // Database Name
    protected static final String DATABASE_NAME = "SQLite";
    // Table Names
    public static final String TABLE_PRODUCT = "Product";
    public static final String TABLE_VAN_PRODUCT = "VanProduct";
    public static final String TABLE_OUTLETS = "Outlets";
    public static final String TABLE_TODAY_OUTLETS = "TodayOutlets";
    public static final String TABLE_ORDER = "OrderTable";
    public static final String TABLE_ORDER_ITEM = "OrderItem";
    public static final String TABLE_OUTSTANDING = "Outstanding";
    // Product Table Columns Names
    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_BRAND = "brand";
    protected static final String KEY_CATEGORY = "category";
    protected static final String KEY_MANUFACTUREPRICE = "manufacturePrice";
    protected static final String KEY_PRICE = "price";
    protected static final String KEY_QUANTITY = "quantity";
    protected static final String KEY_DISCOUNT = "discount";
    //Outlet Table Columns Name
//    protected static final String KEY_ID = "id";
//    protected static final String KEY_NAME = "name";
    protected static final String KEY_CONTACTNO = "contactNo";
    protected static final String KEY_BARCODE = "barcode";
    protected static final String KEY_LONGITUDE = "longitude";
    protected static final String KEY_LATITUDE = "latitude";
    protected static final String KEY_OWNER_NAME = "ownerName";
    protected static final String KEY_EMAIL = "email";
    protected static final String KEY_STATUS = "status";
    protected static final String KEY_ROUTEID = "routeId";
    //Order Table Columns Name
//    protected static final String KEY_ID = "id";
    protected static final String KEY_DATE = "date";
    protected static final String KEY_DESCRIPTION = "description";
    protected static final String KEY_USERID = "userId";
    protected static final String KEY_OUTLETID = "outletId";
//    protected static final String KEY_LONGITUDE = "longitude";
//    protected static final String KEY_LATITUDE = "latitude";
    protected static final String KEY_PAYMENT_TYPE = "paymentType";
    protected static final String KEY_AMOUNT = "amount";
    protected static final String KEY_DELIVERY_DATE = "deliveryDate";
    protected static final String KEY_ORDER_TYPE = "orderType";
    //Order Item Table Columns Name
//    protected static final String KEY_ID = "id";
    protected static final String KEY_ORDERID = "orderId";
    protected static final String KEY_PRODUCTID = "productId";
    protected static final String KEY_PRODUCT_QUANTITY = "productQuantity";
//    protected static final String KEY_ORDER_TYPE = "orderType";
    //Order Outstanding Columns Name
//    protected static final String KEY_AMOUNT = "amount";
//    protected static final String KEY_OUTLETID = "outletId";



    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_BRAND + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_MANUFACTUREPRICE + " FLOAT," + KEY_PRICE + " FLOAT,"
                + KEY_QUANTITY + " INTEGER," + KEY_DISCOUNT + " TEXT)";
        db.execSQL(CREATE_PRODUCT_TABLE);
        Log.i("Tag ", "Product Table Created");

        String CREATE_VAN_PRODUCT_TABLE = "CREATE TABLE " + TABLE_VAN_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_BRAND + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_MANUFACTUREPRICE + " FLOAT," + KEY_PRICE + " FLOAT,"
                + KEY_QUANTITY + " INTEGER," + KEY_DISCOUNT + " TEXT)";
        db.execSQL(CREATE_VAN_PRODUCT_TABLE);
        Log.i("Tag ", "Van Product Table Created");

        String CREATE_OUTLET_TABLE = "CREATE TABLE " + TABLE_OUTLETS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CONTACTNO + " TEXT," + KEY_BARCODE + " TEXT," + KEY_LONGITUDE + " FLOAT," + KEY_LATITUDE + " FLOAT,"
                + KEY_OWNER_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_STATUS + " TEXT," + KEY_ROUTEID + " INTEGER)";
        db.execSQL(CREATE_OUTLET_TABLE);
        Log.i("Tag ", "Outlet Table Created");

        String CREATE_TODAY_OUTLET_TABLE = "CREATE TABLE " + TABLE_TODAY_OUTLETS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CONTACTNO + " TEXT," + KEY_BARCODE + " TEXT," + KEY_LONGITUDE + " FLOAT," + KEY_LATITUDE + " FLOAT,"
                + KEY_OWNER_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_STATUS + " TEXT," + KEY_ROUTEID + " INTEGER)";
        db.execSQL(CREATE_TODAY_OUTLET_TABLE);
        Log.i("Tag ", "Today Outlet Table Created");

        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_USERID + " INTEGER," + KEY_OUTLETID + " INTEGER," + KEY_LONGITUDE + " FLOAT,"
                + KEY_LATITUDE + " FLOAT," + KEY_PAYMENT_TYPE + " TEXT," + KEY_AMOUNT + " FLOAT," + KEY_DELIVERY_DATE + " TEXT,"
                + KEY_ORDER_TYPE + " TEXT)";
        db.execSQL(CREATE_ORDER_TABLE);
        Log.i("Tag :", "Order Table Created");

        String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE " + TABLE_ORDER_ITEM + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ORDERID + " INTEGER," + KEY_PRODUCTID + " TEXT," + KEY_PRODUCT_QUANTITY + " INTEGER," + KEY_ORDER_TYPE + " TEXT)";
        db.execSQL(CREATE_ORDER_ITEM_TABLE);
        Log.i("Tag :", "Order Item Table Created");

        String CREATE_OUTSTANDING_TABLE = "CREATE TABLE " + TABLE_OUTSTANDING + "(" + KEY_OUTLETID + " INTEGER PRIMARY KEY," + KEY_AMOUNT + " FLOAT)";
        db.execSQL(CREATE_OUTSTANDING_TABLE);
        Log.i("Tag :", "Outstanding Table Created");

    }
    // Upgrading database
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        // Create tables again
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTLETS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODAY_OUTLETS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEM);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTSTANDING);
        onCreate(db);
    }
}
