CREATE TABLE IF NOT EXISTS `user` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `name` varchar(10) DEFAULT NULL COMMENT '用户名',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

insert into user values(1, '张三'), (2, '李四'), (3, '王五');



CREATE TABLE IF NOT EXISTS `card_association` (
                                                  `card_id` bigint NOT NULL comment '卡id' primary key ,
                                                  `user_id` bigint NOT NULL unique COMMENT '用户id',
                                                  `name` varchar(10) DEFAULT NULL COMMENT '卡名'
    ) ENGINE=InnoDB ;

insert into card_association values(10, 1, '张三的卡'), (11, 2, '李四的卡'), (12, 3, '王五的卡');



CREATE TABLE IF NOT EXISTS `card_collection` (
                                                 `card_id` bigint NOT NULL comment '卡id' primary key ,
                                                 `user_id` bigint NOT NULL COMMENT '用户id',
                                                 `name` varchar(10) DEFAULT NULL COMMENT '卡名'
    ) ENGINE=InnoDB ;

insert into card_collection values(20, 1, '张三的卡1'), (21, 1, '张三的卡2'), (22, 2, '李四的卡'), (23, 3, '王五的卡');



CREATE TABLE IF NOT EXISTS `card_discriminator` (
  `card_id` bigint NOT NULL comment '卡id' primary key ,
  `name` varchar(10) DEFAULT NULL COMMENT '卡名',
  `type` int(1) DEFAULT 1 COMMENT '卡类型'
) ENGINE=InnoDB ;

insert into card_discriminator values(30, '卡1', 0), (31, '卡2', 1);