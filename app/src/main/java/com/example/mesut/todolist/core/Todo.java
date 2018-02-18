package com.example.mesut.todolist.core;

/**
 * Created by Janik on 24.01.2018.
 */

public class Todo {

    private int id;
    private String title;
    private String desc;
    private String date;
    private int prio_id;

    public Todo(){}

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

}
