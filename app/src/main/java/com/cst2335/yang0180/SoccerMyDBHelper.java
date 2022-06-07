package com.cst2335.yang0180;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class SoccerMyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "chat";
    private static final int VERSION = 2;
    public static final String TABLE_MESSAGE = "message";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_ISSENT = "issent";
    private static final String CREATE_MESSAGE_TABLE = " CREATE TABLE "+TABLE_MESSAGE+" ( "
                                                                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                                                                + COLUMN_CONTENT + " TEXT, "
                                                                + COLUMN_ISSENT + " INTEGER )";
    public static final int MESSAGE_SENT = 1;
    public static final int MESSAGE_RECEIVE = 0;

    public SoccerMyDBHelper(@Nullable Context context){
        this(context, DB_NAME,null,VERSION);
    }

    public SoccerMyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MESSAGE);
        db.execSQL(CREATE_MESSAGE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MESSAGE);
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MESSAGE);
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    public long insertMessage(SoccerMessage message){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTENT,message.getContent());
        if (message.isSend())
        {
            cv.put(COLUMN_ISSENT, MESSAGE_SENT);
        }else{
            cv.put(COLUMN_ISSENT, MESSAGE_RECEIVE);
        }
        long newId = db.insert(TABLE_MESSAGE, null, cv);
        return newId;
    }

    public ArrayList<SoccerMessage> readAllMessage(){

        ArrayList<SoccerMessage> messages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MESSAGE,null,null,null,null,null,null);
        while( cursor.moveToNext() ){
            this.printCursor(cursor,db.getVersion());
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int contentIndex = cursor.getColumnIndex(COLUMN_CONTENT);
            int issentIndex = cursor.getColumnIndex(COLUMN_ISSENT);

            long id = cursor.getLong(idIndex);
            String content = cursor.getString(contentIndex);
            int isSent = cursor.getInt(issentIndex);
            if(isSent == MESSAGE_SENT){
                SoccerMessage message = new SoccerMessage(id,true,content);
                messages.add(message);
            }else{
                SoccerMessage message = new SoccerMessage(id,false,content);
                messages.add(message);
            }
        }
        return messages;

    }

    public boolean deleteMessage( long messageId){

        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = db.delete(TABLE_MESSAGE, COLUMN_ID+" = ?",new String[]{messageId+""});
        return affectedRows > 0;

    }

    private void printCursor(Cursor cursor, int version){
        /**
         * •	The database version number, use db.getVersion() for the version number.
         * •	The number of columns in the cursor.
         * •	The name of the columns in the cursor.
         * •	The number of rows in the cursor
         * •	Print out each row of results in the cursor.
         */
        Log.e("Version", version+"" );
        Log.e("number of columns", cursor.getColumnCount()+"" );
        Log.e(" name of the columns", Arrays.toString(cursor.getColumnNames()) );
        Log.e("number of rows", cursor.getCount()+"" );
        Log.e("message id ",cursor.getLong(cursor.getColumnIndex(COLUMN_ID))+"");
        Log.e("message content", cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)) );
        Log.e("message issent", cursor.getInt(cursor.getColumnIndex(COLUMN_ISSENT))+"" );
    }

}
