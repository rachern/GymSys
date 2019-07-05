/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : myself

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 28/06/2019 14:28:39
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area_type
-- ----------------------------
DROP TABLE IF EXISTS `area_type`;
CREATE TABLE `area_type`  (
  `area_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '场地类型id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场地类型名字',
  PRIMARY KEY (`area_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for match_info
-- ----------------------------
DROP TABLE IF EXISTS `match_info`;
CREATE TABLE `match_info`  (
  `match_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '比赛id',
  `referee_id` int(11) NULL DEFAULT NULL COMMENT '裁判id',
  `area_book_id` int(11) NULL DEFAULT NULL COMMENT '场地预定id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛名称',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛内容',
  `register_id` bigint(20) NULL DEFAULT NULL COMMENT '申请人id',
  PRIMARY KEY (`match_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for referee_info
-- ----------------------------
DROP TABLE IF EXISTS `referee_info`;
CREATE TABLE `referee_info`  (
  `referee_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '裁判id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '裁判名字',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '裁判信息',
  PRIMARY KEY (`referee_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
