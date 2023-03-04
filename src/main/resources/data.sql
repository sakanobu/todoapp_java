-- tasks table
DELETE FROM
  todoapp_java_db.tasks;

INSERT INTO
  todoapp_java_db.tasks (title, status)
VALUES
  ("散歩", "未完了"),
  ("買い物", "完了"),
  ("アニメ", "未完了"),
  ("買い物(アニメ)", "完了");
