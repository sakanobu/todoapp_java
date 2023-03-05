-- tasks table
DROP TABLE IF EXISTS todoapp_java_db.tasks;

CREATE TABLE IF NOT EXISTS todoapp_java_db.tasks
(
    id         INT                                NOT NULL AUTO_INCREMENT,
    title      VARCHAR(256)                       NOT NULL,
    status     ENUM ('未完了', '完了', '削除済み') NOT NULL DEFAULT '未完了',
    priority   ENUM ('低', '中', '高')            NOT NULL DEFAULT '中',
    due_date   DATE                               NOT NULL DEFAULT (CURRENT_DATE),
    created_at TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
