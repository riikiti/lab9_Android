package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
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