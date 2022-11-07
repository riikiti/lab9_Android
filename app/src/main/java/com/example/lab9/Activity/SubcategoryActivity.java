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

import com.example.lab9.Objects.Subcategory;
import com.example.lab9.R;

public class SubcategoryActivity extends AppCompatActivity {

    boolean isNewObject;
    EditText editName, editCategory;
    Subcategory subcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        editName = findViewById(R.id.edit_subcategory_name);
        editCategory = findViewById(R.id.edit_subcategory_category);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            TextView label = findViewById(R.id.subcategoryLabel);
            Button editBtn = findViewById(R.id.editSubcategory);

            isNewObject = arguments.getBoolean("isNewObject");
            if (isNewObject) {
                label.setText("Добавление подкатегории");
                editBtn.setText("Добавить");
                Button deleteBtn = findViewById(R.id.deleteSubcategory);
                deleteBtn.setVisibility(View.GONE);
            } else {
                label.setText("Редактирование подкатегории");
                editBtn.setText("Редактировать");

                subcategory = (Subcategory) arguments.getSerializable(Subcategory.class.getSimpleName());
                editName.setText(subcategory.getName());
                editCategory.setText(subcategory.getCategory());
            }
        }
    }

    public void addOrEdit(View view) {
        String name = editName.getText().toString();
        String category = editCategory.getText().toString();

        if (name.trim().length() < 1) {
            Toast.makeText(this, "Введите название подкатегории!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (category.trim().length() < 1) {
            Toast.makeText(this, "Введите категорию!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        String query = "SELECT _id FROM Category WHERE name = '" + category + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            Toast.makeText(this, "Указанная категория не найдена", Toast.LENGTH_LONG).show();
        }
        else {
            cursor.moveToFirst();
            int categoryID = cursor.getInt(0);
            if (isNewObject) {
                query = "INSERT INTO Subcategory (name, categoryID) VALUES ('" + name + "', " + categoryID + ");";
                Toast.makeText(this, "Новая подкатегория добавлена в список!", Toast.LENGTH_SHORT).show();
            }
            else {
                query = "UPDATE Subcategory SET name = '" + name + "', categoryID = " + categoryID + " WHERE _id = " + subcategory.getId() + ";";
                Toast.makeText(this, "Подкатегория отредактирована!", Toast.LENGTH_SHORT).show();
            }
            db.execSQL(query);
            super.onBackPressed();
            db.close();
        }
    }

    public void deleteSubcategory(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM Subcategory WHERE _id = " + subcategory.getId() + ";");
        db.close();
        Toast.makeText(this, "Подкатегория удалена!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}