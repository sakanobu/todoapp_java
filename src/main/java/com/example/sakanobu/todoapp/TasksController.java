package com.example.sakanobu.todoapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    Task inputTask =
        new Task(null, "", "", "", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now());
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

    Task task = new Task(null, title, "未完了", "中", LocalDate.now(), LocalDateTime.now(),
        LocalDateTime.now());
    tasksDao.create(task);
    return "redirect:/todos";
  }

  @GetMapping("/{id}/edit")
  public String editTaskForm(@PathVariable("id") Integer id, Model model) {
    Task task = tasksDao.findById(id);
    List<String> statusList = List.of("未完了", "完了", "削除済み");
    List<String> priorityList = List.of("低", "中", "高");
    model.addAttribute("task", task);
    model.addAttribute("statusList", statusList);
    model.addAttribute("priorityList", priorityList);
    return "todo_edit";
  }

  @PutMapping("/{id}")
  public String updateTask(@Validated Task requestTask, BindingResult bindingResult,
                           @PathVariable("id") Integer id, @RequestParam("title") String title,
                           @RequestParam("status") String status,
                           @RequestParam("priority") String priority,
                           @RequestParam("dueDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           LocalDate dueDate,
                           @RequestParam("createdAt") LocalDateTime createdAt, Model model) {
    if (bindingResult.hasErrors()) {
      List<String> statusList = List.of("未完了", "完了", "削除済み");
      List<String> priorityList = List.of("低", "中", "高");
      model.addAttribute("task", requestTask);
      model.addAttribute("statusList", statusList);
      model.addAttribute("priorityList", priorityList);
      return "todo_edit";
    }

    Task task = new Task(id, title, status, priority, dueDate, createdAt, LocalDateTime.now());
    tasksDao.update(task);
    return "redirect:/todos";
  }

  @DeleteMapping("/{id}")
  public String deleteTask(@PathVariable("id") Integer id) {
    tasksDao.delete(id);
    return "redirect:/todos";
  }
}
