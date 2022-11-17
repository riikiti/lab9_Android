package com.example.lab9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab9.Objects.Category;
import com.example.lab9.Objects.Software;
import com.example.lab9.Objects.Subcategory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Softwares.db";
    private static final int SCHEMA = 1; // DB Version
    static final String TABLE = "Software";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_GROUPE = "groupe";
    public static final String COLUMN_DEVELOPMENT_DATE = "developmentDate";
    public static final String COLUMN_SUBCATEGORY_ID = "subcategoryID";
    public static final String COLUMN_SECOND_NAME = "secondName";

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
                + COLUMN_SECOND_NAME + " TEXT,"
                + COLUMN_COST + " INTEGER,"
                + COLUMN_GROUPE + " TEXT,"
                + COLUMN_DEVELOPMENT_DATE + " TEXT,"
                + COLUMN_SUBCATEGORY_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COLUMN_SUBCATEGORY_ID + ")"
                + "REFERENCES Subcategory(_id)"
                + "ON UPDATE CASCADE "
                + "ON DELETE CASCADE);");

        db.execSQL("INSERT INTO " + TABLE + " (" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_SECOND_NAME + ", " + COLUMN_COST + ", " + COLUMN_GROUPE + ", " + COLUMN_DEVELOPMENT_DATE + ", " + COLUMN_SUBCATEGORY_ID
                + ") VALUES ('Notepad', 'Блокнот','Блокнот', 100, 'MOA', '01-01-2001', 1), ('Nvidia driver','Блокнот', 'Драйвер для видеокарты Nvidia', 200, 'pri', '01-01-2002', 4);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public List<Software> getSoftwareList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT Software._id, Software.name, Software.description,Software.secondName, Software.cost, Software.version, Software.developmentDate, Category.name, Subcategory.name FROM Software, Category, Subcategory WHERE Software.subcategoryID = Subcategory._id AND Subcategory.categoryID = Category._id;";
        Cursor cursor = db.rawQuery(queryString, null);
        List<Software> softwareList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                softwareList.add(new Software(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return softwareList;
    }

    public List<Category> getCategoryList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT _id, name FROM Category;";
        Cursor cursor = db.rawQuery(queryString, null);
        List<Category> categoryList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                categoryList.add(new Category(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return categoryList;
    }

    public List<Subcategory> getSubcategoryList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT Subcategory._id, Subcategory.name, Category.name FROM Subcategory, Category WHERE Subcategory.categoryID = Category._id;";
        Cursor cursor = db.rawQuery(queryString, null);
        List<Subcategory> subcategoryList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                subcategoryList.add(new Subcategory(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return subcategoryList;
    }
}
