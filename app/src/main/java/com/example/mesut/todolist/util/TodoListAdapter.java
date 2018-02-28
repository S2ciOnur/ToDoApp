package com.example.mesut.todolist.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.activities.MainActivity;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Todo;
import com.example.mesut.todolist.db.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Mesut
 */

public class TodoListAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Todo> todos;
    private ArrayList<Priority> prios;
    private DatabaseHelper dbh;
    private int textUnit;
    private float textSize;

    public TodoListAdapter(Context context, int textViewResourceId, ArrayList todos , ArrayList<Priority> prios) {
        super(context,textViewResourceId, todos);

        this.context = context;
        this.todos = todos;
        this.prios = prios;

        this.textSize = 12;
        this.textUnit = TypedValue.COMPLEX_UNIT_SP;
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

        int prio_id = todo.getPrio_id();
        String prioName = "0";
        for(Priority prio : prios){
            if(prio.getId() == prio_id){
                prioName = prio.getName();
            }
        }

        holder.txtTitle.setText(todo.getTitle());
        holder.txtTitle.setTextSize(textUnit, textSize);
        holder.txtDesc.setText(todo.getDesc());
        holder.txtDesc.setTextSize(textUnit, textSize);
        holder.txtPrio.setText(prioName);
        holder.txtPrio.setTextSize(textUnit, textSize);
        holder.txtDate.setText(todo.getDate());
        holder.txtDate.setTextSize(textUnit, textSize);
        holder.txtCats.setText(todo.catString());
        holder.txtCats.setTextSize(textUnit, textSize);

        return convertView;
    }

    public void setTextSize(int textUnit, float textSize) {
        this.textSize = textSize;
        this.textUnit = textUnit;
    }
}