package com.example.lab9.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab9.Objects.Subcategory;
import com.example.lab9.R;

import java.util.List;

public class SubcategoryAdapter extends ArrayAdapter<Subcategory> {
    private final LayoutInflater inflater;
    private final int layout;
    private final List<Subcategory> subcategoryList;

    public SubcategoryAdapter(Context context, int resource, List<Subcategory> subcategoryList) {
        super(context, resource, subcategoryList);
        this.subcategoryList = subcategoryList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.itemName);
        TextView descriptionView = view.findViewById(R.id.itemDescription);

        Subcategory subcategory = subcategoryList.get(position);

        nameView.setText(subcategory.getName());
        descriptionView.setText("ID: " + subcategory.getId() + ", Категория: " + subcategory.getCategory());

        return view;
    }
}
