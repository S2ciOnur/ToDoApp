package com.example.mesut.todolist.core;

/**
 * Created by Janik on 29.01.2018.
 */

public class Category {

    private int id;
    private String name;

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
