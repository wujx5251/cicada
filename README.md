# Cicada 交易系统

## 支付渠道支付通道接口
### 支付类接口

* trade.app.pay
* trade.wap.pay
* trade.qr.pay
* trade.scan.pay
* trade.web.pay
* trade.js.pay
* trade.quick.pay
* trade.auth.pay
* trade.auth.cfm.pay

### 交易结果查询接口

* trade.query
* trade.pay.query


### 交易撤销类接口

* trade.cancel
* trade.quick.cancel
* trade.auth.cancel
* trade.auth.cfm.cancel

```
 对外提供trade.cancel，支付系统根据订单溯源数据进行自适配
```

### 交易退款接口

trade.refund

### 无卡支付辅助接口

#### 短信发送接口
* trade.sms.send
* trade.bind.sms.send

```
支付系统根据不同渠道进行接口自适配，快钱开卡短信需要使用trade.bind.sms.send
```

#### 卡信息已经绑卡接口

* trade.card.info
* trade.card.bind
* trade.bind.query
* trade.card.unbind


#### 自动任务接口

* trade.close
* trade.refund.query
* trade.bill.down

## 支付渠道

|名称|描述|
|:---|:---|
|alipay|支付宝|
|wxpay|微信支付|
|unionpay|银联支付|
|kqpay|快钱支付|
|bdpay|百度钱包|
|jdpay|京东支付|
|qqpay|QQ钱包|
|yeepay|易宝支付|
|。。。|。。。|

## 支付产品

|名称|描述|
|:---|:---|
|app|app支付|
|h5|手机wap支付|
|web|桌面网页支付|
|qrcode|二维码支付（被扫）|
|scan|扫码支付（主扫）|
|jsapi|jsapi支付|
|quick|无卡快捷支付|
|auth|无卡预授权|
|auth_cfm|无卡预授权完成|

### 交易状态
|名称|描述|
|:---|:---|
|SUCCESS|交易成功|
|FAIL|交易失败|
|PENDING|待交易|
|CLOSED|交易关闭，全部退款|
|FINISHED|交易完结，超时不予许退款|

## 异步通知
### 触发通知类型
|通知类型|描述|默认开启|
|:---|:---|:---|
|SUCCESS|支付成功|是|
|FAIL|支付失败|是|
|CLOSED|交易关闭|否|
|FINISHED|交易完结|否|

