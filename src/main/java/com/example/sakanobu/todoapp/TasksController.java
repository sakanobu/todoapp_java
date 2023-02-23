package com.example.sakanobu.todoapp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/todos")
public class TasksController {
  private final TasksDao tasksDao;

  @Autowired
  public TasksController(TasksDao tasksDao) {
    this.tasksDao = tasksDao;
  }

  @GetMapping
  public String listTodos(Model model) {
    List<Task> allTasks = tasksDao.findAll();
    model.addAttribute("allTasks", allTasks);
    return "todos";
  }

  @PostMapping
  public String createTodo(@RequestParam("title") String title) {
    String id = UUID.randomUUID().toString();
    Task task = new Task(id, title, "UNFINISHED", new Timestamp(System.currentTimeMillis()),
        new Timestamp(System.currentTimeMillis()));
    tasksDao.create(task);
    // tasksDao.createByString(title);
    return "redirect:/todos";
  }
}
