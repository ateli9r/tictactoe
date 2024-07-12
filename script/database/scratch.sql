CREATE TABLE t_user (
    user_id VARCHAR(50) PRIMARY KEY COMMENT '사용자 아이디',
    user_pw VARCHAR(300) NOT NULL COMMENT '패스워드',
    nickname VARCHAR(50) NOT NULL COMMENT '닉네임',
    email VARCHAR(100) NOT NULL COMMENT '이메일'
) COMMENT '사용자';


CREATE TABLE t_rank (
    user_id VARCHAR(50) COMMENT '사용자 아이디',
    total INT DEFAULT 0 COMMENT '대전수',
    wins INT DEFAULT 0 COMMENT '승리',
    losses INT DEFAULT 0 COMMENT '패배',
    draws INT DEFAULT 0 COMMENT '무승부',
    FOREIGN KEY (user_id) REFERENCES t_user(user_id)
) COMMENT '전적';


select SHA2('foo', 256)
;




-- t_user 테이블에 더미 데이터 삽입
INSERT INTO t_user (user_id, user_pw, nickname, email) VALUES 
('user1', SHA2('user1', 256), 'nickname1', 'user1@example.com'),
('user2', SHA2('user2', 256), 'nickname2', 'user2@example.com'),
('user3', SHA2('user3', 256), 'nickname3', 'user3@example.com'),
('user4', SHA2('user4', 256), 'nickname4', 'user4@example.com'),
('user5', SHA2('user5', 256), 'nickname5', 'user5@example.com'),
('user6', SHA2('user6', 256), 'nickname6', 'user6@example.com'),
('user7', SHA2('user7', 256), 'nickname7', 'user7@example.com'),
('user8', SHA2('user8', 256), 'nickname8', 'user8@example.com'),
('user9', SHA2('user9', 256), 'nickname9', 'user9@example.com'),
('user10', SHA2('user10', 256), 'nickname10', 'user10@example.com'),
('user11', SHA2('user11', 256), 'nickname11', 'user11@example.com'),
('user12', SHA2('user12', 256), 'nickname12', 'user12@example.com'),
('user13', SHA2('user13', 256), 'nickname13', 'user13@example.com'),
('user14', SHA2('user14', 256), 'nickname14', 'user14@example.com'),
('user15', SHA2('user15', 256), 'nickname15', 'user15@example.com'),
('user16', SHA2('user16', 256), 'nickname16', 'user16@example.com'),
('user17', SHA2('user17', 256), 'nickname17', 'user17@example.com'),
('user18', SHA2('user18', 256), 'nickname18', 'user18@example.com'),
('user19', SHA2('user19', 256), 'nickname19', 'user19@example.com'),
('user20', SHA2('user20', 256), 'nickname20', 'user20@example.com'),
('user21', SHA2('user21', 256), 'nickname21', 'user21@example.com'),
('user22', SHA2('user22', 256), 'nickname22', 'user22@example.com'),
('user23', SHA2('user23', 256), 'nickname23', 'user23@example.com'),
('user24', SHA2('user24', 256), 'nickname24', 'user24@example.com'),
('user25', SHA2('user25', 256), 'nickname25', 'user25@example.com'),
('user26', SHA2('user26', 256), 'nickname26', 'user26@example.com'),
('user27', SHA2('user27', 256), 'nickname27', 'user27@example.com'),
('user28', SHA2('user28', 256), 'nickname28', 'user28@example.com'),
('user29', SHA2('user29', 256), 'nickname29', 'user29@example.com'),
('user30', SHA2('user30', 256), 'nickname30', 'user30@example.com'),
('user31', SHA2('user31', 256), 'nickname31', 'user31@example.com'),
('user32', SHA2('user32', 256), 'nickname32', 'user32@example.com'),
('user33', SHA2('user33', 256), 'nickname33', 'user33@example.com'),
('user34', SHA2('user34', 256), 'nickname34', 'user34@example.com'),
('user35', SHA2('user35', 256), 'nickname35', 'user35@example.com'),
('user36', SHA2('user36', 256), 'nickname36', 'user36@example.com'),
('user37', SHA2('user37', 256), 'nickname37', 'user37@example.com'),
('user38', SHA2('user38', 256), 'nickname38', 'user38@example.com'),
('user39', SHA2('user39', 256), 'nickname39', 'user39@example.com'),
('user40', SHA2('user40', 256), 'nickname40', 'user40@example.com'),
('user41', SHA2('user41', 256), 'nickname41', 'user41@example.com'),
('user42', SHA2('user42', 256), 'nickname42', 'user42@example.com'),
('user43', SHA2('user43', 256), 'nickname43', 'user43@example.com'),
('user44', SHA2('user44', 256), 'nickname44', 'user44@example.com'),
('user45', SHA2('user45', 256), 'nickname45', 'user45@example.com'),
('user46', SHA2('user46', 256), 'nickname46', 'user46@example.com'),
('user47', SHA2('user47', 256), 'nickname47', 'user47@example.com'),
('user48', SHA2('user48', 256), 'nickname48', 'user48@example.com'),
('user49', SHA2('user49', 256), 'nickname49', 'user49@example.com'),
('user50', SHA2('user50', 256), 'nickname50', 'user50@example.com'),
('user51', SHA2('user51', 256), 'nickname51', 'user51@example.com'),
('user52', SHA2('user52', 256), 'nickname52', 'user52@example.com'),
('user53', SHA2('user53', 256), 'nickname53', 'user53@example.com'),
('user54', SHA2('user54', 256), 'nickname54', 'user54@example.com'),
('user55', SHA2('user55', 256), 'nickname55', 'user55@example.com'),
('user56', SHA2('user56', 256), 'nickname56', 'user56@example.com'),
('user57', SHA2('user57', 256), 'nickname57', 'user57@example.com'),
('user58', SHA2('user58', 256), 'nickname58', 'user58@example.com'),
('user59', SHA2('user59', 256), 'nickname59', 'user59@example.com'),
('user60', SHA2('user60', 256), 'nickname60', 'user60@example.com'),
('user61', SHA2('user61', 256), 'nickname61', 'user61@example.com'),
('user62', SHA2('user62', 256), 'nickname62', 'user62@example.com'),
('user63', SHA2('user63', 256), 'nickname63', 'user63@example.com'),
('user64', SHA2('user64', 256), 'nickname64', 'user64@example.com'),
('user65', SHA2('user65', 256), 'nickname65', 'user65@example.com'),
('user66', SHA2('user66', 256), 'nickname66', 'user66@example.com'),
('user67', SHA2('user67', 256), 'nickname67', 'user67@example.com'),
('user68', SHA2('user68', 256), 'nickname68', 'user68@example.com'),
('user69', SHA2('user69', 256), 'nickname69', 'user69@example.com'),
('user70', SHA2('user70', 256), 'nickname70', 'user70@example.com'),
('user71', SHA2('user71', 256), 'nickname71', 'user71@example.com'),
('user72', SHA2('user72', 256), 'nickname72', 'user72@example.com'),
('user73', SHA2('user73', 256), 'nickname73', 'user73@example.com'),
('user74', SHA2('user74', 256), 'nickname74', 'user74@example.com'),
('user75', SHA2('user75', 256), 'nickname75', 'user75@example.com'),
('user76', SHA2('user76', 256), 'nickname76', 'user76@example.com'),
('user77', SHA2('user77', 256), 'nickname77', 'user77@example.com'),
('user78', SHA2('user78', 256), 'nickname78', 'user78@example.com'),
('user79', SHA2('user79', 256), 'nickname79', 'user79@example.com'),
('user80', SHA2('user80', 256), 'nickname80', 'user80@example.com'),
('user81', SHA2('user81', 256), 'nickname81', 'user81@example.com'),
('user82', SHA2('user82', 256), 'nickname82', 'user82@example.com'),
('user83', SHA2('user83', 256), 'nickname83', 'user83@example.com'),
('user84', SHA2('user84', 256), 'nickname84', 'user84@example.com'),
('user85', SHA2('user85', 256), 'nickname85', 'user85@example.com'),
('user86', SHA2('user86', 256), 'nickname86', 'user86@example.com'),
('user87', SHA2('user87', 256), 'nickname87', 'user87@example.com'),
('user88', SHA2('user88', 256), 'nickname88', 'user88@example.com'),
('user89', SHA2('user89', 256), 'nickname89', 'user89@example.com'),
('user90', SHA2('user90', 256), 'nickname90', 'user90@example.com'),
('user91', SHA2('user91', 256), 'nickname91', 'user91@example.com'),
('user92', SHA2('user92', 256), 'nickname92', 'user92@example.com'),
('user93', SHA2('user93', 256), 'nickname93', 'user93@example.com'),
('user94', SHA2('user94', 256), 'nickname94', 'user94@example.com'),
('user95', SHA2('user95', 256), 'nickname95', 'user95@example.com'),
('user96', SHA2('user96', 256), 'nickname96', 'user96@example.com'),
('user97', SHA2('user97', 256), 'nickname97', 'user97@example.com'),
('user98', SHA2('user98', 256), 'nickname98', 'user98@example.com'),
('user99', SHA2('user99', 256), 'nickname99', 'user99@example.com'),
('user100', SHA2('user100', 256), 'nickname100', 'user100@example.com');

