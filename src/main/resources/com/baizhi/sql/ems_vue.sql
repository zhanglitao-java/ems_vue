/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50647
Source Host           : localhost:3306
Source Database       : taotao_emp

Target Server Type    : MYSQL
Target Server Version : 50647
File Encoding         : 65001

Date: 2021-03-13 16:07:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_emp`
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '员工主键id',
  `name` varchar(40) DEFAULT NULL COMMENT '员工姓名',
  `path` varchar(100) DEFAULT NULL COMMENT '图片存储地址',
  `salary` double(10,2) DEFAULT NULL COMMENT '薪资',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_emp
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sex` varchar(4) DEFAULT NULL COMMENT '性别',
  `status` varchar(4) DEFAULT NULL COMMENT '用户是否激活状态',
  `registerTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
