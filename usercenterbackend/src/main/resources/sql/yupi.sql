/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3307
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : localhost:3307
 Source Schema         : yupi

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 12/09/2023 14:52:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint                                                         NOT NULL AUTO_INCREMENT,
    `userName`     varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NULL     DEFAULT NULL COMMENT '用户昵称',
    `userAccount`  varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NULL     DEFAULT NULL COMMENT '账号',
    `avatarUrl`    varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT NULL COMMENT '用户头像',
    `gender`       tinyint                                                        NULL     DEFAULT NULL COMMENT '性别',
    `userPassword` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NOT NULL COMMENT '密码',
    `email`        varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NULL     DEFAULT NULL COMMENT '邮箱',
    `userStatus`   int                                                            NULL     DEFAULT 0 COMMENT '状态 0-正常',
    `phone`        varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NULL     DEFAULT NULL COMMENT '电话',
    `createTime`   datetime                                                       NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`   datetime                                                       NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`     tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    `userRole`     int                                                            NOT NULL DEFAULT 0 COMMENT '用户权限（用户0，管理员1）',
    `planetCode`   varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NULL     DEFAULT NULL COMMENT '星球编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (4, NULL, 'dogyupi', 'https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png', NULL,
        '1eb8ca928c94c2000b84b66f1cf43933', NULL, 0, NULL, '2023-09-06 19:41:51', '2023-09-11 22:24:38', 0, 0, NULL);
INSERT INTO `user`
VALUES (5, NULL, 'yupi', 'https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png', NULL,
        '1eb8ca928c94c2000b84b66f1cf43933', NULL, 0, NULL, '2023-09-06 19:42:46', '2023-09-11 22:24:38', 0, 1, NULL);
INSERT INTO `user`
VALUES (6, 'dogYupi', '123', 'https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png', 0,
        'df96b24b270f77bbf76ed509fcbfa340', '456', 0, '123', '2023-09-09 00:03:53', '2023-09-10 17:22:36', 0, 0, NULL);
INSERT INTO `user`
VALUES (7, 'dogYupi', '123', 'https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png', 0,
        'df96b24b270f77bbf76ed509fcbfa340', '456', 0, '123', '2023-09-09 00:05:19', '2023-09-10 17:22:36', 0, 0, NULL);
INSERT INTO `user`
VALUES (8, 'xuan', 'xuan', 'https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png', NULL,
        'df96b24b270f77bbf76ed509fcbfa340', NULL, 0, NULL, '2023-09-10 17:21:51', '2023-09-11 22:24:38', 0, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
