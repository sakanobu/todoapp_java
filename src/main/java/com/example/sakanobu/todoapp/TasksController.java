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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/todos")
public class TasksController {
  private final TasksDao tasksDao;
  private final List<String> filterList = List.of("未完了", "未削除", "全て");
  private final List<String> sortList = List.of("期限日", "優先度", "作成日");
  private final List<String> statusList = List.of("未完了", "完了", "削除済み");
  private final List<String> priorityList = List.of("低", "中", "高");

  @Autowired
  public TasksController(TasksDao tasksDao) {
    this.tasksDao = tasksDao;
  }

  @ModelAttribute("filterList")
  public List<String> getFilterList() {
    return filterList;
  }

  @ModelAttribute("sortList")
  public List<String> getSortList() {
    return sortList;
  }

  @ModelAttribute("statusList")
  public List<String> getStatusList() {
    return statusList;
  }

  @ModelAttribute("priorityList")
  public List<String> getPriorityList() {
    return priorityList;
  }

  //  @GetMapping
  //  public String listTasks(Model model) {
  //    Task inputTask =
  //        new Task(null, "", "", "", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now());
  //    List<Task> allTasks = tasksDao.findAll();
  //    model.addAttribute("task", inputTask);
  //    model.addAttribute("allTasks", allTasks);
  //    return "todos";
  //  }

  @GetMapping
  public String listUnfinishedDueDateTasks(Model model) {
    Task inputTask =
        new Task(null, "", "", "", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now());
    List<Task> targetTasks = tasksDao.findByStatusUnfinishedOrderByDueDateAsc();

    model.addAttribute("task", inputTask);
    model.addAttribute("targetTasks", targetTasks);

    return "todos";
  }

  @PostMapping
  public String createTask(@Validated Task inputTask, BindingResult bindingResult,
                           @RequestParam("title") String title, Model model) {
    if (bindingResult.hasErrors()) {
      List<Task> targetTasks = tasksDao.findByStatusUnfinishedOrderByDueDateAsc();

      model.addAttribute("targetTasks", targetTasks);
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

    model.addAttribute("task", task);

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
      model.addAttribute("task", requestTask);

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
