package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor softwareCursor;
    ListView softwareListView;
    SoftwareAdapter softwareAdapter;
    int currentItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
        softwareListView = findViewById(R.id.softwareList);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean mydb = getBaseContext().deleteDatabase("Softwares.db");
        db = databaseHelper.getReadableDatabase();

        softwareCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        List<Software> softwareList = databaseHelper.getSoftwareList();
        softwareAdapter = new SoftwareAdapter(this, R.layout.list_software_item, softwareList);
        softwareListView.setAdapter(softwareAdapter);
        softwareListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Software selected = (Software) parent.getItemAtPosition(position);
                TextView currentItem = findViewById(R.id.currentItem);
                if (currentItemId == selected.getId() && currentItem.getVisibility() == View.VISIBLE) {
                    currentItem.setVisibility(View.GONE);
                }
                else {
                    if (currentItem.getVisibility() == View.GONE) currentItem.setVisibility(View.VISIBLE);
                    String str = "Выбранный объект:\nНазвание: " + selected.getName()
                            + "\nОписание: " + selected.getDescription()
                            + "\nСтоимость: " + selected.getCost()
                            + "\nВерсия: " + selected.getVersion()
                            + "\nДата разработки: " + selected.getDevelopmentDate()
                            + "\nКатегория: " + selected.getCategory()
                            + "\nПодкатегория: " + selected.getSubcategory();
                    currentItem.setText(str);
                    currentItemId = selected.getId();
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        softwareCursor.close();
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
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}