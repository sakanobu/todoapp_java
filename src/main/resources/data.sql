-- tasks table
DELETE FROM
  todoapp_java_db.tasks;

INSERT INTO
  todoapp_java_db.tasks (title, status)
VALUES
  ("散歩", "未完了"),
  ("買い物", "完了"),
  ("アニメ", "削除済み"),
  ("買い物(切手)", "未完了"),
  ("プログラミング", "完了"),
  ("昼寝", "削除済み");
