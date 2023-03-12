package com.example.sakanobu.todoapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todos")
public class TasksController {
  private final TasksDao tasksDao;

  @Autowired
  public TasksController(TasksDao tasksDao) {
    this.tasksDao = tasksDao;
  }

  @ModelAttribute("filterList")
  public List<String> getFilterList() {
    return List.of("未完了", "未削除", "全て");
  }

  @ModelAttribute("sortList")
  public List<String> getSortList() {
    return List.of("期限日", "優先度", "作成日");
  }

  @ModelAttribute("statusList")
  public List<String> getStatusList() {
    return List.of("未完了", "完了", "削除済み");
  }

  @ModelAttribute("priorityList")
  public List<String> getPriorityList() {
    return List.of("低", "中", "高");
  }

  @GetMapping
  public String listUnfinishedDueDateTasks(Model model) {
    model.addAttribute("task",
        new Task(null, "", "", "", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now()));

    Optional.ofNullable((Map<String, String>) model.getAttribute("queryParameterMap"))
        .ifPresentOrElse(q -> {
          model.addAttribute("queryParameterMap", q);
          model.addAttribute("targetTasks", tasksDao.getFilteredSortedTasks(q));
        }, () -> {
          model.addAttribute("queryParameterMap", null);
          model.addAttribute("targetTasks", tasksDao.getFilteredSortedTasks(null));
        });

    return "todos";
  }

  @GetMapping(params = {"filter", "sort"})
  public String listTasksByQueryParameter(@RequestParam String filter, @RequestParam String sort,
                                          Model model) {
    Map<String, String> queryParameterMap = Map.of("filter", filter, "sort", sort);

    model.addAttribute("queryParameterMap", queryParameterMap);
    model.addAttribute("task",
        new Task(null, "", "", "", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now()));
    model.addAttribute("targetTasks", tasksDao.getFilteredSortedTasks(queryParameterMap));

    return "todos";
  }

  @PostMapping
  public String createTask(@Validated Task inputTask, BindingResult bindingResult,
                           @RequestParam("title") String title,
                           RedirectAttributes redirectAttributes, Model model) {
    Map<String, String> queryParameterMap = Map.of("filter", "未完了", "sort", "期限日");

    if (bindingResult.hasErrors()) {
      model.addAttribute("queryParameterMap", queryParameterMap);
      model.addAttribute("targetTasks", tasksDao.getFilteredSortedTasks(queryParameterMap));
      model.addAttribute("task", inputTask);

      return "todos";
    }

    tasksDao.create(new Task(null, title, "未完了", "中", LocalDate.now(), LocalDateTime.now(),
        LocalDateTime.now()));

    redirectAttributes.addFlashAttribute("queryParameterMap", queryParameterMap);

    return "redirect:/todos";
  }

  @PostMapping(params = {"filter", "sort"})
  public String createTask(@Validated Task inputTask, BindingResult bindingResult,
                           @RequestParam("title") String title,
                           @RequestParam("filter") String filter, @RequestParam("sort") String sort,
                           RedirectAttributes redirectAttributes, Model model) {
    Map<String, String> queryParameterMap = Map.of("filter", filter, "sort", sort);

    if (bindingResult.hasErrors()) {
      model.addAttribute("queryParameterMap", queryParameterMap);
      model.addAttribute("targetTasks", tasksDao.getFilteredSortedTasks(queryParameterMap));
      model.addAttribute("task", inputTask);

      return "todos";
    }

    tasksDao.create(new Task(null, title, "未完了", "中", LocalDate.now(), LocalDateTime.now(),
        LocalDateTime.now()));

    redirectAttributes.addFlashAttribute("queryParameterMap", queryParameterMap);

    return "redirect:/todos";
  }

  @GetMapping("/{id}/edit")
  public String editTaskForm(@PathVariable("id") Integer id, Model model) {
    model.addAttribute("task", tasksDao.findById(id));

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
    tasksDao.update(new Task(id, title, status, priority, dueDate, createdAt, LocalDateTime.now()));

    return "redirect:/todos";
  }

  @DeleteMapping("/{id}")
  public String deleteTask(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
    tasksDao.delete(id);

    redirectAttributes.addFlashAttribute("queryParameterMap",
        Map.of("filter", "未完了", "sort", "期限日"));

    return "redirect:/todos";
  }

  @DeleteMapping(path = "/{id}", params = {"filter", "sort"})
  public String deleteTask(@PathVariable("id") Integer id, @RequestParam("filter") String filter,
                           @RequestParam("sort") String sort,
                           RedirectAttributes redirectAttributes) {
    tasksDao.delete(id);

    redirectAttributes.addFlashAttribute("queryParameterMap",
        Map.of("filter", filter, "sort", sort));

    return "redirect:/todos";
  }
}
