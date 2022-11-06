package com.example.lab9.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab9.Objects.Software;
import com.example.lab9.R;

public class SoftwareActivity extends AppCompatActivity {

    boolean isNewObject;
    EditText editName, editDescription, editCost, editVersion, editDate, editSubcategory;
    Software software;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            TextView label = findViewById(R.id.softwareLabel);
            Button editBtn = findViewById(R.id.editSoftware);

            editName = findViewById(R.id.edit_object_name);
            editDescription = findViewById(R.id.edit_object_description);
            editCost = findViewById(R.id.edit_object_cost);
            editVersion = findViewById(R.id.edit_object_version);
            editDate = findViewById(R.id.edit_object_date);
            editSubcategory = findViewById(R.id.edit_object_subcategory);

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
                editDescription.setText(software.getDescription());
                editCost.setText(Integer.toString(software.getCost()));
                editVersion.setText(Integer.toString(software.getVersion()));
                editDate.setText(software.getDevelopmentDate());
                editSubcategory.setText(software.getSubcategory());
            }
        }
    }

    public void addOrEdit(View view) {
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        int cost = Integer.valueOf(editCost.getText().toString());
        int version = Integer.valueOf(editVersion.getText().toString());
        String date = editDate.getText().toString();
        String subcategory = editSubcategory.getText().toString();

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
                query = "INSERT INTO Software (name, description, cost, version, developmentDate, subcategoryID) VALUES ('" + name + "', '" + description + "', " + cost + ", " + version + ", '" + date + "', " + subcategoryID + ");";
                Toast.makeText(this, "Новое ПО добавлено в список!", Toast.LENGTH_SHORT).show();
            }
            else {
                query = "UPDATE Software SET name = '" + name + "', description = '" + description + "', cost = " + cost + ", version = " + version + ", developmentDate = '" + date + "', subcategoryID = " + subcategoryID + " WHERE _id = " + software.getId() + ";";
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