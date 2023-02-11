package com.example.sakanobu.todoapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodosController {
  private final TodosDao todosDao;

  @Autowired
  public TodosController(TodosDao todosDao) {
    this.todosDao = todosDao;
  }

  @GetMapping("/todos")
  public String listTodos(Model model) {
    List<Todo> allTodos = todosDao.findAll();
    model.addAttribute("allTodos", allTodos);
    return "todos";
  }
}
