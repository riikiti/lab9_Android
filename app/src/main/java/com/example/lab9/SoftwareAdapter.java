package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class SoftwareAdapter extends ArrayAdapter<Software> {
    private LayoutInflater inflater;
    private int layout;
    private List<Software> softwares;

    public SoftwareAdapter(Context context, int resource, List<Software> softwares) {
        super(context, resource, softwares);
        this.softwares = softwares;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        //

        Software software = softwares.get(position);

        //

        return view;
    }
}
