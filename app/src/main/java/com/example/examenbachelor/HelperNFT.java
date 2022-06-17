package com.example.examenbachelor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.examenbachelor.bean.NFT;
import com.example.examenbachelor.bean.Note;
import com.example.examenbachelor.bean.Users;

import java.util.ArrayList;
import java.util.List;

public class HelperNFT extends SQLiteOpenHelper{
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ChatRingan";

    // Table name: Note.
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_NFT = "NFT";

    private static final String COLUMN_PSEUDO ="Pseudo";
    private static final String COLUMN_PASSWORD ="Password";
    private static final String COLUMN_MAIL_ADDRESS = "AdresseMail";
    private static final String COLUMN_SOLDE = "Solde";
    private static final String COLUMN_DATE_OF_BIRTH = "DateDeNaissance";

    private static final String COLUMN_PRIX = "Prix";
    private static final String COLUMN_NOM = "Nom";
    private static final String COLUMN_CREATOR = "Createur";
    private static final String COLUMN_PROPRIETAIRE = "Propriétaire";
    private static final String COLUMN_IMAGE = "Image";

    public HelperNFT(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String tableUsers = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_PSEUDO + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_SOLDE + " REAL,"
                + COLUMN_DATE_OF_BIRTH + " NUMERIC,"
                + COLUMN_MAIL_ADDRESS + " TEXT" + ")";

        String tableNFT = "CREATE TABLE " + TABLE_NFT + "("
                + COLUMN_NOM + " TEXT PRIMARY KEY,"
                + COLUMN_PRIX + " REAL,"
                + COLUMN_CREATOR + " TEXT,"
                + COLUMN_PROPRIETAIRE + " TEXT,"
                + COLUMN_IMAGE + " BLOB" + ")";

        // Execute Script.
        db.execSQL(tableUsers);
        db.execSQL(tableNFT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NFT);

        // Create tables again
        onCreate(db);
    }

    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultUsersIfNeed()  {
        int count = this.getUsersCount();
        if(count ==0 ) {
            Users user1 = new Users("PseudoCool", "1234", "AdresseMailCool@gmail.com", "1998-11-07");
            Users user2 = new Users("Grassouillet", "1234", "Grassouillet@gmail.com", "1998-11-07");

            this.addUser(user1);
            this.addUser(user2);
        }
    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PSEUDO, user.getPseudo());
        values.put(COLUMN_PASSWORD, user.getMdp());
        values.put(COLUMN_DATE_OF_BIRTH, user.getDoB());
        values.put(COLUMN_MAIL_ADDRESS, user.getAdresseMail());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);

        // Closing database connection
        db.close();
    }

    public Users getUsers(String pseudo) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { COLUMN_PSEUDO,
                        COLUMN_PASSWORD, COLUMN_MAIL_ADDRESS, COLUMN_DATE_OF_BIRTH }, COLUMN_PSEUDO + "=?",
                new String[] { pseudo }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return user
        return user;
    }

    public List<Users> getAllUsers() {
        Log.i("AllUser", "MyDatabaseHelper.getAllNotes ... " );

        List<Users> usersList = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setPseudo((cursor.getString(0)));
                user.setMdp(cursor.getString(1));
                user.setAdresseMail(cursor.getString(2));
                // Adding note to list
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        // return note list
        return usersList;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int updateUsers(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PSEUDO, user.getPseudo());
        values.put(COLUMN_PASSWORD, user.getMdp());
        values.put(COLUMN_MAIL_ADDRESS, user.getAdresseMail());
        values.put(COLUMN_DATE_OF_BIRTH, user.getDoB());

        // updating row
        return db.update(TABLE_USERS, values, COLUMN_PSEUDO + " = ?",
                new String[]{String.valueOf(user.getPseudo())});
    }

    public void deleteUsers(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_PSEUDO + " = ?",
                new String[] { user.getPseudo() });
        db.close();
    }
}
