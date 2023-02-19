package com.example.sakanobu.todoapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TasksController {
  private final TasksDao tasksDao;

  @Autowired
  public TasksController(TasksDao tasksDao) {
    this.tasksDao = tasksDao;
  }

  @GetMapping("/todos")
  public String listTodos(Model model) {
    List<Task> allTasks = tasksDao.findAll();
    model.addAttribute("allTasks", allTasks);
    return "todos";
  }
}
