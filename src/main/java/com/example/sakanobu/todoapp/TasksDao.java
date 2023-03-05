package com.example.sakanobu.todoapp;

import java.sql.Date;
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

  public Task findById(Integer id) {
    String query = """
        SELECT
          t.id,
          t.title,
          t.status,
          t.priority,
          t.due_date,
          t.created_at,
          t.updated_at
        FROM
          tasks AS t
        WHERE
          t.id = ?;
        """;

    Map<String, Object> targetTask = jdbcTemplate.queryForMap(query, id);

    return new Task(Integer.valueOf(targetTask.get("id").toString()),
        targetTask.get("title").toString(), targetTask.get("status").toString(),
        targetTask.get("priority").toString(),
        Date.valueOf(targetTask.get("due_date").toString()).toLocalDate(),
        Timestamp.valueOf(targetTask.get("created_at").toString()).toLocalDateTime(),
        Timestamp.valueOf(targetTask.get("updated_at").toString()).toLocalDateTime());
  }

  public List<Task> findAll() {
    String query = """
        SELECT
          t.id,
          t.title,
          t.status,
          t.priority,
          t.due_date,
          t.created_at,
          t.updated_at
        FROM
          tasks AS t;
        """;

    List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

    return result.stream()
        .map((Map<String, Object> row) -> new Task(Integer.valueOf(row.get("id").toString()),
            row.get("title").toString(), row.get("status").toString(),
            row.get("priority").toString(),
            Date.valueOf(row.get("due_date").toString()).toLocalDate(),
            Timestamp.valueOf(row.get("created_at").toString()).toLocalDateTime(),
            Timestamp.valueOf(row.get("updated_at").toString()).toLocalDateTime()))
        .toList();
  }

  public List<Task> findByStatusUnfinishedOrderByDueDateAsc() {
    String query = """
        SELECT
          t.id,
          t.title,
          t.status,
          t.priority,
          t.due_date,
          t.created_at,
          t.updated_at
        FROM
          tasks AS t
        WHERE
          t.status = '未完了'
        ORDER BY
          t.due_date ASC;
        """;

    // rowMapper でのメソッド参照でも書けるのかな？
    return jdbcTemplate.query(query, (rs, rowNum) -> new Task(
        rs.getInt("id"),
        rs.getString("title"),
        rs.getString("status"),
        rs.getString("priority"),
        rs.getDate("due_date").toLocalDate(),
        rs.getTimestamp("created_at").toLocalDateTime(),
        rs.getTimestamp("updated_at").toLocalDateTime()
    ));

    //    List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
    //
    //    return result.stream()
    //        .map((Map<String, Object> row) -> new Task(Integer.valueOf(row.get("id").toString()),
    //            row.get("title").toString(), row.get("status").toString(),
    //            row.get("priority").toString(),
    //            Date.valueOf(row.get("due_date").toString()).toLocalDate(),
    //            Timestamp.valueOf(row.get("created_at").toString()).toLocalDateTime(),
    //            Timestamp.valueOf(row.get("updated_at").toString()).toLocalDateTime()))
    //        .toList();
  }


  public void create(Task task) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(task);
    SimpleJdbcInsert insert =
        new SimpleJdbcInsert(jdbcTemplate).withTableName("tasks").usingGeneratedKeyColumns("id");

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
          priority = ?,
          due_date = ?,
          updated_at = ?
        WHERE
          id = ?;
        """;

    jdbcTemplate.update(query, task.getTitle(), task.getStatus(), task.getPriority(),
        task.getDueDate(), task.getUpdatedAt(), task.getId());
  }

  public void delete(Integer id) {
    String query = """
        UPDATE
          tasks
        SET
          status = '削除済み'
        WHERE
          id = ?;
        """;

    jdbcTemplate.update(query, id);
  }
}
