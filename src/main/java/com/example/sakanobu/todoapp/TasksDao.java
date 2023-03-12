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
    String filter;
    String sort;

    if (queryParameterMap == null) {
      return findByStatusUnfinishedOrderByDueDateAsc();
    } else {
      filter = queryParameterMap.get("filter");
      sort = queryParameterMap.get("sort");
    }

    if (filter.equals("未完了") && sort.equals("期限日")) {
      return findByStatusUnfinishedOrderByDueDateAsc();
    }

    if (filter.equals("未完了") && sort.equals("優先度")) {
      return findByStatusUnfinishedOrderByPriorityDescAndDueDateAsc();
    }

    if (filter.equals("未完了") && sort.equals("作成日")) {
      return findByStatusUnfinishedOrderByCreatedAtAsc();
    }

    if (filter.equals("未削除") && sort.equals("期限日")) {
      return findByStatusUndeletedOrderByDueDateAsc();
    }

    if (filter.equals("未削除") && sort.equals("優先度")) {
      return findByStatusUndeletedOrderByPriorityDescAndDueDateAsc();
    }

    if (filter.equals("未削除") && sort.equals("作成日")) {
      return findByStatusUndeletedOrderByCreatedAtAsc();
    }

    if (filter.equals("全て") && sort.equals("期限日")) {
      return findAllOrderByDueDateAsc();
    }

    if (filter.equals("全て") && sort.equals("優先度")) {
      return findAllOrderByPriorityDescAndDueDateAsc();
    }

    if (filter.equals("全て") && sort.equals("作成日")) {
      return findAllOrderByCreatedAtAsc();
    }

    throw new IllegalArgumentException("filter と sort の組み合わせが正しくありません");
  }

  private List<Task> findByStatusUnfinishedOrderByDueDateAsc() {
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
          t.due_date;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findByStatusUnfinishedOrderByPriorityDescAndDueDateAsc() {
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
          t.priority DESC, t.due_date;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findByStatusUnfinishedOrderByCreatedAtAsc() {
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
          t.created_at;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findByStatusUndeletedOrderByDueDateAsc() {
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
          t.status != '削除済み'
        ORDER BY
          t.due_date;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findByStatusUndeletedOrderByPriorityDescAndDueDateAsc() {
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
          t.status != '削除済み'
        ORDER BY
          t.priority DESC, t.due_date;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findByStatusUndeletedOrderByCreatedAtAsc() {
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
          t.status != '削除済み'
        ORDER BY
          t.created_at;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findAllOrderByDueDateAsc() {
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
        ORDER BY
          t.due_date;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findAllOrderByPriorityDescAndDueDateAsc() {
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
        ORDER BY
          t.priority DESC, t.due_date;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
  }

  private List<Task> findAllOrderByCreatedAtAsc() {
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
        ORDER BY
          t.created_at;
        """;

    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Task.class));
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
