package com.example.sakanobu.todoapp;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;

public class Task {
  private Integer id;
  @Size(min = 1, max = 9, message = "ToDo名は1文字以上9文字以内で入力してください。")
  private String title;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Task(Integer id, String title, String status, LocalDateTime createdAt,
              LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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
}
