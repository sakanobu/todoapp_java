package com.example.sakanobu.todoapp;

public class Todo {
  private int id;
  private String name;

  public Todo(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
