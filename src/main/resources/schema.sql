-- todos table
DROP TABLE IF EXISTS todoapp_java_db.todos;

CREATE TABLE IF NOT EXISTS todoapp_java_db.todos (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR (256) NOT NULL,
  PRIMARY KEY (id)
);
