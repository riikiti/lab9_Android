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

import com.example.lab9.Objects.Category;
import com.example.lab9.R;

public class CategoryActivity extends AppCompatActivity {

    boolean isNewObject;
    EditText editName;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        editName = findViewById(R.id.edit_category_name);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            TextView label = findViewById(R.id.categoryLabel);
            Button editBtn = findViewById(R.id.editCategory);

            isNewObject = arguments.getBoolean("isNewObject");
            if (isNewObject) {
                label.setText("Добавление категории");
                editBtn.setText("Добавить");
                Button deleteBtn = findViewById(R.id.deleteCategory);
                deleteBtn.setVisibility(View.GONE);
            } else {
                label.setText("Редактирование категории");
                editBtn.setText("Редактировать");

                category = (Category) arguments.getSerializable(Category.class.getSimpleName());
                editName.setText(category.getName());
            }
        }
    }

    public void addOrEdit(View view) {
        String name = editName.getText().toString();

        if (name.trim().length() < 1) {
            Toast.makeText(this, "Введите название категории!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        String query;
        if (isNewObject) {
            query = "INSERT INTO Category (name) VALUES ('" + name + "');";
            Toast.makeText(this, "Новая категория добавлена в список!", Toast.LENGTH_SHORT).show();
        } else {
            query = "UPDATE Category SET name = '" + name + "' WHERE _id = " + category.getId() + ";";
            Toast.makeText(this, "Категория отредактирована!", Toast.LENGTH_SHORT).show();
        }
        db.execSQL(query);
        super.onBackPressed();
        db.close();
    }

    public void deleteCategory(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Softwares.db", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM Category WHERE _id = " + category.getId() + ";");
        db.close();
        Toast.makeText(this, "Категория удалена!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}