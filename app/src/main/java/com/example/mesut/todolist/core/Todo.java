package com.example.mesut.todolist.core;

import java.util.ArrayList;

/**
 * Created by Janik on 24.01.2018.
 */

public class Todo {

    private int id;
    private String title;
    private String desc;
    private String date;
    private int prio_id;
    private ArrayList<Category> cats = new ArrayList<Category>();

    public Todo(){}

    public String catString(){
        String s = "";

        for(Category cat : cats){
            s += "[" + cat.getName() + "] ";
        }

        return s;
    }


    @Override
    public String toString(){
        String s = id + "-" + title + "-" + desc + "-" + date + "-" + prio_id;
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrio_id() {
        return prio_id;
    }

    public void setPrio_id(int prio_id) {
        this.prio_id = prio_id;
    }

    public ArrayList<Category> getCats() {
        return cats;
    }

    public void setCats(ArrayList<Category> cats) {
        this.cats = cats;
    }

}
