package com.example.mesut.todolist.core;

/**
 * Created by Janik on 24.01.2018.
 */

public class TodoItem {

    private int id;
    private String title;
    private String desc;
    private int prio;

    public TodoItem(int id, String title, String desc, int prio){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.prio = prio;
    }

    public int getId() {
        return id;
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

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }
}
