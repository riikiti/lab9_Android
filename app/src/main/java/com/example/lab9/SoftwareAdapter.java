package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SoftwareAdapter extends ArrayAdapter<Software> {
    private LayoutInflater inflater;
    private int layout;
    private List<Software> softwareList;

    public SoftwareAdapter(Context context, int resource, List<Software> softwareList) {
        super(context, resource, softwareList);
        this.softwareList = softwareList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.itemName);
        TextView descriptionView = view.findViewById(R.id.itemDescription);

        Software software = softwareList.get(position);

        nameView.setText(software.getName());
        descriptionView.setText(software.getCategory() + ", " + software.getSubcategory());

        return view;
    }
}
