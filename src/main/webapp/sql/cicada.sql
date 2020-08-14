create table t_bank_list (
  id bigint unsigned not null auto_increment comment '数据库主键',
  bank_name varchar(64) not null comment '银行名称',
  bank_code varchar(32) not null comment '银行代码',
  bank_initial varchar(2) not null comment '银行首字母',
  card_type varchar(4) not null comment '支持卡片类型 00：全部，01：贷记卡，02：借记卡',
  icon_url varchar(256) comment '图标url',
  icon_base64 text comment '图标base64编码',
  level smallint(2) default '0' comment '优先级:1-9,数字大优先级高',
  channel varchar(15) not null comment '所属渠道，见channel枚举',
  pay_type smallint(2) not null comment '支付类型支持，0：全部，1：消费，:2付款，3：收款',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  modify_time datetime comment '修改时间',
  modify_by varchar(64) comment '修建人',
  primary key (id),
  key idx_pay_type (pay_type),
  key idx_bank_initial (bank_initial),
  unique key uniq_card_no (channel,bank_code,card_type)
) engine=innodb comment='银行支持列表';

create table t_card_info (
  id bigint unsigned not null auto_increment comment 'id',
  record_id varchar(32) not null comment '交易平台流水号',
  card_no varchar(128) not null comment '卡号',
  holder_name varchar(128) not null comment '持卡人姓名',
  bank_code varchar(15) comment '发卡行简码',
  bank_name varchar(32) comment '发卡行名称',
  card_type varchar(4) not null comment '银行卡类型：01:信用卡,02:借记卡',
  expired_year varchar(128) comment '过期年',
  expired_month varchar(128) comment '过期月',
  certification_type varchar(4) not null comment '证件类型：01：身份证 02：军官证 03：护照 04：回乡证 05：台胞证 06：警官证 07：士兵证 99：其它证件',
  certification_no varchar(128) not null comment '证件编号',
  cvv2 varchar(128) comment 'cvv',
  mobile_no varchar(128) not null comment '银行卡绑定的手机号',
  salt varchar(32) not null comment 'salt',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  modify_time datetime comment '修改时间',
  modify_by varchar(64) comment '修建人',
  primary key (id),
  unique key uniq_record_id (record_id)
) engine=innodb comment='银行卡信息';

create table t_card_bind (
  id bigint unsigned not null auto_increment comment 'id',
  bind_id varchar(64) not null comment '绑定id',
  record_id varchar(32) not null comment '交易平台流水号',
  card_id bigint not null comment '卡信息id',
  mch_id varchar(64) not null comment '所属商户',
  app_id varchar(64) not null comment '应用id',
  channel varchar(15) comment '所属渠道，见channel枚举',
  token varchar(64) comment '渠道绑卡标记',
  short_card_no varchar(32) not null comment '短卡号，前六后四中间*代替',
  enc_card_no varchar(64) not null comment '加密卡号MD5',
  card_type varchar(4) not null comment '银行卡类型：01:信用卡,02:借记卡',
  status smallint(2) not null default 2 comment '状态 1绑定，2待绑定，0已解绑',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  bind_time datetime comment '绑定时间',
  last_refresh_time datetime comment '刷新时间',
  primary key (id),
  key idx_card_no(enc_card_no),
  unique key uniq_record_id (record_id),
  unique key uniq_bind_id (bind_id)
) engine=innodb comment='绑卡列表';

create table t_merchant_info (
  id bigint unsigned not null auto_increment comment 'id',
  name varchar(255) not null comment '商户名称',
  icon varchar(128) comment '商户图标',
  mch_id varchar(64) not null comment '商户编号',
  mch_key varchar(128) comment '商户密钥',
  status smallint(2) not null default 1 comment '商户状态0已禁用1正常2待审批',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  modify_time datetime comment '修改时间',
  modify_by varchar(64) comment '修改人',
  primary key (id),
  unique key uniq_mch_id (mch_id)
) engine=innodb comment='商户信息';

