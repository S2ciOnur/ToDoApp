package com.example.mesut.todolist.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Todo;
import com.example.mesut.todolist.db.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Nihal on 1/24/2017.
 */

public class TodoListAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Todo> todos;
    private DatabaseHelper dbh;

    public TodoListAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context = context;
        todos = objects;

    }

    private class ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
        TextView txtPrio;
        TextView txtDate;
        TextView txtCats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_todolist, null);

            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.titleTextView);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.descTextView);
            holder.txtPrio =(TextView) convertView.findViewById(R.id.prioTextView);
            holder.txtDate =(TextView) convertView.findViewById(R.id.dateTextView);
            holder.txtCats = (TextView) convertView.findViewById(R.id.catsTextView);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Todo todo = todos.get(position);

        holder.txtTitle.setText(todo.getTitle());
        holder.txtDesc.setText(todo.getDesc());
        //holder.txtPrio.setText(dbh.getPrioNameById(todo.getPrio_id()));
        holder.txtDate.setText(todo.getDate());

        holder.txtCats.setText(todo.catString());

        return convertView;
    }
}