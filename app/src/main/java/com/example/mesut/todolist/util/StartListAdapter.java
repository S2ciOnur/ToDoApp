package com.example.mesut.todolist.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;

import java.util.ArrayList;

/**
 * Created by Janik on 19.02.2018.
 */

public class StartListAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Category> cats;

    public StartListAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context = context;
        cats = objects;

    }

    private class ViewHolder {
        TextView txtName;
        TextView txtAnz;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_all_categories, null);

            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.categoryTextView);
            holder.txtAnz = (TextView) convertView.findViewById(R.id.anzTextView);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category cat = cats.get(position);
        holder.txtName.setText(cat.getName());
        holder.txtAnz.setText(cat.getId() + "");
        return convertView;
    }
}