create table t_merchant_user (
  id bigint unsigned not null auto_increment comment 'id',
  mch_id varchar(64) not null comment '商户编号',
  login_id varchar(64) not null comment '登录名',
  password varchar(64) not null comment '口令',
  email varchar(64) comment 'email',
  mobile_no varchar(32) comment '手机号码',
  role smallint(2) not null comment '1master，2auditor',
  status smallint(2) not null default 1 comment '商户状态0已禁用1正常',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  modify_time datetime comment '修改时间',
  modify_by varchar(64) comment '修建人',
  primary key (id),
  unique key uniq_mch_login (mch_id, login_id)
) engine=innodb comment='商户用户';

create table t_merchant_app (
  id bigint unsigned not null auto_increment comment 'id',
  mch_id varchar(64) not null comment '商户编号',
  name varchar(64) not null comment '应用名',
  icon varchar(64) comment '应用图标',
  app_id varchar(64) not null comment '应用id',
  status smallint(2) not null default 1 comment '状态 0已禁用1正常',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  modify_time datetime comment '修改时间',
  modify_by varchar(64) comment '修建人',
  primary key (id),
  unique key uniq_app_key (app_id)
) engine=innodb comment='支付app';

create table t_channel_config (
  id bigint unsigned not null auto_increment comment 'id',
  app_id varchar(64) comment '应用id',
  channel varchar(15) not null comment '所属渠道，见channel枚举',
  product varchar(15) not null comment '所属支付产品，见producttype枚举',
  salt varchar(32) not null comment 'salt',
  create_time datetime not null comment '创建时间',
  create_by varchar(64) comment '创建人',
  modify_time datetime comment '修改时间',
  modify_by varchar(64) comment '修建人',
  primary key (id),
  unique key uniq_config_key (app_id,channel,product)
) engine=innodb comment='支付通道配置';

create table t_record(
	id bigint unsigned not null auto_increment comment 'id',
	record_id varchar(32) not null comment '交易平台流水号',
	target_record_id varchar(32) comment '目标交易流水号', 
	reply_id varchar(64) comment '三方流水号',
	method varchar(64) not null comment 'api名称',
	version varchar(8) not null comment 'api版本',
	mch_id varchar(64) not null comment '商户id',
	app_id varchar(64) not null comment '应用id',
	channel varchar(15) comment '所属渠道，见channel枚举',
	product varchar(15) comment '所属支付产品，见producttype枚举',
	c_mch_id varchar(64) not null comment '渠道商户id',
	c_app_id varchar(64) not null comment '渠道应用id',
	amount int unsigned comment '交易金额',
	bind_id varchar(64) comment '无卡消费所需的绑卡id',
	result_status smallint(2) not null default '2' comment '结果编码',
	result_msg varchar(256) comment '结果响应信息',
	request_time datetime not null comment '请求时间',
	response_time datetime comment '响应时间',
	channel_request_time datetime comment '渠道请求时间',
  channel_response_time datetime comment '渠道响应时间',
  extra_data varchar(256) comment '扩展数据，存放渠道返回的关键数据json格式',
	server_ip varchar(64) comment '服务端ip',
	primary key (id),
	unique key uniq_record_id (record_id),
	key idx_req_rsp_time (request_time,response_time),
	key idx_mch_id (mch_id),	
	key idx_app_id (app_id),
	key idx_cmch_id (c_mch_id),
	key idx_capp_id (c_app_id),
	key idx_reply_id (reply_id),
	key idx_method (method,version)
) engine=innodb comment='交易流水';

