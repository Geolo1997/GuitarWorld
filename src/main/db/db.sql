
-- GuitarWorld_Server


-- 创建数据库
CREATE DATABASE GuitarWorld_Server;

-- 创建表

-- 用户表
CREATE TABLE User (
    -- 用户名
    username VARCHAR(20) NOT NULL PRIMARY KEY,
    -- 密码
    password VARCHAR(20) NOT NULL,
    -- 电子邮箱
    email VARCHAR(20) NOT NULL,
    -- 头像地址
    avatar_path VARCHAR(100)
);

-- 用户关系表
CREATE TABLE UserRelation (
    -- 关注者
    follower_username VARCHAR(20) NOT NULL REFERENCES User(username),
    -- 被关注者
    following_username VARCHAR(20) NOT NULL REFERENCES User(username),
    -- 用户关系类型
    relation_type VARCHAR(20) NOT NULL,
    -- 联合主键
    PRIMARY KEY(follower_username, following_username)
);

-- 作品表
CREATE TABLE Works (
    -- 无符号自增主键
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    -- 作者用户名
    author VARCHAR(20) NOT NULL REFERENCES User(username),
    -- 创作时间
    create_time DATETIME,
    -- 标题
    title VARCHAR(30),
    -- 内容
    content VARCHAR(300)
);

-- 评论表
CREATE TABLE Comment (
    -- 无符号自增主键
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    -- 所评论的作品id
    works_id INT UNSIGNED NOT NULL REFERENCES Works(id),
    -- 作者用户名
    author VARCHAR(20) NOT NULL REFERENCES User(username),
    -- 创作时间
    create_time DATETIME,
    -- 内容
    content VARCHAR(100)
);

-- 图片表
CREATE TABLE Image (
    -- 无符号自增主键
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    works_id INT UNSIGNED NOT NULL REFERENCES Works(id),
    path VARCHAR(100) NOT NULL
);

-- 乐曲
CREATE TABLE music (

    -- 无符号自增主键
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT ,

);