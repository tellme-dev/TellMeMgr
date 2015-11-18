/*
Navicat MySQL Data Transfer

Source Server         : 222.211.79.78
Source Server Version : 50173
Source Host           : 222.211.79.78:3306
Source Database       : yw

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-11-10 20:15:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c_company
-- ----------------------------
DROP TABLE IF EXISTS `c_company`;
CREATE TABLE `c_company` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所在地区',
  `ADDRESS` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `CODE` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `TYPE_KEY` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '客户公司类型',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `USER_NUM` int(11) DEFAULT NULL COMMENT '员工数量',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户公司表';

-- ----------------------------
-- Records of c_company
-- ----------------------------

-- ----------------------------
-- Table structure for c_user
-- ----------------------------
DROP TABLE IF EXISTS `c_user`;
CREATE TABLE `c_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '员工姓名',
  `CLIENT_COMPANY_ID` int(11) DEFAULT NULL COMMENT '所属公司',
  `SEX` int(1) DEFAULT NULL COMMENT '性别',
  `PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `EMAIL` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `POST` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '岗位',
  `CLIENT_MANAGER` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理',
  `CLIENT_MANAGER_PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理电话',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中，1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `LOGO` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `FIRST_TIME` datetime DEFAULT NULL COMMENT '首次登陆时间',
  `ACCOUNT` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `SALT` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户联系人表';

-- ----------------------------
-- Records of c_user
-- ----------------------------

-- ----------------------------
-- Table structure for p_area
-- ----------------------------
DROP TABLE IF EXISTS `p_area`;
CREATE TABLE `p_area` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '区域名称',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `SERIAL_CODE` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '父区域ID',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='维护区域表';

-- ----------------------------
-- Records of p_area
-- ----------------------------

-- ----------------------------
-- Table structure for p_area_user
-- ----------------------------
DROP TABLE IF EXISTS `p_area_user`;
CREATE TABLE `p_area_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AREA_ID` int(11) DEFAULT NULL COMMENT '区域ID',
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '项目ID',
  `USER_ID` int(11) DEFAULT NULL COMMENT '员工ID',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='区域分配人员表';

-- ----------------------------
-- Records of p_area_user
-- ----------------------------

-- ----------------------------
-- Table structure for p_fault
-- ----------------------------
DROP TABLE IF EXISTS `p_fault`;
CREATE TABLE `p_fault` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '所属项目',
  `FAULT` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '故障现象',
  `COMMENT` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='故障现象表';

-- ----------------------------
-- Records of p_fault
-- ----------------------------

-- ----------------------------
-- Table structure for p_limit_template
-- ----------------------------
DROP TABLE IF EXISTS `p_limit_template`;
CREATE TABLE `p_limit_template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='响应时限模板表';

-- ----------------------------
-- Records of p_limit_template
-- ----------------------------

-- ----------------------------
-- Table structure for p_limit_times
-- ----------------------------
DROP TABLE IF EXISTS `p_limit_times`;
CREATE TABLE `p_limit_times` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `START_POINT` int(11) DEFAULT NULL COMMENT '起始节点',
  `END_POINT` int(11) DEFAULT NULL COMMENT '终止节点',
  `POINT_TIME` int(11) DEFAULT NULL COMMENT '响应时限（分钟）',
  `CREATE_TIME` int(11) DEFAULT NULL COMMENT '创建时间',
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '所属项目',
  `WARN` int(11) DEFAULT NULL COMMENT '超时预警',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目流程响应时限表';

-- ----------------------------
-- Records of p_limit_times
-- ----------------------------

-- ----------------------------
-- Table structure for p_point
-- ----------------------------
DROP TABLE IF EXISTS `p_point`;
CREATE TABLE `p_point` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POINT_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '点位ID',
  `POINT_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '点位编号',
  `NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '点位名称',
  `TYPE` int(11) DEFAULT NULL COMMENT '点位类型',
  `ADDRESS` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '点位地址',
  `LOCATION_X` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'X坐标',
  `LOCATION_Y` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'Y坐标',
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '所属项目',
  `AREA_ID` int(11) DEFAULT NULL COMMENT '所属区域',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `POINT_PICTURE` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '点位图片',
  `IP_ADDRESS` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `LIMIT_TIME` int(11) DEFAULT NULL COMMENT '故障处理时限',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='点位表';

-- ----------------------------
-- Records of p_point
-- ----------------------------

-- ----------------------------
-- Table structure for p_project
-- ----------------------------
DROP TABLE IF EXISTS `p_project`;
CREATE TABLE `p_project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '项目编号',
  `NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称',
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所在地州',
  `INDUSTRY_KEY` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '所属行业',
  `START_DATE` date DEFAULT NULL COMMENT '维护开始时间',
  `END_DATE` date DEFAULT NULL COMMENT '维护结束时间',
  `CLIENT_MANAGER` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理',
  `MAINTAIN_CONTRACT` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '维护合同编号',
  `CONTRACT_NUM` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '合同金额',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `CLIENT_COMPANY` int(11) DEFAULT NULL COMMENT '项目所属客户公司',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目表';

-- ----------------------------
-- Records of p_project
-- ----------------------------

-- ----------------------------
-- Table structure for p_project_area
-- ----------------------------
DROP TABLE IF EXISTS `p_project_area`;
CREATE TABLE `p_project_area` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_ID` int(11) DEFAULT NULL,
  `AREA_ID` int(11) DEFAULT NULL,
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目区域关系表';

-- ----------------------------
-- Records of p_project_area
-- ----------------------------

-- ----------------------------
-- Table structure for p_project_company
-- ----------------------------
DROP TABLE IF EXISTS `p_project_company`;
CREATE TABLE `p_project_company` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_ID` int(11) DEFAULT NULL,
  `COMPANY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目和维护公司关系表';

-- ----------------------------
-- Records of p_project_company
-- ----------------------------

-- ----------------------------
-- Table structure for p_solution
-- ----------------------------
DROP TABLE IF EXISTS `p_solution`;
CREATE TABLE `p_solution` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '所属项目',
  `SOLUTION` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '解决办法',
  `DEVICE_CHANGE` int(1) DEFAULT NULL COMMENT '是否属于更换设备（0：不属于，1：属于）',
  `COMMENT` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='解决办法表';

-- ----------------------------
-- Records of p_solution
-- ----------------------------

-- ----------------------------
-- Table structure for p_template_times
-- ----------------------------
DROP TABLE IF EXISTS `p_template_times`;
CREATE TABLE `p_template_times` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `START_POINT` int(11) DEFAULT NULL COMMENT '起始节点',
  `END_POINT` int(11) DEFAULT NULL COMMENT '终止节点',
  `POINT_TIME` int(11) DEFAULT NULL COMMENT '操作响应时限（小时）',
  `CREATE_TIME` int(11) DEFAULT NULL COMMENT '创建时间',
  `LIMIT_ID` int(11) DEFAULT NULL COMMENT '所属模板',
  `WARN` int(11) DEFAULT NULL COMMENT '超时预警',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='流程响应模板时限表';

-- ----------------------------
-- Records of p_template_times
-- ----------------------------

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONFIG_KEY` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT 'KEY',
  `CONFIG_VALUE` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'VALUE',
  `TYPE_ID` int(2) DEFAULT NULL COMMENT '类型ID',
  `TYPE_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '类型名称',
  `SORT` int(11) DEFAULT NULL COMMENT '排序号',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `SERIAL_CODE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_KEY` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='基础数据配置表';

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES ('1', '1', '前端点位', '1', '受理类型', '1', '0', null, '26');
INSERT INTO `t_config` VALUES ('2', '2', '监控中心', '1', '受理类型', '1', '0', null, '26');
INSERT INTO `t_config` VALUES ('3', '3', '中心平台', '1', '受理类型', '1', '0', null, '26');
INSERT INTO `t_config` VALUES ('4', '4', '技术支撑', '1', '受理类型', '1', '0', null, '26');
INSERT INTO `t_config` VALUES ('5', '5', '业务咨询', '1', '受理类型', '1', '0', null, '26');
INSERT INTO `t_config` VALUES ('6', '1', '服务台', '2', '员工类型', '1', '0', null, '27');
INSERT INTO `t_config` VALUES ('7', '2', '维护人员', '2', '员工类型', '1', '0', null, '27');
INSERT INTO `t_config` VALUES ('8', '3', '专家', '2', '员工类型', '1', '0', null, '27');
INSERT INTO `t_config` VALUES ('9', '1', '电源', '3', '员工维护类型', '1', '0', null, '28');
INSERT INTO `t_config` VALUES ('10', '2', '平台', '3', '员工维护类型', '1', '0', null, '28');
INSERT INTO `t_config` VALUES ('11', '3', '前端', '3', '员工维护类型', '1', '0', null, '28');
INSERT INTO `t_config` VALUES ('15', '4', '网络', '3', '员工维护类型', '1', '0', null, '28');
INSERT INTO `t_config` VALUES ('17', '1', '公安', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('18', '2', '党政军', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('19', '3', '文教', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('20', '4', '金融', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('21', '5', '大企业', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('22', '6', '能源', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('23', '7', '交通', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('24', '8', '重点行业', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('25', '9', '其他', '4', '项目所属行业', '1', '0', null, '29');
INSERT INTO `t_config` VALUES ('26', '1', '受理类型', '0', '大类', '1', '0', null, '0');
INSERT INTO `t_config` VALUES ('27', '2', '员工类型', '0', '大类', '1', '0', null, '0');
INSERT INTO `t_config` VALUES ('28', '3', '员工维护类型', '0', '大类', '1', '0', null, '0');
INSERT INTO `t_config` VALUES ('29', '4', '项目所属行业', '0', '大类', '1', '0', null, '0');

-- ----------------------------
-- Table structure for t_process
-- ----------------------------
DROP TABLE IF EXISTS `t_process`;
CREATE TABLE `t_process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NODE_ID` int(2) DEFAULT NULL COMMENT '流程节点ID（1：派单，2：接单，3：退单，4：挂单，5：解挂，6：汇报，7：请验，8：关闭）',
  `USER_ID` int(11) DEFAULT NULL COMMENT '操作人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  `COMMENT` varchar(600) COLLATE utf8_bin DEFAULT NULL COMMENT '操作描述',
  `TICKET_ID` int(11) DEFAULT NULL COMMENT '所属工单',
  `FAULT_ID` int(11) DEFAULT NULL,
  `SOLUTION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='流程记录表';

-- ----------------------------
-- Records of t_process
-- ----------------------------

-- ----------------------------
-- Table structure for t_process_file
-- ----------------------------
DROP TABLE IF EXISTS `t_process_file`;
CREATE TABLE `t_process_file` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` int(1) DEFAULT NULL COMMENT '文件类型（1：图片，2：视频，3：语音）',
  `URL` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '文件路径',
  `PROCESS_ID` int(11) DEFAULT NULL COMMENT '所属流程',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='流程关联文件表';

-- ----------------------------
-- Records of t_process_file
-- ----------------------------

-- ----------------------------
-- Table structure for t_process_node
-- ----------------------------
DROP TABLE IF EXISTS `t_process_node`;
CREATE TABLE `t_process_node` (
  `NODE_ID` int(2) NOT NULL COMMENT '流程节点ID',
  `NODE_NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '流程节点名称',
  `TYPE` int(1) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作节点表';

-- ----------------------------
-- Records of t_process_node
-- ----------------------------
INSERT INTO `t_process_node` VALUES ('1', '申告', null);
INSERT INTO `t_process_node` VALUES ('2', '派单', null);
INSERT INTO `t_process_node` VALUES ('3', '接单', null);
INSERT INTO `t_process_node` VALUES ('4', '处理中', null);
INSERT INTO `t_process_node` VALUES ('5', '回单', null);
INSERT INTO `t_process_node` VALUES ('6', '关闭', null);
INSERT INTO `t_process_node` VALUES ('7', '挂单申请', null);
INSERT INTO `t_process_node` VALUES ('8', '挂单成功', null);
INSERT INTO `t_process_node` VALUES ('9', '退单申请', null);
INSERT INTO `t_process_node` VALUES ('10', '退单成功', null);
INSERT INTO `t_process_node` VALUES ('11', '汇报', null);
INSERT INTO `t_process_node` VALUES ('12', '挂单失败', null);
INSERT INTO `t_process_node` VALUES ('13', '退单失败', null);
INSERT INTO `t_process_node` VALUES ('14', '未修复', null);
INSERT INTO `t_process_node` VALUES ('15', '催单', null);
INSERT INTO `t_process_node` VALUES ('16', '解除挂单', null);

-- ----------------------------
-- Table structure for t_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket`;
CREATE TABLE `t_ticket` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE_KEY` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '受理类型（1：前端点位，2：监控中心，3：中心平台，4：支撑工单，5：咨询工单）',
  `TICKET_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '单号',
  `USER_ID` int(11) DEFAULT NULL COMMENT '录单人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '录单时间',
  `SUMMARY` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '摘要',
  `COMMENT` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '详细描述',
  `STATUS` int(1) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `SOURCE_ID` int(11) DEFAULT NULL,
  `POINT_ID` int(11) DEFAULT NULL COMMENT '故障点位',
  `PROJECT_ID` int(11) DEFAULT NULL COMMENT '所属项目',
  `CLIENT_PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '客户手机',
  `MESSAGE_SEND` int(1) DEFAULT NULL COMMENT '是否推送消息（0：否，1：是）',
  `HANDLE_HOUR` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '希望处理时间（小时）',
  `INTO_STORE` int(1) DEFAULT NULL COMMENT '是否加入知识库（0：否，1：是）',
  `ALERT_STATUS` int(1) DEFAULT NULL COMMENT '预警状态（0：未超时；1：已超时）',
  `ALERT_COMMENT` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '预警说明',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='工单表';

-- ----------------------------
-- Records of t_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for t_ticket_source
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket_source`;
CREATE TABLE `t_ticket_source` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CALL_TIME` datetime DEFAULT NULL,
  `CALL_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CONTACTOR` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CLIENT_NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `INDUSTRY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='工单来源表';

-- ----------------------------
-- Records of t_ticket_source
-- ----------------------------

-- ----------------------------
-- Table structure for t_ticket_user
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket_user`;
CREATE TABLE `t_ticket_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL COMMENT '接单维护人员',
  `DISPATCH_USER` int(11) DEFAULT NULL COMMENT '派单人',
  `DISPATCH_TIME` datetime DEFAULT NULL COMMENT '派单时间',
  `STATUS` int(1) DEFAULT NULL COMMENT '处理状态',
  `TICKET_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='工单派遣表';

-- ----------------------------
-- Records of t_ticket_user
-- ----------------------------

-- ----------------------------
-- Table structure for u_company
-- ----------------------------
DROP TABLE IF EXISTS `u_company`;
CREATE TABLE `u_company` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所在地区',
  `ADDRESS` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `CODE` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `CREATET_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `USER_NUM` int(11) DEFAULT NULL COMMENT '员工数量',
  `SERIAL_CODE` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '机构编码',
  `PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工公司表';

-- ----------------------------
-- Records of u_company
-- ----------------------------

-- ----------------------------
-- Table structure for u_function
-- ----------------------------
DROP TABLE IF EXISTS `u_function`;
CREATE TABLE `u_function` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '功能名称',
  `URL` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '访问地址',
  `SORT` int(11) DEFAULT NULL COMMENT '排序号',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中；1：已删除）',
  `SERIAL_CODE` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '功能编码',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '父功能',
  `CSS_CODE` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '功能图标样式',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='功能模块表';

-- ----------------------------
-- Records of u_function
-- ----------------------------
INSERT INTO `u_function` VALUES ('1', '个人工作台', '', '1', '0', null, '0', null);
INSERT INTO `u_function` VALUES ('2', '员工管理', null, '2', '0', null, '0', null);
INSERT INTO `u_function` VALUES ('3', '项目管理', null, '3', '0', null, '0', null);
INSERT INTO `u_function` VALUES ('4', '客户管理', null, '4', '0', null, '0', null);
INSERT INTO `u_function` VALUES ('11', '今日工作', null, '1', '0', null, '1', null);
INSERT INTO `u_function` VALUES ('12', '新建工单', null, '1', '0', null, '1', null);
INSERT INTO `u_function` VALUES ('13', '业务受理', null, '1', '0', null, '1', null);
INSERT INTO `u_function` VALUES ('14', '查询&催单', null, '1', '0', null, '1', null);
INSERT INTO `u_function` VALUES ('15', '最新汇报', null, '1', '0', null, '1', null);
INSERT INTO `u_function` VALUES ('16', '报警事件', null, '1', '0', null, '1', null);
INSERT INTO `u_function` VALUES ('21', '员工管理', null, '1', '0', null, '2', null);
INSERT INTO `u_function` VALUES ('31', '区域管理', null, '1', '1', null, '3', null);
INSERT INTO `u_function` VALUES ('32', '项目管理', null, '1', '0', null, '3', null);
INSERT INTO `u_function` VALUES ('33', '设备管理', null, '1', '0', null, '3', null);
INSERT INTO `u_function` VALUES ('34', '配置', null, '1', '0', null, '3', null);
INSERT INTO `u_function` VALUES ('41', '客户管理', null, '1', '0', null, '4', null);

-- ----------------------------
-- Table structure for u_sign
-- ----------------------------
DROP TABLE IF EXISTS `u_sign`;
CREATE TABLE `u_sign` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL COMMENT '签到人',
  `SIGN_TIME` datetime DEFAULT NULL COMMENT '签到时间',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态（0：签入，1：签出）',
  `LOCATION_X` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'X坐标',
  `LOCATION_Y` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'Y坐标',
  `ADDRESS` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='签到记录表';

-- ----------------------------
-- Records of u_sign
-- ----------------------------

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '员工姓名',
  `COMPANY_ID` int(11) DEFAULT NULL COMMENT '所属公司',
  `SEX` int(1) DEFAULT NULL COMMENT '性别',
  `PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `EMAIL` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `POST` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '岗位（录入）',
  `ROLE_KEY` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '角色（1：服务台，2：维护人员，3：专家）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `FLAG` int(1) DEFAULT NULL COMMENT '删除标志（0：使用中，1：已删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `LOGO` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `ACCOUNT` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `SALT` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '加密作料',
  `SIGN_STATUS` int(1) DEFAULT NULL COMMENT '最新签到状态',
  `SIGN_TIME` datetime DEFAULT NULL COMMENT '最新签到时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工表';

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES ('1', '管理员', '1', '1', null, null, null, null, '2015-11-01 19:41:50', '1', '0', null, null, 'admin', '515a1ae3512f46e165d7bcaaa51bb04a', '66827e77e51f43ef5f117e49103fbc90', null, null);

-- ----------------------------
-- Table structure for u_user_function
-- ----------------------------
DROP TABLE IF EXISTS `u_user_function`;
CREATE TABLE `u_user_function` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL,
  `FUNCTION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户模块权限表';

-- ----------------------------
-- Records of u_user_function
-- ----------------------------
INSERT INTO `u_user_function` VALUES ('1', '1', '1');
INSERT INTO `u_user_function` VALUES ('2', '1', '2');
INSERT INTO `u_user_function` VALUES ('3', '1', '3');
INSERT INTO `u_user_function` VALUES ('4', '1', '4');

-- ----------------------------
-- Table structure for u_user_type
-- ----------------------------
DROP TABLE IF EXISTS `u_user_type`;
CREATE TABLE `u_user_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL,
  `MAINTAINANCE_KEY` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户维护工种表';

-- ----------------------------
-- Records of u_user_type
-- ----------------------------
