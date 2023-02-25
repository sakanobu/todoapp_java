package com.example.sakanobu.todoapp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
public class TasksDao {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public TasksDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Task> findAll() {
    String query = """
        SELECT
          t.id,
          t.title,
          t.status,
          t.created_at,
          t.updated_at
        FROM
          tasks AS t
        """;

    List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

    return result.stream()
        .map((Map<String, Object> row) -> new Task(row.get("id").toString(),
            row.get("title").toString(), row.get("status").toString(),
            Timestamp.valueOf(row.get("created_at").toString()),
            Timestamp.valueOf(row.get("updated_at").toString())))
        .toList();
  }

  public Task findById(String id) {
    String query = """
        SELECT
          t.id,
          t.title,
          t.status,
          t.created_at,
          t.updated_at
        FROM
          tasks AS t
        WHERE
          t.id = ?
        """;

    Map<String, Object> targetTask = jdbcTemplate.queryForMap(query, id);

    return new Task(targetTask.get("id").toString(),
        targetTask.get("title").toString(), targetTask.get("status").toString(),
        Timestamp.valueOf(targetTask.get("created_at").toString()),
        Timestamp.valueOf(targetTask.get("updated_at").toString()));
  }

  public void create(Task task) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(task);
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tasks");

    insert.execute(param);
  }

  public void createByString(String title) {
    String query = """
        INSERT INTO
          tasks (title)
        VALUES
          (?);
        """;

    jdbcTemplate.update(query, title);
  }

  public void update(Task task) {
    String query = """
        UPDATE
          tasks
        SET
          title = ?,
          status = ?,
          updated_at = ?
        WHERE
          id = ?
        """;

    jdbcTemplate.update(query, task.getTitle(), task.getStatus(), task.getUpdatedAt(),
        task.getId());
  }

  public void delete(String id) {
    String query = """
        DELETE FROM
          tasks
        WHERE
          id = ?
        """;

    jdbcTemplate.update(query, id);
  }
}
