package com.example.mesut.todolist.core;

/**
 * Created by Janik on 29.01.2018.
 */

public class Priority {

    private int id;
    private String name;
    private int weight;

    public  Priority(int id, String name, int weight){
        this.id = id;
        this.name = name;
        this.weight = weight;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