create table t_trade_record (
  id bigint unsigned not null auto_increment comment 'id',
  record_id varchar(32) not null comment '交易平台流水号',
  order_id varchar(64) not null comment '业务单号',
  order_time datetime not null comment '订单时间',
  subject varchar(256) not null comment '订单标题',
  body varchar(256) comment '订单描述',
  status smallint(2) not null default 2 comment '交易状态,0:失败，1：成功，2：待交易',
  amount int unsigned not null default 0 comment '交易金额',
  handling_fee int unsigned not null default 0 comment '交易手续费',
  refund_amount int unsigned not null default 0 comment '退款金额',
  currency varchar(8) default 'cny' comment '货币',
  bind_id varchar(64) comment '无卡消费所需的绑卡id',
  notify_url varchar(256) comment '回调通知地址',
  return_url varchar(256) comment '交易完成跳转地址',
  quit_url varchar(256) comment '回退地址', 
  client_ip varchar(64) comment '客户端ip',
  trade_type smallint(2) not null comment '交易类型1收款2付款3退款4撤销',
  pay_time datetime comment '付款时间', 
  expire_time datetime comment '订单失效时间',
  primary key (id),
  unique key uniq_record_id (record_id),
  key idx_pay_time (pay_time),
  key idx_expire_time (expire_time),
  key idx_order_id (order_id,order_time),
  key idx_bind_id (bind_id),
  key idx_status (status)
) engine=innodb comment='交易记录（支付，退款记录）';

create table t_record_detail (
  id bigint unsigned not null auto_increment comment 'id',
  record_id varchar(32) not null comment '交易平台流水号',
  request_time datetime not null comment '支付请求时间',
  response_time datetime comment '支付响应时间',
  channel_request_time datetime comment '渠道请求时间',
  channel_response_time datetime comment '渠道响应时间',
  request_data text  comment '支付请求数据',
  response_data text comment '支付响应数据',
  channel_request_data text comment '渠道请求数据',
  channel_response_data text comment '渠道响应数据',
  req_reserved text comment '客户端保留数据',
  page_pay_data text comment '页面支付数据',
  primary key (id),
  unique key uniq_record_id (record_id)
) engine=innodb comment='交易明细';


create table t_callback_record (
  id bigint unsigned not null auto_increment comment 'id',
  record_id varchar(32) not null comment '交易平台流水号',
  callback_type int not null comment '回调类型1、主动，2、渠道，3、任务',
  status smallint(2) not null comment '交易状态0失败，1成功',
  finish smallint(2) not null default 2 comment '回调状态2未结束，1结束',
  agent_notify_time datetime comment '渠道回调时间',
	agent_notify_data text comment '渠道回调数据',
	agent_return_time datetime comment '渠道回调回复时间',
	agent_return_data text comment '渠道回调回复数据',
	callback_data text comment '回调数据',
  create_time datetime comment '回调记录创建时间',
  callback_url varchar(256) not null comment '回调地址',
  sign_key varchar(128) not null comment '签名key',
  key idx_record_id(record_id),
  key idx_create_time(create_time),
  primary key (id)
)engine = innodb comment='回调记录';


create table t_callback_detail (
  id bigint unsigned not null auto_increment comment 'id',
  callback_id bigint unsigned not null comment '回调记录id',
  callback_time datetime comment '回调时间',
  callback_return_time datetime comment '响应时间',
  callback_return_data text comment '响应内容',
  finish smallint(2) not null default 2 comment '回调状态2未结束，1结束',
  key idx_callback_id(callback_id),
  key idx_time(callback_time,callback_return_time),
  primary key (id)
)engine = innodb comment='回调记录详情';


create table t_admin_user (
  id int unsigned not null auto_increment,
  login_name varchar(255) comment '登录用户名称',
  user_name varchar(255) comment '用户名称',
  type smallint(6) not null default '0' comment '管理员类型 0 普通用户，1超级管理员',
  mail varchar(255) comment '登录用户邮箱',
  create_time bigint not null comment '添加时间',
  login_time bigint comment '最近登录时间',
  status tinyint(1)  not null default '1' comment '状态，0删除，1正常',
  primary key (id),
  unique key idx_name(login_name)
) engine=innodb comment='管理员表';

create table t_user_group_relation (
  id bigint unsigned not null auto_increment,
  user_id int not null comment '管理用户表id',
  group_id bigint not null comment '项目id',
  permission smallint(6) not null default '1' comment '权限（1代表读写，0代表只读）',
  status tinyint(1)  not null default '1' comment '状态，0删除，1正常',
  primary key (id)
) engine=innodb comment='用户应用关联表';

