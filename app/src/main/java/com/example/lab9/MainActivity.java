package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.lab9.Adapters.CategoryAdapter;
import com.example.lab9.Adapters.SoftwareAdapter;
import com.example.lab9.Adapters.SubcategoryAdapter;
import com.example.lab9.Objects.Category;
import com.example.lab9.Objects.Software;
import com.example.lab9.Objects.Subcategory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ListView softwareListView;
    ListView editListView;
    SoftwareAdapter softwareAdapter;
    CategoryAdapter categoryAdapter;
    SubcategoryAdapter subcategoryAdapter;
    int currentItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
        softwareListView = findViewById(R.id.softwareList);
        editListView = findViewById(R.id.editList);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean mydb = getBaseContext().deleteDatabase("Softwares.db");
        db = databaseHelper.getReadableDatabase();

        List<Software> softwareList = databaseHelper.getSoftwareList();
        softwareAdapter = new SoftwareAdapter(this, R.layout.list_item, softwareList);
        softwareListView.setAdapter(softwareAdapter);
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
                        + "\nНазвание: " + selected.getName()
                        + "\nОписание: " + selected.getDescription()
                        + "\nСтоимость: " + selected.getCost()
                        + "\nВерсия: " + selected.getVersion()
                        + "\nДата разработки: " + selected.getDevelopmentDate()
                        + "\nКатегория: " + selected.getCategory()
                        + "\nПодкатегория: " + selected.getSubcategory();
                currentItem.setText(str);
                currentItemId = selected.getId();
            }
        });
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

        Spinner dropdown = findViewById(R.id.itemSpinner);
        String[] spinnerItems = new String[]{"Объекты", "Категории", "Подкатегории"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        dropdown.setAdapter(spinnerAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tabHost.setCurrentTabByTag("Catalog Tab");
    }
}