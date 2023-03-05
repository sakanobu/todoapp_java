-- tasks table
DELETE
FROM
    todoapp_java_db.tasks;

INSERT INTO
    todoapp_java_db.tasks (title, status, priority)
VALUES
    ('買い物', '未完了', '低'),
    ('散歩', '完了', '中'),
    ('アニメ', '削除済み', '高'),
    ('プログラミング', '未完了', '高'),
    ('買い物(切手)', '完了', '低'),
    ('昼寝', '削除済み', '中'),
    ('温泉', '未完了', '中'),
    ('散髪', '完了', '高'),
    ('買い物(靴)', '削除済み', '低');
