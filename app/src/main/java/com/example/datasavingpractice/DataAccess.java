package com.example.datasavingpractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataAccess extends SQLiteOpenHelper {
    private static final String DBNAME = "myContacts";
    private static final int VERSION = 1;
    private static final String CONTACTS_TABLE = "contacts";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";

    public DataAccess(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + CONTACTS_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT," + COL_PHONE + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, contact.getName());
        contentValues.put(COL_PHONE, contact.getPhone());
        db.insert(CONTACTS_TABLE, null, contentValues);
        db.close();
    }

    public Contact getContact() {
        Contact contact = new Contact();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_NAME, COL_PHONE};
        Cursor c = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE, null);
        c.moveToLast();
        contact.setName(c.getString(1));
        contact.setPhone(c.getString(2));
        db.close();
        c.close();
        return contact;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
