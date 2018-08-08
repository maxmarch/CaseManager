package com.mpoznyak.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Todo {

    private int id;
    private List<String> mTasks;

    public Todo() {
        mTasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTasks(List<String> tasks) {
        mTasks = tasks;
    }

    public List<String> getTasks() {
        return mTasks;
    }

    public void addTask(String task) throws IllegalArgumentException, NullPointerException {

        if (mTasks == null) {
            throw new NullPointerException("A list of tasks wasn't initialized");
        }

        if (task == null || task.equals("") || task.equals(" ")) {
            throw new IllegalArgumentException("A task should have a meaningful name");
        }
        mTasks.add(task);

    }

    public String removeTask(int index) {
        return mTasks.remove(index);
    }


}
