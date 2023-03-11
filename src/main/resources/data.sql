-- tasks table
DELETE
FROM
    todoapp_java_db.tasks;

INSERT INTO
    todoapp_java_db.tasks (title, status, priority, due_date, created_at)
VALUES
    ('買い物', '未完了', '低', ADDDATE(CURDATE(), INTERVAL 10 DAY), ADDDATE(NOW(), INTERVAL 9 DAY)),
    ('散歩', '完了', '中', ADDDATE(CURDATE(), INTERVAL 20 DAY), ADDDATE(NOW(), INTERVAL 8 DAY)),
    ('アニメ', '削除済み', '高', ADDDATE(CURDATE(), INTERVAL 30 DAY), ADDDATE(NOW(), INTERVAL 7 DAY)),
    ('プログラミング', '未完了', '高', ADDDATE(CURDATE(), INTERVAL 40 DAY), ADDDATE(NOW(), INTERVAL 6 DAY)),
    ('買い物(切手)', '完了', '低', ADDDATE(CURDATE(), INTERVAL 50 DAY), ADDDATE(NOW(), INTERVAL 5 DAY)),
    ('昼寝', '削除済み', '中', ADDDATE(CURDATE(), INTERVAL 60 DAY), ADDDATE(NOW(), INTERVAL 4 DAY)),
    ('温泉', '未完了', '中', ADDDATE(CURDATE(), INTERVAL 70 DAY), ADDDATE(NOW(), INTERVAL 3 DAY)),
    ('散髪', '完了', '高', ADDDATE(CURDATE(), INTERVAL 80 DAY), ADDDATE(NOW(), INTERVAL 2 DAY)),
    ('買い物(靴)', '削除済み', '低', ADDDATE(CURDATE(), INTERVAL 90 DAY), ADDDATE(NOW(), INTERVAL 1 DAY)),
    ('期限日2日前', '未完了', '中', ADDDATE(CURDATE(), INTERVAL 2 DAY), NOW()),
    ('期限日1日前', '未完了', '中', ADDDATE(CURDATE(), INTERVAL 1 DAY), NOW()),
    ('期限日超過', '未完了', '中', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
    ('期限日より作成日が後', '未完了', '中', ADDDATE(CURDATE(), INTERVAL 1 DAY), ADDDATE(NOW(), INTERVAL 10 DAY));
