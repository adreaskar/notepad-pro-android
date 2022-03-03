package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "NotepadPro.db";

    public DBHelper(Context context) {
        super(context, "NotepadPro.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table users(username TEXT primary key, password TEXT, email TEXT)");
        MyDB.execSQL("create table notes(title TEXT primary key, body TEXT, favourite TEXT, visibility TEXT, user TEXT, notedate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop table if exists users");
        MyDB.execSQL("drop table if exists notes");
    }

    // Insert User Data function ---------------------------------------------------
    public Boolean insertUserData (String username, String password, String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",username);
        values.put("password",password);
        values.put("email",email);

        long result = MyDB.insert("users", null, values);
        return result != -1;
    }

    // Check User Username Function ----------------
    public Boolean checkUsername (String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username=?", new String[] {username});
        return cursor.getCount() > 0;
    }

    //Check Username and Password Function ---------------------------------
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username=? and password=?", new String[] {username,password});
        return cursor.getCount() > 0;
    }

    // Insert Note Data function ---------------------------------------------------------------------------------------------
    public Boolean insertNoteData (String title, String body, String favourite, String visibility, String user, String date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title",title);
        values.put("body",body);
        values.put("favourite",favourite);
        values.put("visibility",visibility);
        values.put("user",user);
        values.put("notedate",date);

        long result = MyDB.insert("notes", null, values);
        return result != -1;
    }

    // Edit Note Data function -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Boolean editNoteData (String title, String newtitle, String body, String newbody, String favourite, String newfavourite, String visibility, String newvisibility, String user, String date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title",newtitle);
        values.put("body",newbody);
        values.put("favourite",newfavourite);
        values.put("visibility",newvisibility);
        values.put("user",user);
        values.put("notedate",date);

        long result = MyDB.update("notes", values, "title=? AND body=? AND favourite=? AND visibility=? and user=? AND notedate=?",new String[] {title,body,favourite,visibility,user,date});
        return result != -1;
    }

    public Boolean deleteNote (String title, String body, String favourite, String visibility, String user, String date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        long result = MyDB.delete("notes", "title=? AND body=? AND favourite=? AND visibility=? AND user=? AND notedate=?", new String[]{title,body,favourite,visibility,user,date});
        return result != -1;
    }

    public Cursor readPrivateNotes(String user) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from notes where user=? and favourite=? ORDER BY title", new String[] {user,"no"});
        return cursor;
    }

    public Cursor readFavouriteNotes(String user) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from notes where user=? and favourite=? ORDER BY notedate DESC", new String[] {user,"yes"});
        return cursor;
    }

    public Cursor readGlobalNotes(String user) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from notes where visibility=? ORDER BY notedate DESC", new String[] {"public"});
        return cursor;
    }
}