-- t_rank 테이블에 더미 데이터 삽입
INSERT INTO t_rank (user_id, total, wins, losses, draws) VALUES 
('user1', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user2', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user3', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user4', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user5', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user6', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user7', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user8', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user9', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user10', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user11', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user12', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user13', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user14', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user15', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user16', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user17', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user18', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user19', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user20', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user21', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user22', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user23', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user24', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user25', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user26', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user27', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user28', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user29', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user30', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user31', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user32', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user33', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user34', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user35', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user36', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user37', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user38', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user39', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user40', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user41', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user42', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user43', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user44', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user45', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user46', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user47', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user48', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user49', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user50', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user51', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user52', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user53', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user54', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user55', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user56', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user57', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user58', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user59', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user60', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user61', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user62', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user63', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user64', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user65', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user66', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user67', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user68', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user69', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user70', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user71', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user72', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user73', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user74', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user75', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user76', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user77', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user78', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user79', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user80', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user81', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user82', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user83', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user84', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user85', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user86', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user87', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user88', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user89', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user90', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user91', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user92', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user93', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user94', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user95', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user96', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user97', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user98', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user99', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20)),
('user100', FLOOR(RAND() * 100), FLOOR(RAND() * 50), FLOOR(RAND() * 30), FLOOR(RAND() * 20));


