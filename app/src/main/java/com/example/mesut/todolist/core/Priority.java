package com.example.mesut.todolist.core;

/**
 * Created by Janik on 29.01.2018.
 *
 */

public class Priority {

    private int id;
    private String name;
    private int weight;

    public  Priority(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
