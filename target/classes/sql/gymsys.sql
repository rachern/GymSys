/*
 Navicat Premium Data Transfer

 Source Server         : ROOT
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : gymsys

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 28/06/2019 13:00:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area_book
-- ----------------------------
DROP TABLE IF EXISTS `area_book`;
CREATE TABLE `area_book`  (
  `book_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '预约信息id',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '场地id',
  `book_start_time` datetime(0) NULL DEFAULT NULL COMMENT '预约开始时间',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '预约学生id',
  `book_end_time` datetime(0) NULL DEFAULT NULL COMMENT '预约结束时间',
  PRIMARY KEY (`book_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area_info
-- ----------------------------
DROP TABLE IF EXISTS `area_info`;
CREATE TABLE `area_info`  (
  `area_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '场地id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场地名字',
  `teamed` int(1) NOT NULL DEFAULT 0 COMMENT '是不是校队预留',
  `classed` int(1) NOT NULL DEFAULT 0 COMMENT '是不是上课使用场地',
  `money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费标准',
  `area_type_id` int(11) NULL DEFAULT NULL COMMENT '场地类型id',
  PRIMARY KEY (`area_id`) USING BTREE,
  INDEX `area_type_id`(`area_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area_notice
-- ----------------------------
DROP TABLE IF EXISTS `area_notice`;
CREATE TABLE `area_notice`  (
  `area_notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '场地公告id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告标题',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告信息',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '工作人员id',
  PRIMARY KEY (`area_notice_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area_type
-- ----------------------------
DROP TABLE IF EXISTS `area_type`;
CREATE TABLE `area_type`  (
  `area_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '场地类型id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场地类型名字',
  PRIMARY KEY (`area_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area_use
-- ----------------------------
DROP TABLE IF EXISTS `area_use`;
CREATE TABLE `area_use`  (
  `area_use_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '场地使用id',
  `area_book_id` int(11) NULL DEFAULT NULL COMMENT '场地预约信息id',
  `use_start_time` datetime(0) NULL DEFAULT NULL COMMENT '场地使用开始时间',
  `use_end_time` datetime(0) NULL DEFAULT NULL COMMENT '场地使用结束时间',
  `money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '共花费多少钱',
  PRIMARY KEY (`area_use_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `auth_persistent_logins`;
CREATE TABLE `auth_persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_power
-- ----------------------------
DROP TABLE IF EXISTS `auth_power`;
CREATE TABLE `auth_power`  (
  `power_id` bigint(25) NOT NULL AUTO_INCREMENT,
  `power_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`power_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_power
-- ----------------------------
INSERT INTO `auth_power` VALUES (1, 'ui', '/swagger-ui.html/**', '2018-12-19 17:09:29', '2018-12-19 17:12:28');
INSERT INTO `auth_power` VALUES (2, 'resources', '/swagger-resources/**', '2018-12-19 17:11:42', '2018-12-19 17:12:32');
INSERT INTO `auth_power` VALUES (3, 'webjars', '/webjars/**', '2018-12-19 17:12:02', '2018-12-19 17:12:02');
INSERT INTO `auth_power` VALUES (4, 'vi', '/v2/**', '2018-12-19 17:12:25', '2018-12-19 17:12:25');
INSERT INTO `auth_power` VALUES (5, 'superadmin', '/**', '2018-12-19 17:15:24', '2018-12-19 17:15:24');

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `role_id` bigint(25) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (1, '管理员');
INSERT INTO `auth_role` VALUES (2, '超级管理员');
INSERT INTO `auth_role` VALUES (3, '用户');
INSERT INTO `auth_role` VALUES (4, '器材管理员');
INSERT INTO `auth_role` VALUES (5, '场地管理员');
INSERT INTO `auth_role` VALUES (6, '赛事管理员');

-- ----------------------------
-- Table structure for auth_role_power_table
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_power_table`;
CREATE TABLE `auth_role_power_table`  (
  `role_id` bigint(25) NOT NULL,
  `power_id` bigint(25) NOT NULL,
  PRIMARY KEY (`role_id`, `power_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role_power_table
-- ----------------------------
INSERT INTO `auth_role_power_table` VALUES (1, 1);
INSERT INTO `auth_role_power_table` VALUES (1, 2);
INSERT INTO `auth_role_power_table` VALUES (1, 3);
INSERT INTO `auth_role_power_table` VALUES (1, 4);
INSERT INTO `auth_role_power_table` VALUES (2, 5);
INSERT INTO `auth_role_power_table` VALUES (3, 5);
INSERT INTO `auth_role_power_table` VALUES (4, 5);
INSERT INTO `auth_role_power_table` VALUES (5, 5);
INSERT INTO `auth_role_power_table` VALUES (6, 5);

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`  (
  `user_id` bigint(25) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (1, 'admin', '$2a$10$CdRbMGIai6DDMlFPt7ZsMOH7M4uP1eE3dIS4EUruAzkQDNs/uLW9K', '1651561568@qq.com');
INSERT INTO `auth_user` VALUES (2, 'superAdmin', '$2a$10$CdRbMGIai6DDMlFPt7ZsMOH7M4uP1eE3dIS4EUruAzkQDNs/uLW9K', '1651561568@qq.com');
INSERT INTO `auth_user` VALUES (4, 'user', '$2a$10$orrEDDIMTpXlL8S6jllmZO3q9E3sYA9XIlL3M/hgIWipyBvJls6G6', '1651561568@qq.com');
INSERT INTO `auth_user` VALUES (5, '1', '$2a$10$owG2iWEhPoxeDXvT2PVMU.zyNVDDNM2RQVzYonXu7YDaij5rc7cHO', '1651561568@qq.com');
INSERT INTO `auth_user` VALUES (6, '2', '$2a$10$lZAPEVgA2hDh92p9e5i.N.Umg6jNKWfYjm0MPglexCxlgB44hxmQC', '1651561568@qq.com');

-- ----------------------------
-- Table structure for auth_user_role_table
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role_table`;
CREATE TABLE `auth_user_role_table`  (
  `user_id` bigint(25) NOT NULL,
  `role_id` bigint(25) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user_role_table
-- ----------------------------
INSERT INTO `auth_user_role_table` VALUES (1, 1);
INSERT INTO `auth_user_role_table` VALUES (2, 2);
INSERT INTO `auth_user_role_table` VALUES (4, 1);
INSERT INTO `auth_user_role_table` VALUES (5, 4);
INSERT INTO `auth_user_role_table` VALUES (6, 3);

-- ----------------------------
-- Table structure for databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `databasechangeloglock`;
CREATE TABLE `databasechangeloglock`  (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime(0) NULL DEFAULT NULL,
  `LOCKEDBY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of databasechangeloglock
-- ----------------------------
INSERT INTO `databasechangeloglock` VALUES (1, b'0', NULL, NULL);

-- ----------------------------
-- Table structure for eq_compensate
-- ----------------------------
DROP TABLE IF EXISTS `eq_compensate`;
CREATE TABLE `eq_compensate`  (
  `compensate_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `eq_id` bigint(11) NULL DEFAULT NULL,
  `compensate_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`compensate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eq_compensate
-- ----------------------------
INSERT INTO `eq_compensate` VALUES (1, 12, 20.00);
INSERT INTO `eq_compensate` VALUES (2, 13, 10.00);

-- ----------------------------
-- Table structure for eq_damage
-- ----------------------------
DROP TABLE IF EXISTS `eq_damage`;
CREATE TABLE `eq_damage`  (
  `eq_damage_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eq_id` bigint(20) NULL DEFAULT NULL,
  `damage_number` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`eq_damage_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eq_damage
-- ----------------------------
INSERT INTO `eq_damage` VALUES (3, 12, 0);
INSERT INTO `eq_damage` VALUES (4, 13, 0);

-- ----------------------------
-- Table structure for eq_info
-- ----------------------------
DROP TABLE IF EXISTS `eq_info`;
CREATE TABLE `eq_info`  (
  `eq_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '器材ID',
  `eq_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '器材名',
  `eq_total_number` int(255) NULL DEFAULT NULL COMMENT '器材总数',
  `eq_used_number` int(255) NULL DEFAULT NULL COMMENT '器材在库数量',
  `eq_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '租借价格',
  PRIMARY KEY (`eq_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eq_info
-- ----------------------------
INSERT INTO `eq_info` VALUES (12, '足球1', 5, 2, 6.00);
INSERT INTO `eq_info` VALUES (13, '篮球', 2, 2, 4.00);
INSERT INTO `eq_info` VALUES (14, '排球', 1, 1, 2.00);
INSERT INTO `eq_info` VALUES (15, '羽毛球拍', 4, 4, 1.00);
INSERT INTO `eq_info` VALUES (16, '网球', 2, 2, 3.00);

-- ----------------------------
-- Table structure for eq_order
-- ----------------------------
DROP TABLE IF EXISTS `eq_order`;
CREATE TABLE `eq_order`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `order_price` decimal(10, 2) NULL DEFAULT NULL,
  `order_type` bigint(255) NOT NULL COMMENT '订单类型',
  `order_create_time` datetime(0) NULL DEFAULT NULL COMMENT '订单创建时间',
  `order_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '租借开始时间',
  `order_end_time` datetime(0) NULL DEFAULT NULL COMMENT '租借结束时间',
  `order_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eq_order
-- ----------------------------
INSERT INTO `eq_order` VALUES (8, 5, 650.00, 2, '2018-06-17 13:37:08', '2018-06-17 13:37:08', NULL, '');
INSERT INTO `eq_order` VALUES (9, 5, 450.00, 2, '2019-06-17 13:42:08', '2019-06-18 13:17:33', NULL, '');
INSERT INTO `eq_order` VALUES (10, 5, 6.00, 1, '2019-06-15 13:18:04', '2019-06-17 13:37:08', '2019-06-17 22:20:55', '订单超时');
INSERT INTO `eq_order` VALUES (11, 5, 400.00, 2, '2019-06-18 13:17:40', '2019-06-17 14:54:47', NULL, '');
INSERT INTO `eq_order` VALUES (12, 5, 2.00, 1, '2019-06-18 13:18:06', '2019-06-18 14:54:47', '2019-06-18 12:54:47', '租借完成');
INSERT INTO `eq_order` VALUES (13, 5, 200.00, 3, '2019-06-18 07:30:49', NULL, NULL, '维修结束');
INSERT INTO `eq_order` VALUES (14, 5, NULL, 4, '2018-06-18 07:30:49', NULL, NULL, NULL);
INSERT INTO `eq_order` VALUES (15, 5, 3.00, 1, '2018-06-21 12:27:55', '2018-06-21 12:27:55', '2018-06-21 12:27:55', '租借完成');
INSERT INTO `eq_order` VALUES (16, 5, 3.00, 1, '2019-06-21 12:28:06', '2019-06-21 12:28:06', '2019-06-21 12:28:06', '订单超时');
INSERT INTO `eq_order` VALUES (17, 5, 13.00, 1, '2019-06-21 12:31:03', '2019-06-21 12:31:03', '2019-06-21 12:31:03', '订单超时');
INSERT INTO `eq_order` VALUES (18, 5, 13.00, 1, '2019-06-21 12:32:06', '2019-06-21 12:32:06', '2019-06-21 12:32:06', '订单超时');
INSERT INTO `eq_order` VALUES (19, 5, 1.00, 1, '2019-06-21 13:05:47', '2019-06-21 13:05:47', '2019-06-21 13:05:47', '订单超时');
INSERT INTO `eq_order` VALUES (20, 5, 1.00, 1, '2019-06-21 13:06:02', '2019-06-21 13:06:02', '2019-06-21 13:06:02', '租借完成');
INSERT INTO `eq_order` VALUES (21, 5, 1.00, 1, '2019-06-21 13:06:17', '2019-06-21 13:06:17', '2019-06-21 13:06:17', '订单超时');
INSERT INTO `eq_order` VALUES (22, 5, 1.00, 1, '2019-06-21 13:06:48', '2019-06-21 13:06:48', '2019-06-21 13:06:48', '订单超时');
INSERT INTO `eq_order` VALUES (23, 5, 10.00, 1, '2019-06-21 13:28:48', '2019-06-21 13:28:48', '2019-06-21 13:28:48', '订单超时');
INSERT INTO `eq_order` VALUES (24, 5, 8.00, 1, '2019-06-27 06:58:17', '2019-06-27 06:58:17', '2019-06-27 08:58:17', '订单超时');
INSERT INTO `eq_order` VALUES (25, 5, 6.00, 1, '2019-06-27 07:02:35', '2019-06-27 07:02:35', '2019-06-27 08:02:35', '订单超时');
INSERT INTO `eq_order` VALUES (26, 5, 48.00, 1, '2019-06-27 07:03:32', '2019-06-27 07:03:32', '2019-06-27 09:03:32', '订单超时');
INSERT INTO `eq_order` VALUES (27, 5, 8.00, 1, '2019-06-27 15:08:15', '2019-06-27 15:08:15', '2019-06-27 17:08:15', '订单超时');
INSERT INTO `eq_order` VALUES (28, 5, 20.00, 2, '2019-06-27 07:24:14', NULL, NULL, '');
INSERT INTO `eq_order` VALUES (29, 5, NULL, 4, '2019-06-27 07:24:49', NULL, NULL, NULL);
INSERT INTO `eq_order` VALUES (30, 5, NULL, 4, '2019-06-27 07:25:53', NULL, NULL, NULL);
INSERT INTO `eq_order` VALUES (31, 5, 10.00, 3, '2019-06-27 07:28:49', NULL, NULL, '报修中');
INSERT INTO `eq_order` VALUES (32, 5, 6.00, 2, '2019-06-27 13:16:15', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for eq_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `eq_order_detail`;
CREATE TABLE `eq_order_detail`  (
  `order_detail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `eq_id` bigint(20) NOT NULL,
  `eq_number` int(11) NULL DEFAULT NULL,
  `eq_order_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '操作价格',
  PRIMARY KEY (`order_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eq_order_detail
-- ----------------------------
INSERT INTO `eq_order_detail` VALUES (1, 8, 12, 3, 450.00);
INSERT INTO `eq_order_detail` VALUES (2, 8, 13, 2, 200.00);
INSERT INTO `eq_order_detail` VALUES (3, 9, 12, 2, 400.00);
INSERT INTO `eq_order_detail` VALUES (4, 9, 14, 1, 50.00);
INSERT INTO `eq_order_detail` VALUES (5, 10, 12, 1, 6.00);
INSERT INTO `eq_order_detail` VALUES (6, 11, 15, 4, 400.00);
INSERT INTO `eq_order_detail` VALUES (7, 12, 15, 2, 2.00);
INSERT INTO `eq_order_detail` VALUES (8, 13, 12, 2, NULL);
INSERT INTO `eq_order_detail` VALUES (9, 13, 13, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (10, 14, 12, 2, NULL);
INSERT INTO `eq_order_detail` VALUES (11, 15, 14, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (12, 15, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (13, 16, 14, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (14, 16, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (15, 17, 12, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (16, 17, 13, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (17, 17, 14, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (18, 17, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (19, 18, 12, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (20, 18, 13, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (21, 18, 14, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (22, 18, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (23, 19, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (24, 20, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (25, 21, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (26, 22, 15, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (27, 23, 12, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (28, 23, 13, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (29, 24, 13, 1, 4.00);
INSERT INTO `eq_order_detail` VALUES (30, 25, 12, 1, 6.00);
INSERT INTO `eq_order_detail` VALUES (31, 26, 12, 2, 12.00);
INSERT INTO `eq_order_detail` VALUES (32, 26, 12, 2, 12.00);
INSERT INTO `eq_order_detail` VALUES (33, 27, 13, 1, 4.00);
INSERT INTO `eq_order_detail` VALUES (34, 28, 12, 2, 10.00);
INSERT INTO `eq_order_detail` VALUES (35, 28, 12, 2, 10.00);
INSERT INTO `eq_order_detail` VALUES (36, 29, 12, 1, NULL);
INSERT INTO `eq_order_detail` VALUES (37, 30, 12, 2, NULL);
INSERT INTO `eq_order_detail` VALUES (38, 31, 12, 3, NULL);
INSERT INTO `eq_order_detail` VALUES (39, 32, 16, 2, 6.00);

-- ----------------------------
-- Table structure for match_info
-- ----------------------------
DROP TABLE IF EXISTS `match_info`;
CREATE TABLE `match_info`  (
  `match_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '比赛id',
  `referee_id` int(11) NULL DEFAULT NULL COMMENT '裁判id',
  `area_book_id` int(11) NULL DEFAULT NULL COMMENT '场地预定id',
  PRIMARY KEY (`match_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_type
-- ----------------------------
DROP TABLE IF EXISTS `order_type`;
CREATE TABLE `order_type`  (
  `type_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_type
-- ----------------------------
INSERT INTO `order_type` VALUES (1, '器材租借');
INSERT INTO `order_type` VALUES (2, '器材购买');
INSERT INTO `order_type` VALUES (3, '器材维护');
INSERT INTO `order_type` VALUES (4, '器材报废');

-- ----------------------------
-- Table structure for referee_info
-- ----------------------------
DROP TABLE IF EXISTS `referee_info`;
CREATE TABLE `referee_info`  (
  `referee_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '裁判id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '裁判名字',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '裁判信息',
  PRIMARY KEY (`referee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
