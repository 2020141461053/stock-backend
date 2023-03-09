/*
 Navicat Premium Data Transfer

 Source Server         : guanyan
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 1.117.68.73:3306
 Source Schema         : stock

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 28/02/2023 11:50:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
                          `id` int(0) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `creat_date` float(255, 0) NULL DEFAULT NULL,
  `min_low` float(255, 0) NULL DEFAULT NULL,
  `max_high` float(255, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES (5, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for stock_data
-- ----------------------------
DROP TABLE IF EXISTS `stock_data`;
CREATE TABLE `stock_data`  (
                               `id` int(0) NOT NULL AUTO_INCREMENT,
                               `sid` int(0) NULL DEFAULT NULL,
                               `last_value` float(255, 0) NULL DEFAULT NULL,
  `value` float(255, 0) NULL DEFAULT NULL,
  `turnover` int(0) NULL DEFAULT NULL,
  `low` float(255, 0) NULL DEFAULT NULL,
  `high` float(255, 0) NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_data
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int(0) NOT NULL AUTO_INCREMENT,
                         `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '35b9529f89cfb9b848060ca576237e17', '8O+vDNr2sI3N82BI31fu1A==', 'admin', '初始管理员');
INSERT INTO `user` VALUES (2, 'testuser', 'd596a809e1d07519fc35d76d6fabb14e', 'xUq7ziaWk5NsNH8+wn4xGg==', 'user', 'test');
INSERT INTO `user` VALUES (3, 'testuser1', '66bfa88097cf4aaee5cc991ed4211f27', 'Njs+3ma0lZ62N/VGIKGBMA==', 'user', 'test1');
INSERT INTO `user` VALUES (4, 'testuser3', 'a1d9728ca41b2cf74baf76098223b1e5', 'xi781UTOzk81jkTJWyY7Yw==', 'user', 'test3');

-- ----------------------------
-- Table structure for user_stock
-- ----------------------------
DROP TABLE IF EXISTS `user_stock`;
CREATE TABLE `user_stock`  (
                               `id` int(0) NOT NULL AUTO_INCREMENT,
                               `uid` int(0) NULL DEFAULT NULL,
                               `sid` int(0) NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_stock
-- ----------------------------
INSERT INTO `user_stock` VALUES (2, 1, 8);
INSERT INTO `user_stock` VALUES (3, 1, 6);
INSERT INTO `user_stock` VALUES (4, 1, 7);
INSERT INTO `user_stock` VALUES (5, 1, 6);
INSERT INTO `user_stock` VALUES (6, 1, 7);

SET FOREIGN_KEY_CHECKS = 1;
