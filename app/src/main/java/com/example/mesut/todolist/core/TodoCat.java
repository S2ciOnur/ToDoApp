package com.example.mesut.todolist.core;

/**
 * Created by Janik on 29.01.2018.
 */

public class TodoCat {

    private int todo_id;
    private int cat_id;

    public TodoCat(int todo_id, int cat_id){
        this.todo_id = todo_id;
        this.cat_id = cat_id;
    }

    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
