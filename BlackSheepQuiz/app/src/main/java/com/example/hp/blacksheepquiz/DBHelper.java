package com.example.hp.blacksheepquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 22/5/2559.
 */
public class DBHelper {

    private static final String DATABASE_NAME = "recStage.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "recStage";
    private static final String INSERT = "insret into " + TABLE_NAME + " (id, stagerec) value (?, ?)";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private List<Integer> idList = new ArrayList<>();
    private List<Integer> stageList = new ArrayList<>();

    public DBHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();

    }

    public List<Integer> getStageList() {
        return stageList;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void insert(int id, int stage) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("stage", stage);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public int update(long id, int stage) {
        int numRows;
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("stage", stage);
        numRows = db.update(TABLE_NAME, contentValues, " id = " + id, null);
        Log.v("DBHelper", "num rows updated is " + numRows);
        return numRows;
    }

    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    public void selectById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{
                new Integer(id).toString()
        };
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{"id", "stage"}, whereClause, whereArgs, null, null, null);
        idList = new ArrayList<Integer>();
        stageList = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                idList.add(cursor.getInt(0));
                stageList.add(cursor.getInt(1));
                Log.v("DBHelper", new Integer(cursor.getInt(0)).toString());
                Log.v("DBHelper", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public void selectAll() {
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{"id", "stage"}, null, null, null, null, null);
        idList = new ArrayList<Integer>();
        stageList = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                idList.add(cursor.getInt(0));
                stageList.add(cursor.getInt(1));
                Log.v("DBHelper", new Integer(cursor.getInt(0)).toString());
                Log.v("DBHelper", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        private static String TAG_NAME = "OpenHelper";

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.v(TAG_NAME, "Creating table");
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(id integer primary key, stage integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.v(TAG_NAME, "Upgrading database");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }
    }

}
