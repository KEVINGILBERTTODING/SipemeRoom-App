package com.example.sipemroomapp.AdminAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sipemroomapp.Model.TipeModel;

import java.util.List;

public class SpinnerTipeAdapter extends ArrayAdapter<TipeModel> {

    public SpinnerTipeAdapter(@NonNull Context context, List<TipeModel> kategori){
        super(context, android.R.layout.simple_spinner_item, kategori);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setText(getItem(position).getNamaTipe());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setText(getItem(position).getNamaTipe());
        return view;
    }

    // get
    public String getKodeTipe(int position) {
        return getItem(position).getKodeTipe();
    }





}
