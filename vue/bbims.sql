/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : bbims

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 06/12/2021 20:44:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `headpic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` int NOT NULL DEFAULT 2 COMMENT '角色 1超级管理员 2普通管理员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '管理员', '123456', 'http://localhost:9090/files/43740d70d50a4795a28eab31667e1899', 1);
INSERT INTO `admin` VALUES (2, 'wangkang', '哈哈哈哈', '123456', 'http://localhost:9090/files/495d0a3983614cac8d5907452d09aa30', 1);
INSERT INTO `admin` VALUES (3, '12312', 'aaawqq', '123123123', 'http://localhost:9090/files/f8e0855474654de8a0f8c3059dc4a040', 2);
INSERT INTO `admin` VALUES (4, '12312', 'qweqe', '13asdas', 'http://localhost:9090/files/3eae37bb57b34cf99430542b9a05c657', 2);
INSERT INTO `admin` VALUES (9, 'hahah', '我是菜狗', 'wwwwwww', 'http://localhost:9090/files/d24c5e18d23248528eaf2569ca735f9e', 2);
INSERT INTO `admin` VALUES (10, '1', '1213', '123456', 'http://localhost:9090/files/be4dab6736654a9ea0845c7b567d05d6', 2);
INSERT INTO `admin` VALUES (11, 'admin12', '管理员11', '123456', 'http://localhost:9090/files/71ada932b6404f6b9a2ff067ab676962', 1);
INSERT INTO `admin` VALUES (14, '1111', '1111', '1111', 'http://localhost:9090/files/af671b0d0c4e45cbbed606c43939cbf2', 2);
INSERT INTO `admin` VALUES (15, '222', '222', '222', 'http://localhost:9090/files/64ce7d1fb9144b79aa640760d5025912', 2);
INSERT INTO `admin` VALUES (16, 'adm1', NULL, '123456', NULL, 2);
INSERT INTO `admin` VALUES (17, 'ad', '萨达萨达', 'asdad', 'http://localhost:9090/files/9f3e94417a5c4003aa0842d72375f883', 2);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '书名',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类别',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `createtime` date NULL DEFAULT NULL COMMENT '出版日期',
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'isbn',
  `inventory` int NULL DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (4, '1', 1.54, '1', '文学', 'http://localhost:9090/files/2ff8a302a481496cb93a7e1703bfebcd', '2021-12-15', '1', 1);
