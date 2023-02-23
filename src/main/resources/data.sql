-- tasks table
DELETE FROM
  todoapp_java_db.tasks;

INSERT INTO
  todoapp_java_db.tasks (id, title, status)
VALUES
  ("c4c7ab8e-22c4-5c26-0639-f8351dcab63e", "散歩", "FINISHED"),
  ("a95d6e5f-2030-93a2-e182-a4a9858c7153", "買い物", "UNFINISHED"),
  ("6cc6fc4a-2f4a-5ba3-280d-b5111de4a177", "アニメ", "FINISHED"),
  ("ba7a013e-02e1-2dd9-7111-c499cc04d844", "買い物(アニメ)", "UNFINISHED");
