package com.example.androidapplication.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class SampleDBManager {

    //declare and initialize the database variables
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CONTACTNAME = "contact_name";
    public static final String KEY_CONTACTADDRESS1 = "contact_address1";
    public static final String KEY_CONTACTADDRESS2 = "contact_address2";
    public static final String KEY_CONTACTCOUNTY = "contact_county";
    public static final String KEY_CONTACTCONTACTNO= "contact_contactno";

    private static final String DBNAME = "Contacts_DB";
    private static final String TABLENAME = "ContactDetails";
    private static final int DBVERSION ='1';


    private  final Context context;
    private  MyDatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //create the table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" +
            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_CONTACTNAME + " TEXT NOT NULL, " +
            KEY_CONTACTADDRESS1 + " TEXT, " +
            KEY_CONTACTADDRESS2 + " TEXT, " +
            KEY_CONTACTCOUNTY + " TEXT, " +
            KEY_CONTACTCONTACTNO + " TEXT NOT NULL);";

    public SampleDBManager(Context ctx){
        this.context=ctx;
        DBHelper = new MyDatabaseHelper(context);
    }//end Constructor

    private static class MyDatabaseHelper extends SQLiteOpenHelper{

        MyDatabaseHelper(Context context) {
            super(context,DBNAME,null,DBVERSION);
        }//end Constructor

        @Override
        public void onCreate(SQLiteDatabase db) {//create the table
            try{
                db.execSQL(CREATE_TABLE);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }//end onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//upgrade the database
            Log.w("SampleDBManager","Upgrading Database");
            db.execSQL("DROP TABLE IF EXISTS ContactDetails");
            onCreate(db);
        }//end onUpgrade

    }//end inner class

    public SampleDBManager open(){//open the database
        try{
            db=DBHelper.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  this;
    }

    public SampleDBManager openReadable() {
        db = DBHelper.getReadableDatabase();
        return this;
    }//end openReadable


    public void close(){
        DBHelper.close();
    }//end close

    //insert row into the table
    public long insertRow(String name, String address1, String address2,
                                String county, String contactno){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_CONTACTNAME, name);
            contentValues.put(KEY_CONTACTADDRESS1,address1);
            contentValues.put(KEY_CONTACTADDRESS2,address2);
            contentValues.put(KEY_CONTACTCOUNTY,county);
            contentValues.put(KEY_CONTACTCONTACTNO,contactno);
            return db.insert(TABLENAME,KEY_ROWID,contentValues);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }//end insertRow

    //insert data from the table
    public Cursor getSomething(String rowid )throws SQLException{
        Cursor mCursor = db.query(true,TABLENAME, new String[]{
                        KEY_ROWID,
                        KEY_CONTACTNAME,
                        KEY_CONTACTADDRESS1,
                        KEY_CONTACTADDRESS2,
                        KEY_CONTACTCOUNTY,
                        KEY_CONTACTCONTACTNO
                },
                KEY_ROWID +"=" + rowid,null,null,null,null,null);

        if(mCursor !=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }//end getSomething

    //insert all the rows from the table
    public Cursor getRow()throws SQLException{
        Cursor mCursor = db.query(true,TABLENAME, new String[]{
                        KEY_ROWID,
                        KEY_CONTACTNAME,
                        KEY_CONTACTADDRESS1,
                        KEY_CONTACTADDRESS2,
                        KEY_CONTACTCOUNTY,
                        KEY_CONTACTCONTACTNO
                },
                null,null,null,null,null,null);

        if(mCursor !=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }//end getRow

    //update the row in the table
    public long updateRow(String RowId, String name, String address1, String address2,
                                String county, String contactno){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_ROWID, RowId);
            contentValues.put(KEY_CONTACTNAME, name);
            contentValues.put(KEY_CONTACTADDRESS1,address1);
            contentValues.put(KEY_CONTACTADDRESS2,address2);
            contentValues.put(KEY_CONTACTCOUNTY,county);
            contentValues.put(KEY_CONTACTCONTACTNO, contactno);
            return db.update(TABLENAME, contentValues, KEY_ROWID + "=" + RowId, null);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }//end updateRow

    //delete a row in the table
    public long deleteRow(String row){
        try{
            return db.delete(TABLENAME, KEY_ROWID + "=" + row, null);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }//end deleteRow

    //search for a name in the table
    public Cursor searchRow(String name)throws SQLException{
        Cursor mCursor = db.query(true,TABLENAME, new String[]{
                        KEY_ROWID,
                        KEY_CONTACTNAME,
                        KEY_CONTACTADDRESS1,
                        KEY_CONTACTADDRESS2,
                        KEY_CONTACTCOUNTY,
                        KEY_CONTACTCONTACTNO
                },
                "contact_name LIKE '%" + name + "%'",null,null,null,null,null);

        if(mCursor !=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }//end searchRow



}//end class