INSERT INTO `book` VALUES (9, '12', NULL, '1', '文学', 'http://localhost:9090/files/1493824334be4a738ccf62e4b5a8ba0d', NULL, '1', 1111);
INSERT INTO `book` VALUES (10, '2222', 1.12, '2', '童书', 'http://localhost:9090/files/db9e90de04bc4d0dbb1e5f307344dd19', NULL, '2', 22222);
INSERT INTO `book` VALUES (11, '22', 1.12, '5', '社会科学', 'http://localhost:9090/files/ed0d5c4f7b2f469db7ee64207160672a', NULL, '23', 1111);
INSERT INTO `book` VALUES (12, '12', 1.00, '1', '经济学', 'http://localhost:9090/files/1493824334be4a738ccf62e4b5a8ba0d', NULL, '1', 1111);
INSERT INTO `book` VALUES (16, '1', NULL, '2', '小说', 'http://localhost:9090/files/db9e90de04bc4d0dbb1e5f307344dd19', NULL, '2', 2);
INSERT INTO `book` VALUES (17, '3', 1.12, '3', '政治', 'http://localhost:9090/files/71990be093b3453f96f4927fbff756ed', NULL, '3', 3);
INSERT INTO `book` VALUES (18, '12', 1.12, '1', '法律', 'http://localhost:9090/files/1493824334be4a738ccf62e4b5a8ba0d', NULL, '1', 1111);
INSERT INTO `book` VALUES (19, '2222', 1.54, '2', '寓言', 'http://localhost:9090/files/db9e90de04bc4d0dbb1e5f307344dd19', NULL, '2', 22222);
INSERT INTO `book` VALUES (20, '22', 1.54, '5', '神话', 'http://localhost:9090/files/ed0d5c4f7b2f469db7ee64207160672a', NULL, '23', 1111);
INSERT INTO `book` VALUES (21, '12', 1.54, '1', '文学', 'http://localhost:9090/files/1493824334be4a738ccf62e4b5a8ba0d', NULL, '1', 1111);
INSERT INTO `book` VALUES (22, '2222', NULL, '2', '童书', 'http://localhost:9090/files/db9e90de04bc4d0dbb1e5f307344dd19', NULL, '2', 22222);
INSERT INTO `book` VALUES (23, '22', NULL, '5', '社会科学', 'http://localhost:9090/files/ed0d5c4f7b2f469db7ee64207160672a', NULL, '23', 1111);
INSERT INTO `book` VALUES (24, '1', 1.12, '1111', '经济学', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', NULL, '1', 1);
INSERT INTO `book` VALUES (25, '1', 1.12, '1111', '小说', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', NULL, '1', 1);
INSERT INTO `book` VALUES (26, '哇大大伟大2', 1.54, '1', '政治', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', NULL, '1', 1);
INSERT INTO `book` VALUES (27, '1哇', 1.54, '1', '法律', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', NULL, '1', 1);
INSERT INTO `book` VALUES (28, '1哇', 1.54, '1', '寓言', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', NULL, '1', 1);
INSERT INTO `book` VALUES (29, '1哇', 1.54, '1', '神话', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', '2021-12-13', '1', 1);
INSERT INTO `book` VALUES (31, '1哇', 1.54, '1', '文学', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', '2021-12-13', '1', 1);
INSERT INTO `book` VALUES (32, '1哇', 1.54, '1', '童书', 'http://localhost:9090/files/f80d212dc22d48a0b75a6c0ac59c808d', '2021-12-13', '1', 1);
INSERT INTO `book` VALUES (33, '11111', NULL, NULL, '社会科学', 'http://localhost:9090/files/0edfc7190f354bdaab70554f7d43e0db', NULL, NULL, NULL);
INSERT INTO `book` VALUES (34, '11231321', 1.54, '1', '经济学', 'http://localhost:9090/files/8e7a7dc04ef14344bcaaa7c0b42ae461', '2021-10-13', '1', 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `pid` int NULL DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '文学', NULL);
INSERT INTO `category` VALUES (2, '童书', 1);
INSERT INTO `category` VALUES (3, '社会科学', 1);
INSERT INTO `category` VALUES (4, '经济学', 1);
INSERT INTO `category` VALUES (5, '小说', 1);
INSERT INTO `category` VALUES (6, '政治', 3);
INSERT INTO `category` VALUES (7, '法律', 3);
INSERT INTO `category` VALUES (8, '寓言', 6);
INSERT INTO `category` VALUES (9, '神话', NULL);
INSERT INTO `category` VALUES (11, '123', 11);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论人',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论时间',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID',
  `foreign_id` bigint NULL DEFAULT 0 COMMENT '关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '留言表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (21, '哦豁', 'admin', '2021-05-22 10:49:48', NULL, 0);
INSERT INTO `message` VALUES (22, '老弟', 'admin', '2021-05-22 10:51:07', 21, 0);
INSERT INTO `message` VALUES (23, '哈哈哈', 'wangkang', '2021-05-24 17:13:45', 22, 0);
INSERT INTO `message` VALUES (24, '我们都爱吃大西瓜', 'wangkang', '2021-05-24 17:13:58', NULL, 0);
INSERT INTO `message` VALUES (25, '41414141414', 'admin', '2021-12-03 09:20:21', NULL, 0);
INSERT INTO `message` VALUES (27, 'aaaa', 'admin', '2021-12-06 11:15:31', 26, 0);
INSERT INTO `message` VALUES (30, '123123123', 'admin', '2021-12-06 11:24:30', 29, 0);
INSERT INTO `message` VALUES (32, 'ds', '1', '2021-12-06 11:42:09', NULL, 0);
INSERT INTO `message` VALUES (34, 'adaad', 'admin', '2021-12-06 13:17:24', 33, 0);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `number` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `book_id` int NULL DEFAULT NULL,
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户账户',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '实付款',
  `discount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '优惠金额',
  `transport_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '运费',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `state` int NOT NULL DEFAULT 0 COMMENT '状态 0/1 未支付/支付',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (23, '1234567890123120', 4, '1', 1, 'wangkang', 1.54, 1.54, 0.00, 10.00, '2021-12-06 19:09:53', NULL, 0);
INSERT INTO `order` VALUES (28, '1234567890123120', 4, '1', 1, 'wangkang', 1.54, 1.54, 0.00, 0.00, '2021-10-06 10:00:53', NULL, 0);
INSERT INTO `order` VALUES (29, '1234567890123120', 4, '1', 1, 'wangkang', 1.54, 1.54, 0.00, 0.00, '2021-10-06 10:00:53', NULL, 0);
INSERT INTO `order` VALUES (31, '1234567890123120', 4, '1', 1, 'wangkang', 1.54, 1.54, 0.00, 0.00, '2021-10-06 10:00:53', NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `role` int NOT NULL DEFAULT 0 COMMENT '角色 0普通用户 1会员',
  `headpic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wangkang1', 'ha', '123456', 12, '女', '地球11', 0, 'http://localhost:9090/files/e05c23deb9074ab8af28f2f6ac52c256');
INSERT INTO `user` VALUES (2, 'daw', '12', ' 21212', 134, '女', '火星', 0, 'http://localhost:9090/files/1c0fd97647474c2ea0701be4560c96e7');
INSERT INTO `user` VALUES (3, 'jjj', 'qwqw', ' qqwqq', 1111, '男', '地球', 0, 'http://localhost:9090/files/7d3624b4fb7a4e608c87ae95926fc95e');
INSERT INTO `user` VALUES (4, '121', 'hahah1', '222222', 222, '未知', '地球', 0, 'http://localhost:9090/files/5fcb7c3446fe41549a61bdf37832ea54');
INSERT INTO `user` VALUES (5, 'daw1', '12111', '111', 134, '未知', '火星', 0, '');
INSERT INTO `user` VALUES (7, '121', 'hahah1', '222222', 222, '男', '地球', 0, 'http://localhost:9090/files/aa5dcb8608494fcdbd935e5aa2d16bef');
INSERT INTO `user` VALUES (10, 'wangkang1', 'hahah', '123456', 12, '未知', '地球', 0, 'http://localhost:9090/files/fb53b900af994ccf9eea23633c45734f');
INSERT INTO `user` VALUES (11, 'daw', '12', ' 21212', 134, '女', '火星', 0, 'http://localhost:9090/files/1c0fd97647474c2ea0701be4560c96e7');
INSERT INTO `user` VALUES (12, 'jjj', 'qwqw', ' qqwqq', 1111, '男', '地球', 0, 'http://localhost:9090/files/ca9efe6adbee4c25beba7bac91d5079a');
INSERT INTO `user` VALUES (15, '121', 'hahah1', '222222', 222, '男', '地球', 0, 'http://localhost:9090/files/aa5dcb8608494fcdbd935e5aa2d16bef');
INSERT INTO `user` VALUES (16, 'jjj1', 'qwqw111', '111111', 222, '男', '地球', 0, '');
INSERT INTO `user` VALUES (17, 'wangkang1', 'hahah', '123456', 12, '未知', '地球', 0, 'http://localhost:9090/files/fb53b900af994ccf9eea23633c45734f');
INSERT INTO `user` VALUES (18, 'daw', '12', ' 21212', 134, '女', '火星', 0, 'http://localhost:9090/files/1c0fd97647474c2ea0701be4560c96e7');
INSERT INTO `user` VALUES (19, 'jjj', 'qwqw', ' qqwqq', 1111, '男', '地球', 0, 'http://localhost:9090/files/7d3624b4fb7a4e608c87ae95926fc95e');
INSERT INTO `user` VALUES (20, '121', 'hahah1', '222222', 222, '未知', '地球', 0, 'http://localhost:9090/files/5fcb7c3446fe41549a61bdf37832ea54');
INSERT INTO `user` VALUES (21, 'daw1', '12111', '111', 134, '未知', '火星', 0, '');
INSERT INTO `user` VALUES (22, '121', 'hahah1', '222222', 222, '男', '地球', 0, 'http://localhost:9090/files/aa5dcb8608494fcdbd935e5aa2d16bef');
INSERT INTO `user` VALUES (23, 'jjj1', 'qwqw111', '111111', 222, '男', '地球', 0, '');
INSERT INTO `user` VALUES (24, 'wangkang1', 'hahah', '123456', 12, '未知', '地球', 0, 'http://localhost:9090/files/fb53b900af994ccf9eea23633c45734f');
INSERT INTO `user` VALUES (25, 'daw', '12', ' 21212', 134, '女', '火星', 0, 'http://localhost:9090/files/1c0fd97647474c2ea0701be4560c96e7');
INSERT INTO `user` VALUES (26, 'jjj', 'qwqw', ' qqwqq', 1111, '男', '地球', 0, 'http://localhost:9090/files/7d3624b4fb7a4e608c87ae95926fc95e');
INSERT INTO `user` VALUES (27, '121', 'hahah1', '222222', 222, '男', '地球', 0, 'http://localhost:9090/files/aa5dcb8608494fcdbd935e5aa2d16bef');
INSERT INTO `user` VALUES (28, 'jjj1', 'qwqw111', '111111', 222, '男', '地球', 0, '');
INSERT INTO `user` VALUES (29, 'qqq', 'hahah', '123456', 12, '未知', '地球', 0, 'http://localhost:9090/files/fb53b900af994ccf9eea23633c45734f');
INSERT INTO `user` VALUES (30, '1', '1', NULL, 1, '未知', '1111', 0, 'http://localhost:9090/files/6b9f7d72b83a4c178434abe8fdd50bf2');
INSERT INTO `user` VALUES (31, '213', '11321', NULL, 312, '女', '1231', 0, NULL);
INSERT INTO `user` VALUES (32, '12311321', '222', NULL, 11, '男', '1111', 0, 'http://localhost:9090/files/ed33c0c3b6ca467982c4cc19c22d0a42');
INSERT INTO `user` VALUES (33, '1', '1', NULL, 1, '男', '1', 0, 'http://localhost:9090/files/d85861c0910b49368aa4f1a4a8ef1f1a');

SET FOREIGN_KEY_CHECKS = 1;
