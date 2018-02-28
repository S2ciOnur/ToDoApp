package com.example.mesut.todolist.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;

import java.util.ArrayList;

/**
 * Created by Janik on 05.02.2018.
 */

public class CatListAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Category> cats;
    private int textUnit;
    private float textSize;

    public CatListAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        textUnit = TypedValue.COMPLEX_UNIT_SP;
        textSize = 12;

        this.context = context;
        cats = objects;
    }

    private class ViewHolder {
        TextView txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_category_settings, null);

            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.categoryTextView);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category cat = cats.get(position);
        holder.txtName.setText(cat.getName());
        holder.txtName.setTextSize(textUnit, textSize);
        return convertView;

    }

    public void setTextSize(int textUnit, float textSize) {
        this.textSize = textSize;
        this.textUnit = textUnit;
    }
}