SELECT COUNT(*) = 1 FROM `t_user` WHERE `user_id` = 'asdf' AND `user_pw` = 'f0e4c2f76c58916ec258f246851bea091d14d4247a2fc3e18694461b1816e13b'


select * from t_user;
-- Parameters: asdf(String), f0e4c2f76c58916ec258f246851bea091d14d4247a2fc3e18694461b1816e13b(String)

select * from t_rank;

update t_rank
set
	`total` = `wins` + `losses` + `draws`
where 1=1;



SELECT u.user_id AS userId, u.nickname, r.total, r.wins, r.losses, r.draws
FROM t_user u
INNER JOIN t_rank r ON u.user_id = r.user_id
WHERE u.user_id = 'user1'
;


SELECT COUNT(*) = 1
FROM `t_user`
WHERE `user_id` = 'user1'
AND `user_pw` = SHA2('user2', 256)



SELECT u.`user_id` AS userId, u.`nickname`,
	r.`total`, r.`wins`, r.`losses`, r.`draws`
FROM `t_user` u
INNER JOIN `t_rank` r ON u.`user_id` = r.`user_id`
WHERE u.`user_id` = 'user1'




SELECT u.`user_id` AS userId, u.`nickname`, r.`total`, r.`wins`, r.`losses`, r.`draws` FROM `t_user` u INNER JOIN `t_rank` r ON u.`user_id` = r.`user_id` WHERE u.`user_id` = ${userId}



SELECT u.`user_id` AS userId, u.`nickname`, r.`total`, r.`wins`, r.`losses`, r.`draws` FROM `t_user` u INNER JOIN `t_rank` r ON u.`user_id` = r.`user_id` WHERE u.`user_id` = ?



SELECT u.`user_id` userId,
	u.`nickname`,
    ifnull(r.`total`, 0) total,
    ifnull(r.`wins`, 0) wins,
    ifnull(r.`losses`, 0) losses,
    ifnull(r.`draws`, 0) draws
FROM `t_user` u
LEFT JOIN `t_rank` r ON u.`user_id` = r.`user_id`
WHERE u.`user_id` = ${userId}
;
        