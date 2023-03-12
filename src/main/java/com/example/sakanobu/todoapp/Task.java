package com.example.sakanobu.todoapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public class Task {
  private Integer id;
  @Size(min = 1, max = 9, message = "ToDo名は1文字以上9文字以内で入力してください。")
  private String title;
  private String status;
  private String priority;
  // この@DateTimeFormatによって todos/{id}/edit の際に input要素のtype=dateかつth:field="*{dueDate}"の値がフォームにセットされる
  // ただし、PUT の際の Controller の @RequestParam で String から LocalDate に自動変換する際は @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) が必要なのは注意
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Task() {
  }

  public Task(Integer id, String title, String status, String priority, LocalDate dueDate,
              LocalDateTime createdAt,
              LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  /**
   * ToDoのStatus、本日の日付、期限日などの関係からToDoの締切に関する状態をを表す文字列を返す.
   *
   * @return tr要素のclass属性に設定する文字列
   */
  public String getDeadlineStatus() {
    if (!getStatus().equals("未完了")) {
      return "";
    }

    if (isDueDateOver()) {
      return "todo-danger";
    } else if (isDueDateWithinOneDay()) {
      return "todo-warning";
    } else {
      return "";
    }
  }

  /**
   * 本日の日付が期限日を超えているかを判定する.
   *
   * @return 本日の日付が期限日を超えていれば true を返す
   */
  public boolean isDueDateOver() {
    return getDueDate().isBefore(LocalDate.now());
  }

  /**
   * 本日の日付が期限日まで残り1日以内かどうかを判定する.
   *
   * @return 本日の日付が期限日と一致する、もしくは、期限日の1日前と一致するのであれば true を返す
   */
  public boolean isDueDateWithinOneDay() {
    return getDueDate().isEqual(LocalDate.now())
        || getDueDate().isEqual(LocalDate.now().plusDays(1));
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "id: %d, title: %s, status: %s, priority: %s, dueDate: %s, createdAt: %s, updatedAt: %s"
        .formatted(id, title, status, priority, dueDate, createdAt, updatedAt);
  }
}
