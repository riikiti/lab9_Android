package com.example.lab9.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab9.DatabaseHelper;
import com.example.lab9.Objects.Software;
import com.example.lab9.Objects.Subcategory;
import com.example.lab9.R;

import java.util.ArrayList;
import java.util.List;

public class SoftwareActivity extends AppCompatActivity {

    boolean isNewObject;
    EditText editName, editDescription, editCost, editVersion, editDate, editSubcategory, editSecondName;
    Software software;
    Spinner dropdownList;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);

        initSpin();

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            TextView label = findViewById(R.id.softwareLabel);
            Button editBtn = findViewById(R.id.editSoftware);

            editName = findViewById(R.id.edit_object_name);
            editDescription = findViewById(R.id.edit_object_description);
            editCost = findViewById(R.id.edit_object_cost);
            // editVersion = findViewById(R.id.edit_object_version);
            editDate = findViewById(R.id.edit_object_date);
            editSubcategory = findViewById(R.id.edit_object_subcategory);
            editSecondName = findViewById(R.id.edit_object_secondName);


            isNewObject = arguments.getBoolean("isNewObject");
            if (isNewObject) {
                label.setText("Добавление ПО");
                editBtn.setText("Добавить");
                Button deleteBtn = findViewById(R.id.deleteSoftware);
                deleteBtn.setVisibility(View.GONE);
            }
            else {
                label.setText("Редактирование ПО");
                editBtn.setText("Редактировать");

                software = (Software) arguments.getSerializable(Software.class.getSimpleName());
                editName.setText(software.getName());
                editDescription.setText(software.getSurname());
                editCost.setText(String.format("%s", software.getDate()));
                //editVersion.setText(String.format("%s", software.getGroupe()));
                editDate.setText(software.getDevelopmentDate());
                editSubcategory.setText(software.getSubcategory());
                editSecondName.setText(software.getSecondName());
            }
        }
    }


    public List<?> initSpin() {
        dropdownList = findViewById(R.id.spinner2);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM Subcategory;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        List<String> str = new ArrayList<>();

        do {
            str.add(cursor.getString(1));
        } while (cursor.moveToNext());

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownList.setAdapter(adapter);

        return str;
    }

    public void addOrEdit(View view) {

        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        String secondName = editSecondName.getText().toString();
       // String version = editVersion.getText().toString();
        int cost ;
        try {
            cost = Integer.parseInt(editCost.getText().toString());

        }
        catch (Exception e) {
            Toast.makeText(this, "Проверьте правильность ввода данных!", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = editDate.getText().toString();
        String subcategory = dropdownList.getSelectedItem().toString();

        if (name.trim().length() < 1) {
            Toast.makeText(this, "Введите название ПО!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (subcategory.trim().length() < 1) {
            Toast.makeText(this, "Введите название подкатегории!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        String query = "SELECT _id FROM Subcategory WHERE name = '" + subcategory + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            Toast.makeText(this, "Указанная подкатегория не найдена", Toast.LENGTH_LONG).show();
        }
        else {
            cursor.moveToFirst();
            int subcategoryID = cursor.getInt(0);
            if (isNewObject) {
                query = "INSERT INTO Software (name, description,secondName, cost,  developmentDate, subcategoryID) VALUES ('" + name + "', '" + description + "', '" + secondName + "', " + cost + ", '" + date + "', " + subcategoryID + ");";
                Toast.makeText(this, "Новое ПО добавлено в список!", Toast.LENGTH_SHORT).show();
            }
            else {
                query = "UPDATE Software SET name = '" + name + "', description = '" + description + "', secondName = '" + secondName + "', cost = " + cost + ", developmentDate = '" + date + "', subcategoryID = " + subcategoryID + " WHERE _id = " + software.getId() + ";";
                Toast.makeText(this, "ПО отредактировано!", Toast.LENGTH_SHORT).show();
            }
            db.execSQL(query);
            super.onBackPressed();
        }
        db.close();
        cursor.close();
    }

    public void deleteSoftware(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM Software WHERE _id = " + software.getId() + ";");
        db.close();
        Toast.makeText(this, "ПО удалено!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}