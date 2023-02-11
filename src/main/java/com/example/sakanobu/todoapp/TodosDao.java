package com.example.sakanobu.todoapp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TodosDao {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public TodosDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Todo> findAll() {
    String query = """
        SELECT
          t.id,
          t.name
        FROM
          todos AS t
        """;

    List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

    return result.stream()
        .map((Map<String, Object> row) -> new Todo(Integer.parseInt(row.get("id").toString()),
            row.get("name").toString()))
        .toList();
  }
}
