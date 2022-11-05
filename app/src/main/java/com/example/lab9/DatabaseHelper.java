package com.example.lab9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Softwares.db";
    private static final int SCHEMA = 1; // DB Version
    static final String TABLE = "Software"; //

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_VERSION = "version";
    public static final String COLUMN_DEVELOPMENT_DATE = "developmentDate";
    public static final String COLUMN_SUBCATEGORY_ID = "subcategoryID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Category table
        db.execSQL("CREATE TABLE IF NOT EXISTS Category ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL UNIQUE);");

        db.execSQL("INSERT INTO Category (_id, name) VALUES (1, 'Прикладное ПО'), (2, 'Системное ПО');");

        // Subcategory table
        db.execSQL("CREATE TABLE IF NOT EXISTS Subcategory ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL UNIQUE,"
                + "categoryID INTEGER NOT NULL,"
                + "FOREIGN KEY(categoryID)"
                + "REFERENCES Category (_id)"
                + "ON UPDATE CASCADE "
                + "ON DELETE CASCADE);");

        db.execSQL("INSERT INTO Subcategory (name, categoryID) VALUES ('Редактор', 1), ('Браузер', 1), ('Архиватор', 1), ('Драйвер', 2), ('ОС', 2), ('Антивирус', 2);");

        // Software table
        db.execSQL("CREATE TABLE IF NOT EXISTS Software ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_COST + " INTEGER,"
                + COLUMN_VERSION + " INTEGER,"
                + COLUMN_DEVELOPMENT_DATE + " TEXT,"
                + COLUMN_SUBCATEGORY_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COLUMN_SUBCATEGORY_ID + ")"
                + "REFERENCES Subcategory(_id)"
                + "ON UPDATE CASCADE "
                + "ON DELETE CASCADE);");

        db.execSQL("INSERT INTO " + TABLE + " (" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_COST + ", " + COLUMN_VERSION + ", " + COLUMN_DEVELOPMENT_DATE + ", " + COLUMN_SUBCATEGORY_ID
                + ") VALUES ('Notepad', 'Блокнот', 100, 1, '01-01-2001', 1), ('Nvidia driver', 'Драйвер для видеокарты Nvidia', 200, 2, '01-01-2002', 4);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public List<Software> getSoftwareList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT Software._id, Software.name, Software.description, Software.cost, Software.version, Software.developmentDate, Category.name, Subcategory.name FROM Software, Category, Subcategory WHERE Software.subcategoryID = Subcategory._id AND Subcategory.categoryID = Category._id;";
        Cursor cursor = db.rawQuery(queryString, null);
        List<Software> softwareList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                softwareList.add(new Software(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return softwareList;
    }
}
