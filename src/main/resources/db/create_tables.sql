create DATABASE todo_db;

use todo_db;

-- 사용자 테이블
CREATE TABLE User
(
    userId   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(50)  NOT NULL,
    password VARCHAR(30)  NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);

-- 일정 테이블
CREATE TABLE Task
(
    taskId      BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId      BIGINT       NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    createdAt   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE
);