create table user
(
    id      int auto_increment
        primary key,
    name    varchar(20) not null,
    gender  varchar(5)  null,
    age     int         null,
    address varchar(32) null,
    qq      varchar(20) null,
    email   varchar(50) null
);

INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (1, '张三', '男', 20, '广东', '11111111', '11111111@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (5, '王五', '女', 26, '湖南', '33333333', '33333333@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (6, '李四', '男', 25, '湖南', '22222222', '22222222@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (7, '李四', '女', 25, '湖南', '88888888', '88888888@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (8, '赵六', '女', 25, '湖南', '44444444', '44444444@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (9, '孙七', '男', 25, '湖南', '55555555', '55555555@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (10, '王八', '男', 25, '湖南', '66666666', '66666666@qq.com');
INSERT INTO Day15.user (id, name, gender, age, address, qq, email) VALUES (11, '刘九', '男', 25, '湖南', '77777777', '77777777@qq.com');