### 通知参数
|参数|类型|是否必填|最大长度|描述|
|:---|:---|:---|:---|:---|
|method|String|是|32|接口名称|
|version|String|是|6|接口版本|
|timestamp|Integer|是|15|服务响应时间戳|
|channelId|String|是|15|交易类型，详见：[支付渠道](#支付渠道)|
|recordId|String|是|32|交易流水号|
|replyId|String|是|32|渠道交易流水号|
|orderId|String|是|32|业务单号|
|orderTime|DateTime|是|19|订单时间，格式：yyyy-MM-dd HH:mm:ss|
|amount|Integer|是| |交易金额|
|status|String|是|10|交易状态,详见：[交易状态](#交易状态)|
|resultMsg|String|否| |交易信息描述|
|reqReserved|String|否|2048|用户回送数据|
|sign|String|是|48|签名信息,详见：[签名规则](#签名规则)|

### [通知应答](#通知应答规则)

## 交易规则

### 交易取消

* 仅允许为当天发生的交易
* 面向无卡支付交易（支付宝、微信请使用退货）

### 交易退货

* 无卡支付须在付款完成后30天内、支付宝为3个月、微信为1年
* 面向全渠道支付类交易

### 交易查询

* 银联只允许查询30天内交易
* 面向全渠道支付类交易
* 支付无卡支付取消、退货，支付宝、微信自动适配走退货查询接口

### 签名规则

* 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
* 第二步，在stringA最后拼接上mchKey得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为小写
* 特别注意以下重要规则：
  - 参数名ASCII码从小到大排序（字典序）；
  - 如果参数的值为空不参与签名；
  - 参数名区分大小写；
   
### 通知应答规则

* 程序执行完后必须响应 http 200 code
* 程序执行完后必须打印输出“success”（不包含引号）。如果商户反馈的字符不是success这7个字符，服务器会不断重发通知，直到超过24小时22分钟。一般情况下，25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）；


## 业务错误码

|错误码|错误描述|解决方案|
|:---|:---|:---|
|A.1.2|请求方式错误|详见接口|
|A.1.4|数据格式错误|之处JSON、XML|
|A.1.5|时间戳错误|检查服务器时间是否同步|
|A.1.6|内容类型错误|支持json、urlencoded、multipart|
|A.1.7|编码格式不支持|详见接口文档|
|A.1.9|系统错误|联系管理员|
|A.1.10|没有权限|联系管理员进行白名单放行|
|A.2.1|接口不存在| |
|A.2.8|入参错误|检查数据格式、长度、类型、必填|
|B.1.INVALID_MCH|不合法的mchid|商户不存在或者禁用，联系管理员|
|B.1.INVALID_APP|不合法的appid|appid不存在或者禁用，联系管理员|
|B.1.NOT_FOUND_CARD|银行卡绑卡信息不存在|核实绑卡id是否正确|
|B.1.INVALID_CARD_TYPE|银行卡类型错误|核实银行卡类型是否为借记卡或贷记卡（01\|02）|
|B.1.INVALID_CERT_TYPE|证件类型不合法|详见接口证件类型描述|
|B.1.INVALID_CARD|银行卡信息不符合|卡号错误或者卡类型错误|
|B.1.INVALID_CHANNEL|不合法的channelId|渠道id上送错误|
|B.1.UNKNOW_CHANNEL_CONFIG|渠道配置信息获取失败|核实是否进行支付产品配置|
|B.1.INVALID_CURRENCY|不合法的货币类型| |
|B.1.INVALID_PARAM|参数不合法|见返回错误说明|
|B.1.INVALID_SIGN|签名错误| |
|B.1.INVALID_CHN_SIGN|渠道数据签名错误| |
|B.1.INVALID_AMOUNT|不合法的交易金额|金额超过允许支付或退款的范围|
|B.1.INVALID_STATUS|不合法的交易状态|交易记录状态状态错误或者禁止进行操作|
|B.1.NOT_SUPPORT|不支持的类型|见返回错误说明|
|B.1.NOT_RECORD|未知的交易记录|核查上传的recordId是否正确|
|B.1.EXPIRED|交易已过期|重新发起支付|
|B.2.NOT_COMPONET|未知的支付组件|联系管理员|
|B.2.CONN_ERR|请求渠道网络连接异常|重试请求|
|B.2.READ_ERR|请求渠道网络数据请求超时异常|重试请求|
|B.2.REQ_ERR|请求渠道网络异常|重试请求|
|B.2.BUSY|系统处理繁忙|重试请求|


## 支持银行码表

|银行简码|银行名称|
|:---|:---|
|ICBC|工商银行|
|ABC|农业银行|
|BOC|中国银行|
|CCB|建设银行|
|CMB|招商银行|
|BCOM|交通银行|
|SPDB|浦发银行|
|CITIC|中信银行|
|CIB|兴业银行|
|CMBC|民生银行|
|PAB|平安银行|
|GDB|广发银行|
|SHB|上海银行|
|CEB|光大银行|
|HXB|华夏银行|
|PSBC|中国邮政储蓄银行|
|JSB|江苏银行|
|NBCB|宁波银行|
|DLB|大连银行|
|SRCB|上海农商行|
|WZB|温州银行|
|JJB|九江银行|
|NCB|南昌银行|
|IMB|内蒙古银行|
|ORDOSB|鄂尔多斯银行|
|BSB|包商银行|
|JZB|锦州银行|
|QLB|齐鲁银行|
|WHCCB|威海市商业银行|
|WFB|潍坊银行|
|HBBB|湖北银行|
|CDB|承德银行|
|HZB|杭州银行|
|TZB|台州银行|
|LZB|兰州银行|
|SRB|上饶银行|
|HRBB|哈尔滨银行|
|LJB|龙江银行|
|GZCB|广州银行|
|HSB|徽商银行|
|NXB|宁夏银行|
|DYB|东营银行|
|NJCB|南京银行|
|GYB|贵阳银行|
|QHB|青海银行|
|YZB|鄞州银行|
|CSB|长沙银行|
|CQB|重庆银行|
|HBB|河北银行|
|CDRCBB|成都农商|
|ZJTLCB|泰隆商行|
|SXRCB|绍兴银行|
|ZJCZCB|稠州商行|
|ZJRCC|浙江农信|
|HZRCB|湖州商行|
|ZJMTCB|民泰商行|
|JHRCB|金华银行|
|GZRCB|赣州银行|
|CRB|广州农商行|
|DGB|东莞银行|
|DGRCB|东莞农商行|
|HYB|华润银行|
|NYB|南粤银行|
|SDRCB|顺德农商行|
|SZSB|石嘴山银行|
|SJB|盛京银行|
|LSSRCB|临商银行|
|DZB|德州银行|
|LSB|莱商银行|
|RZRCB|日照银行|
|QSRCB|齐商银行|
|BJRCB|北京农商行|
|CSRCB|常熟农商行|
|JYRCB|江阴农商行|
|JSRCU|江苏农信|
|WXRCB|无锡农商行|
|WJRCB|吴江农商行|
|FJRCC|福建农信|
|GXBBWB|广西北部湾|
|QHRCC|青海农信|
|HNRCC|湖南农信|
|QDRCB|青岛银行|
|SQB|商丘银行|
|HKB|汉口银行|
|JLRCB|吉林银行|
|CQRCB|重庆农商|
|TJRCB|天津农商|
|TJB|天津银行|
|YNRCC|云南农信|
|FDB|富滇银行|
|SXRCC|山西省联社|
|BOCD|成都银行|

## 缺陷（待完善功能）

* 回调业务处理失败时进行重试
* 支付接口业务唯一标识校验
* 后台管理功能完善