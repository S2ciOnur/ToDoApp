package com.example.mesut.todolist.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.TodoItem;

import java.util.ArrayList;

/**
 * Created by Nihal on 1/24/2017.
 */

public class CategoryListAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<CategoryItem> CategoryItems;

    public CategoryListAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context = context;
        CategoryItems = objects;

    }

    private class ViewHolder
    {
        TextView txtCategory;
        Button deleteCategoryBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_todolist, null);

            holder = new ViewHolder();
            holder.txtCategory = (TextView) convertView.findViewById(R.id.category);
            holder.deleteCategoryBtn = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        TodoItem todoItem = todoItems.get(position);
        holder.txtCategory.setText(categoryITem.getCategory() + "");
        holder.txtTitle.setText("Description: "+ todoItem.getDesc()+"");
        holder.txtDesc.setText("Priority: "+todoItem.getPrio());
        return convertView;


    }
}