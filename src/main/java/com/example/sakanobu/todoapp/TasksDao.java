package com.example.sakanobu.todoapp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
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

    return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Task.class), id);
  }

  public List<Task> getFilteredSortedTasks(Map<String, String> queryParameterMap) {
    if (queryParameterMap == null) {
      return jdbcTemplate.query("""
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
            t.due_date;
          """, new BeanPropertyRowMapper<>(Task.class));
    }

    StringBuilder queryStringBuilder = new StringBuilder();

    queryStringBuilder.append("""
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
        """
    );

    switch (queryParameterMap.get("filter")) {
      case "未完了":
        queryStringBuilder.append("WHERE t.status = '未完了' ");
        break;
      case "未削除":
        queryStringBuilder.append("WHERE t.status != '削除済み' ");
        break;
      case "全て":
        break;
      default:
        throw new IllegalArgumentException("filter に不正な値が指定されています");
    }

    switch (queryParameterMap.get("sort")) {
      case "期限日":
        queryStringBuilder.append("ORDER BY t.due_date;");
        break;
      case "優先度":
        queryStringBuilder.append("ORDER BY t.priority DESC, t.due_date;");
        break;
      case "作成日":
        queryStringBuilder.append("ORDER BY t.created_at;");
        break;
      default:
        throw new IllegalArgumentException("sort に不正な値が指定されています");
    }

    return jdbcTemplate.query(queryStringBuilder.toString(),
        new BeanPropertyRowMapper<>(Task.class));
  }

  public void create(Task task) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(task);
    SimpleJdbcInsert insert =
        new SimpleJdbcInsert(jdbcTemplate).withTableName("tasks").usingGeneratedKeyColumns("id");

    insert.execute(param);
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
