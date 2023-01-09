package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Custom extends BaseAdapter {
    Context context;
    LayoutInflater inflater = null;
    ArrayList<hocsinh> student = new ArrayList<>();

    public Custom(Context context,  ArrayList<hocsinh> student) {
        this.context = context;
        this.student = student;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return student.size();
    }

    @Override
    public Object getItem(int i) {
        return student.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
     if (view==null){
         view = inflater.inflate(R.layout.activity_list_row,viewGroup,false);
     }
        ImageView anh=(ImageView) view.findViewById(R.id.imganh);
        TextView ten =(TextView) view.findViewById(R.id.txtname);
        TextView tuoi = (TextView) view.findViewById(R.id.txtversion);

        ten.setText(student.get(i).Ten);
        tuoi.setText(String.valueOf(student.get(i).Tuoi));
        anh.setImageResource(student.get(i).anh);

        return view;
    }
}
