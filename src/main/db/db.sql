
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
    name VARCHAR(50) NOT NULL,
    author VARCHAR(20) NOT NULL,
    profile VARCHAR(200)
);

-- 乐谱
CREATE TABLE MusicScore (
    -- 无符号自增主键
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    -- 乐曲id
    music_id INT UNSIGNED,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(20) NOT NULL
);

-- 乐谱图片
CREATE TABLE MusicScoreImage (
    -- 无符号自增主键
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    -- 乐谱id
    music_score_id INT UNSIGNED,
    -- 存储路径
    path VARCHAR(100) NOT NULL
);


-- 测试数据
INSERT INTO music(name, author, profile) VALUES('七里香', '周杰伦', '秋刀鱼的滋味，猫和鱼都想了解');
INSERT INTO music(name, author, profile) VALUES('东风破', '周杰伦', '谁在用琵琶弹奏一曲东风破');
INSERT INTO music(name, author, profile) VALUES('背对背拥抱', '林俊杰', '话总说不清楚，该怎么明了，一字一句像圈套');
INSERT INTO music(name, author, profile) VALUES('Let\'s not fall in love', 'BIGBANG', '그녀가 떠나가요, 나는 아무거도');
INSERT INTO music(name, author, profile) VALUES('Boy with Luv', 'BTS', 'oh my my my , oh my my my ');


INSERT INTO MusicScore(music_id, name, author) VALUES (2, '东风破（双吉他伴奏版）', 'Geolo');
INSERT INTO MusicScore(music_id, name, author) VALUES (2, '东风破指弹吉他谱', 'Xiaoming');

INSERT INTO MusicScoreImage(music_score_id, path) VALUES (1, 'http://192.168.1.107:8080/GuitarWorld/avatar?username=Geolo');
INSERT INTO MusicScoreImage(music_score_id, path) VALUES (1, 'http://192.168.1.107:8080/GuitarWorld/avatar?username=Geolo');
INSERT INTO MusicScoreImage(music_score_id, path) VALUES (1, 'http://192.168.1.107:8080/GuitarWorld/avatar?username=Geolo');

INSERT INTO MusicScoreImage(music_score_id, path) VALUES (2, 'http://192.168.1.107:8080/GuitarWorld/avatar?username=Xiaoming');
INSERT INTO MusicScoreImage(music_score_id, path) VALUES (2, 'http://192.168.1.107:8080/GuitarWorld/avatar?username=Xiaoming');
INSERT INTO MusicScoreImage(music_score_id, path) VALUES (2, 'http://192.168.1.107:8080/GuitarWorld/avatar?username=Xiaoming');

CREATE TABLE video_works (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    -- 作者用户名
    author VARCHAR(20) NOT NULL REFERENCES User(username),
    -- 创作时间
    create_time DATETIME,
    -- 标题
    title VARCHAR(30),
    video_url VARCHAR(100) NOT NULL
);

ALTER TABLE works add video_url VARCHAR(100), add type VARCHAR(10);

UPDATE works
SET type = 'IMAGE_TEXT';