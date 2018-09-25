package com.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Sumit on 14/10/2015.
 */
public class SqlLiteDbHelper extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_PATH = "/data/data/com.albaniancircle/databases/";
    // Database Name
    private static final String DATABASE_NAME = "options.sqlite";
    // Contacts table name
    private static final String TABLE_COUNTRY = "tblcountry";
    private static final String TABLE_STATES = "tblstates";
    private static final String TABLE_OPTIONS = "tbloptions";
    private static final String COL_OPTIONS = "options";
    private static final String COL_QID = "q_id";
    private SQLiteDatabase db;
    // Contacts Table Columns names
    private static final String COUNTRY_ID = "country_id";
    private static final String STATE_ID = "state_id";
    private static final String COUNTRY_NAME = "country_name";
    private static final String STATE_NAME = "state_name";


    Context ctx;

    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }


    // Getting single contact
    public ArrayList<CountryModel> getCountryList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <CountryModel> countrylist = new ArrayList <>();


        try
        {
//            Cursor c =null;
//            c = db.rawQuery("SELECT " + COL_ID_PRODUCT + " FROM "
//                    + TABLE_PRODUCT + " WHERE " + COL_CATEGORY + "=  '" + idcat + "'", null);

            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_COUNTRY, null);

            if(c.moveToFirst())

            {
                for(int i=0;i<c.getCount();i++)

                {

                    Log.d("sumit","name of country= "+c.getString(c.getColumnIndex(COUNTRY_NAME)));

                    CountryModel country=new CountryModel();

                    country.setCountry_id(c.getString(c.getColumnIndex(COUNTRY_ID)));
                    country.setCountry_name(c.getString(c.getColumnIndex(COUNTRY_NAME)));

                    countrylist.add(country);
                    c.moveToNext();

                }
                c.close();
                db.close();
            }

            CountryModel countrytemp=new CountryModel();

            countrytemp.setCountry_id("");
            countrytemp.setCountry_name("Country");
            countrylist.add(0,countrytemp);

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<CountryModel> getStateList(String idcat) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <CountryModel> countrylist = new ArrayList <>();


        try
        {
//            Cursor c =null;
//            c = db.rawQuery("SELECT " + COL_ID_PRODUCT + " FROM "
//                    + TABLE_PRODUCT + " WHERE " + COL_CATEGORY + "=  '" + idcat + "'", null);

            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_STATES + " WHERE " + COUNTRY_ID + "=  '" + idcat + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {



                    CountryModel country=new CountryModel();

                    country.setState_id(c.getString(c.getColumnIndex(STATE_ID)));
                    country.setState_name(c.getString(c.getColumnIndex(STATE_NAME)));

                    countrylist.add(country);
                    c.moveToNext();
                }



                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    // Getting tall list
    public ArrayList<String> getTallList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
//            Cursor c =null;
//            c = db.rawQuery("SELECT " + COL_ID_PRODUCT + " FROM "
//                    + TABLE_PRODUCT + " WHERE " + COL_CATEGORY + "=  '" + idcat + "'", null);

            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "13" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

//                    CountryModel country=new CountryModel();

//                    country.setCountry_id(c.getString(c.getColumnIndex(COUNTRY_ID)));
//                    country.setCountry_name(c.getString(c.getColumnIndex(COUNTRY_NAME)));

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<String> getHearAboutList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
//            Cursor c =null;
//            c = db.rawQuery("SELECT " + COL_ID_PRODUCT + " FROM "
//                    + TABLE_PRODUCT + " WHERE " + COL_CATEGORY + "=  '" + idcat + "'", null);

            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "10" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

//                    CountryModel country=new CountryModel();

//                    country.setCountry_id(c.getString(c.getColumnIndex(COUNTRY_ID)));
//                    country.setCountry_name(c.getString(c.getColumnIndex(COUNTRY_NAME)));

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<String> getRelgionList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "15" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<String> getLanguagesList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "19" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<String> getDescribeYourselfList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "20" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getInterestList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "32" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<String> getPetList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "21" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }




    public ArrayList<String> getKindOfMoviesList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "22" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getDOUDrinkList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <String> countrylist = new ArrayList <>();


        try
        {
            Cursor c = db.rawQuery("SELECT * FROM "
                    + TABLE_OPTIONS+" WHERE " + COL_QID + "=  '" + "23" + "'", null);

            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {

                    countrylist.add(c.getString(c.getColumnIndex(COL_OPTIONS)));
                    c.moveToNext();
                }
                c.close();
                db.close();
            }

            return countrylist;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


//    448-376-838

    public void CopyDataBaseFromAsset() throws IOException {
//        InputStream in = ctx.getAssets().open("options");
        InputStream in = ctx.getAssets().open("options.sqlite");
        Log.d("sumit", "Starting copying");
        String outputFileName = DATABASE_PATH + DATABASE_NAME;
        File databaseFile = new File(DATABASE_PATH);
        // check if databases folder exists, if not create one and its subfolders
        if (!databaseFile.exists()) {
            databaseFile.mkdir();
        }

        OutputStream out = new FileOutputStream(outputFileName);

        byte[] buffer = new byte[1024];
        int length;


        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        Log.d("sumit", "Completed");
        out.flush();
        out.close();
        in.close();

    }


    public void openDataBase() throws SQLException {
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS );
        try {
            CopyDataBaseFromAsset();
            openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
