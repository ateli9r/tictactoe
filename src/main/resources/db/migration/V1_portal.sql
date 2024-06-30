-- DROP TABLE `tk_admin_user`;

CREATE TABLE `tk_admin_user` (
	`user_id` VARCHAR(30) NOT NULL COMMENT '아이디',
	`user_pw` VARCHAR(300) NOT NULL COMMENT '패스워드',
	`email` VARCHAR(100) NOT NULL COMMENT '이메일',
	PRIMARY KEY (`user_id`)
) COMMENT '관리자 회원';

