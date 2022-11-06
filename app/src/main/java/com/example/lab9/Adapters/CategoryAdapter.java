package com.example.lab9.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab9.Objects.Category;
import com.example.lab9.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final LayoutInflater inflater;
    private final int layout;
    private final List<Category> categoryList;

    public CategoryAdapter(Context context, int resource, List<Category> categoryList) {
        super(context, resource, categoryList);
        this.categoryList = categoryList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.itemName);
        TextView descriptionView = view.findViewById(R.id.itemDescription);

        Category category = categoryList.get(position);

        nameView.setText(category.getName());
        descriptionView.setText("ID: " + category.getId());

        return view;
    }
}
