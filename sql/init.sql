CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

INSERT INTO `seckill`(`seckill_id`, `name`, `number`, `start_time`, `end_time`, `create_time`, `version`) VALUES (1000, '1000元秒杀iphone8', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill`(`seckill_id`, `name`, `number`, `start_time`, `end_time`, `create_time`, `version`) VALUES (1001, '500元秒杀ipad2', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill`(`seckill_id`, `name`, `number`, `start_time`, `end_time`, `create_time`, `version`) VALUES (1002, '300元秒杀小米4', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill`(`seckill_id`, `name`, `number`, `start_time`, `end_time`, `create_time`, `version`) VALUES (1003, '200元秒杀红米note', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);

CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `state` tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
