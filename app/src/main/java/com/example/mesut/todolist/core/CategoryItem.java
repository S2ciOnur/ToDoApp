package com.example.mesut.todolist.core;

/**
 * Created by Janik on 24.01.2018.
 */

public class CategoryItem {

    private int id;
    private String category;

    public CategoryItem(String category){
        this.category = category;
    }
    public int getId() {
        return id;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
