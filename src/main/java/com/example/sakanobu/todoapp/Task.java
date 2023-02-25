package com.example.sakanobu.todoapp;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Task {
  private String id;
  @NotBlank(message = "ToDo名を入力してください。")
  @Size(min = 1, max = 20, message = "ToDo名は1文字以上20文字以内で入力してください。")
  private String title;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Task(String id, String title, String status, LocalDateTime createdAt,
              LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
