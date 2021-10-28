/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : volcano

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 27/10/2021 21:31:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for volcano_roles
-- ----------------------------
DROP TABLE IF EXISTS `volcano_roles`;
CREATE TABLE `volcano_roles`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_rc5oqm1dxegvou8gi0goetf4m`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of volcano_roles
-- ----------------------------
INSERT INTO `volcano_roles` VALUES (1, 'ROLE_USER', '员工');
INSERT INTO `volcano_roles` VALUES (2, 'ROLE_ADMIN', '管理员');

-- ----------------------------
-- Table structure for volcano_users
-- ----------------------------
DROP TABLE IF EXISTS `volcano_users`;
CREATE TABLE `volcano_users`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `enabled` bit(1) NOT NULL,
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password_hash` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_miktqqu0rp4ud1ign7im14tls`(`email`) USING BTREE,
  UNIQUE INDEX `UK_2oxvufns78dymbl7l58dcy1c4`(`mobile`) USING BTREE,
  UNIQUE INDEX `UK_tnc0dltyfewuu3yq4fex72map`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of volcano_users
-- ----------------------------
INSERT INTO `volcano_users` VALUES (1, b'1', b'1', b'1', 'zhangsan@local.dev', b'1', '13000000001', 'Zhang San', '{bcrypt}$2a$10$jhS817qUHgOR4uQSoEBRxO58.rZ1dBCmCTjG8PeuQAX4eISf.zowm', 'user');
INSERT INTO `volcano_users` VALUES (2, b'1', b'1', b'1', 'lisi@local.dev', b'1', '13000000002', 'Li Si', '{SHA-1}{TMlrFVppiMOhmI6VBoytlEkepfqUHrpyOXgCoFUo3Mk=}1ebde6bb35fd02816880948864fa771eb85a122a', 'old_user');

-- ----------------------------
-- Table structure for volcano_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `volcano_users_roles`;
CREATE TABLE `volcano_users_roles`  (
  `user_id` bigint(0) NOT NULL,
  `role_id` bigint(0) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKh2kameocdq0sire4vajm0h45g`(`role_id`) USING BTREE,
  CONSTRAINT `FK4yletyn0k3f1fhosd7wym8pbs` FOREIGN KEY (`user_id`) REFERENCES `volcano_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKh2kameocdq0sire4vajm0h45g` FOREIGN KEY (`role_id`) REFERENCES `volcano_roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of volcano_users_roles
-- ----------------------------
INSERT INTO `volcano_users_roles` VALUES (1, 1);
INSERT INTO `volcano_users_roles` VALUES (2, 1);
INSERT INTO `volcano_users_roles` VALUES (1, 2);

SET FOREIGN_KEY_CHECKS = 1;
