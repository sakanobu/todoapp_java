-- tasks table
DELETE FROM
  todoapp_java_db.tasks;

INSERT INTO
  todoapp_java_db.tasks (title, status)
VALUES
  ("散歩", "FINISHED"),
  ("買い物", "UNFINISHED"),
  ("アニメ", "FINISHED"),
  ("買い物(アニメ)", "UNFINISHED");
