package com.example.lab9.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab9.Adapters.CategoryAdapter;
import com.example.lab9.Adapters.SoftwareAdapter;
import com.example.lab9.Adapters.SubcategoryAdapter;
import com.example.lab9.DatabaseHelper;
import com.example.lab9.Objects.Category;
import com.example.lab9.Objects.Software;
import com.example.lab9.Objects.Subcategory;
import com.example.lab9.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    ListView softwareListView;
    ListView editListView;
    Spinner dropdownList;

    SoftwareAdapter softwareAdapter;
    CategoryAdapter categoryAdapter;
    SubcategoryAdapter subcategoryAdapter;

    int currentItemId = -1;
    int currentTable = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
        softwareListView = findViewById(R.id.softwareList);
        editListView = findViewById(R.id.editList);
        dropdownList = findViewById(R.id.itemSpinner);
        String[] spinnerItems = new String[]{"Студенты", "Факультеты", "Группы"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        dropdownList.setAdapter(spinnerAdapter);
        initListeners();
        
        databaseHelper = new DatabaseHelper(getApplicationContext());

        // TODO: Delete after finishing
        //getBaseContext().deleteDatabase("Softwares.db");

        db = databaseHelper.getReadableDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Update tabs when user returned to MainActivity
        List<Software> softwareList = databaseHelper.getSoftwareList();
        softwareAdapter = new SoftwareAdapter(this, R.layout.list_item, softwareList);
        softwareListView.setAdapter(softwareAdapter);
        selectDropdownItem(currentTable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }

    private void initTabs() {
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;
        // Catalog Tab
        tabSpec = tabHost.newTabSpec("Catalog Tab");
        tabSpec.setIndicator("Каталог");
        tabSpec.setContent(R.id.tabCatalog);
        tabHost.addTab(tabSpec);

        // Edit Tab
        tabSpec = tabHost.newTabSpec("Edit Tab");
        tabSpec.setIndicator("Редактирование");
        tabSpec.setContent(R.id.tabEdit);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("Catalog Tab");
    }

    private void initListeners() {
        // Catalog tab object click
        softwareListView.setOnItemClickListener((parent, view, position, id) -> {
            Software selected = (Software) parent.getItemAtPosition(position);
            TextView currentItem = findViewById(R.id.currentItem);
            if (currentItemId == selected.getId() && currentItem.getVisibility() == View.VISIBLE) {
                currentItem.setVisibility(View.GONE);
            } else {
                if (currentItem.getVisibility() == View.GONE)
                    currentItem.setVisibility(View.VISIBLE);
                String str = "Выбранный объект:"
                        + "\nID: " + selected.getId()
                        + "\nИмя: " + selected.getName()
                        + "\nФамилия: " + selected.getSurname()
                        + "\nОтчество: " + selected.getSecondName()
                        + "\nКурс: " + selected.getDate()
                        + "\nДата рождения: " + selected.getDevelopmentDate()
                        + "\nФакультет: " + selected.getCategory()
                        + "\nГруппа: " + selected.getSubcategory();
                currentItem.setText(str);
                currentItemId = selected.getId();
            }
        });

        // Edit tab object click
        editListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent;
            switch (currentTable) {
                case 0:
                    Software software = (Software) parent.getItemAtPosition(position);
                    intent = new Intent(this, SoftwareActivity.class);
                    intent.putExtra(Software.class.getSimpleName(), software);
                    break;
                case 1:
                    Category category = (Category) parent.getItemAtPosition(position);
                    intent = new Intent(this, CategoryActivity.class);
                    intent.putExtra(Category.class.getSimpleName(), category);
                    break;
                case 2:
                    Subcategory subcategory = (Subcategory) parent.getItemAtPosition(position);
                    intent = new Intent(this, SubcategoryActivity.class);
                    intent.putExtra(Subcategory.class.getSimpleName(), subcategory);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + currentTable);
            }
            intent.putExtra("isNewObject", false);
            startActivity(intent);
        });

        // Edit tab dropdown menu change selected item
        dropdownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentTable = position;
                selectDropdownItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void selectDropdownItem(int position) {
        switch (position) {
            case 0:
                List<Software> softwareList = databaseHelper.getSoftwareList();
                softwareAdapter = new SoftwareAdapter(MainActivity.this, R.layout.list_item, softwareList);
                editListView.setAdapter(softwareAdapter);
                break;
            case 1:
                List<Category> categoryList = databaseHelper.getCategoryList();
                categoryAdapter = new CategoryAdapter(MainActivity.this, R.layout.list_item, categoryList);
                editListView.setAdapter(categoryAdapter);
                break;
            case 2:
                List<Subcategory> subcategoryList = databaseHelper.getSubcategoryList();
                subcategoryAdapter = new SubcategoryAdapter(MainActivity.this, R.layout.list_item, subcategoryList);
                editListView.setAdapter(subcategoryAdapter);
                break;
        }
    }

    public void addObject(View view) {
        Intent intent;
        switch (currentTable) {
            case 0:
                intent = new Intent(this, SoftwareActivity.class);
                break;
            case 1:
                intent = new Intent(this, CategoryActivity.class);
                break;
            case 2:
                intent = new Intent(this, SubcategoryActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentTable);
        }
        intent.putExtra("isNewObject", true);
        startActivity(intent);
    }
}