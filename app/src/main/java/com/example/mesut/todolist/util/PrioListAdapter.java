package com.example.mesut.todolist.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Priority;

import java.util.ArrayList;

/**
 * Created by Janik on 05.02.2018.
 */
public class PrioListAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Priority> prios;

    public PrioListAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context = context;
        prios = objects;

    }

    private class ViewHolder {
        TextView txtName;
        TextView txtWeight;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_priority_settings, null);

            holder = new PrioListAdapter.ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.priorityTextView);
            holder.txtWeight = (TextView) convertView.findViewById(R.id.weightTextView);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Priority prio = prios.get(position);
        holder.txtName.setText(prio.getName());
        holder.txtWeight.setText(prio.getWeight() + "");
        return convertView;

    }
}
