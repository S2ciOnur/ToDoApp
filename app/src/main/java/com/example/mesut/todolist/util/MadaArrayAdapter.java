package com.example.mesut.todolist.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This is a custom ArrayAdapter providing a Method for
 * setting the text size of the items.
 * 
 * @author nsteinbiss
 */
public class MadaArrayAdapter<T> extends ArrayAdapter<T>{

	private float textSize = 16;
	private int textSizeUnit = TypedValue.COMPLEX_UNIT_SP;
	
	/**
	 * Creates an empty MadaArrayAdapter
	 * @param context the current context
	 */
	public MadaArrayAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	TextView txt = (TextView) super.getView(position, convertView, parent);
    	txt.setTextSize(textSizeUnit, textSize);
    	return txt;
    }
    
    /**
     * Sets the text size with the given unit.
     * @param textSize the text size
     * @param textSizeUnit the text size unit (see {@link TypedValue} 
     * for possible values) 
     */
    public void setTextSize(float textSize, int textSizeUnit) {
		this.textSize = textSize;
		this.textSizeUnit = textSizeUnit;
	}
    
    /**
     * Returns the text size currently set for this adapter
     * @return the current text size
     */
    public float getTextSize() {
		return textSize;
	}
    
    /**
     * Returns the text size unit currently set for this adapter
     * @return the text size unit
     */
    public int getTextSizeUnit() {
		return textSizeUnit;
	}
}
