create table admin
(
    id        int auto_increment
        primary key,
    adminname varchar(20) null,
    password  varchar(20) null
);

INSERT INTO Day15.admin (id, adminname, password) VALUES (1, '张三', '123456');