package com.example.sakanobu.todoapp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public String listTasks(Model model) {
    Task inputTask = new Task("", "", "", LocalDateTime.now(), LocalDateTime.now());
    List<Task> allTasks = tasksDao.findAll();
    model.addAttribute("task", inputTask);
    model.addAttribute("allTasks", allTasks);
    return "todos";
  }

  @PostMapping
  public String createTask(@Validated Task inputTask, BindingResult bindingResult,
                           @RequestParam("title") String title, Model model) {
    if (bindingResult.hasErrors()) {
      List<Task> allTasks = tasksDao.findAll();
      model.addAttribute("allTasks", allTasks);
      model.addAttribute("task", inputTask);
      return "todos";
    }

    String id = UUID.randomUUID().toString();
    Task task = new Task(id, title, "UNFINISHED", LocalDateTime.now(), LocalDateTime.now());
    tasksDao.create(task);
    return "redirect:/todos";
  }

  @GetMapping("/{id}/edit")
  public String editTaskForm(@PathVariable("id") String id, Model model) {
    Task task = tasksDao.findById(id);
    List<String> statusList = List.of("UNFINISHED", "FINISHED");
    model.addAttribute("task", task);
    model.addAttribute("statusList", statusList);
    return "todo_edit";
  }

  @PutMapping("/{id}")
  public String updateTask(@Validated Task requestTask,
                           BindingResult bindingResult,
                           @PathVariable("id") String id, @RequestParam("title") String title,
                           @RequestParam("status") String status,
                           @RequestParam("createdAt") LocalDateTime createdAt, Model model) {
    if (bindingResult.hasErrors()) {
      List<String> statusList = List.of("UNFINISHED", "FINISHED");
      model.addAttribute("task", requestTask);
      model.addAttribute("statusList", statusList);
      return "todo_edit";
    }

    Task task = new Task(id, title, status, createdAt,
        LocalDateTime.now());
    tasksDao.update(task);
    return "redirect:/todos";
  }

  @DeleteMapping("/{id}")
  public String deleteTask(@PathVariable("id") String id) {
    tasksDao.delete(id);
    return "redirect:/todos";
  }
}
