package com.cicada.core.bean;

/**
 * 参数说明<
 */
public interface TradingParameter {

    interface Order {

        /**
         * 订单 ID
         */
        String ID = "orderId";

        /**
         * 订单发生时间
         */
        String TIME = "orderTime";

        /**
         * 订单主题
         */
        String SUBJECT = "subject";

        /**
         * 订单详情
         */
        String BODY = "body";

        /**
         * 订单金额
         * <p>整型，实际金额乘以 100，例如 13.14 元，值为 1314；</p>
         */
        String AMOUNT = "amount";

        /**
         * 获取货币类型
         */
        String CURRENCY = "currency";

    }

    interface Card {

        /**
         * 绑定id，银行卡序列号（存储）
         */
        String BIND_ID = "bindId";

        /**
         * 银行卡类型
         */
        String TYPE = "cardType";

        /**
         * 绑卡 NO
         */
        String NO = "cardNo";

        /**
         * 信用卡有效期年
         */
        String CREDITCARD_EXPIRE_YEAR = "expireYear";

        /**
         * 信用卡有效期月
         */
        String CREDITCARD_EXPIRE_MONTH = "expireMonth";

        /**
         * 信用卡 CVV
         */
        String CREDITCARD_CVV = "cvv2";

        /**
         * 银行卡绑定手机号
         */
        String CARD_MOBILE_NO = "mobileNo";

        /**
         * 银行卡持卡人证件类型
         */
        String CERTIFICATION_TYPE = "certificationType";

        /**
         * 银行卡持卡人证件号码
         */
        String CERTIFICATION_NO = "certificationNo";

        /**
         * 银行卡持卡人姓名
         */
        String HOLDER_NAME = "holderName";

        /**
         * 银行卡机构码，银行简码
         */
        String ORGANIZATION_CODE = "bankCode";

    }

    /**
     * 接口名称
     */
    String METHOD = "method";

    /**
     * 接口版本
     */
    String VERSION = "version";

    /**
     * 应用程序 NO
     */
    String APP_ID = "appId";

    /**
     * 商户 NO
     */
    String MCH_ID = "mchId";

    /**
     * 授权id
     */
    String OPEN_ID = "openId";

    /**
     * 时间戳
     */
    String TIMESTAMP = "timestamp";

    /**
     * 交易状态
     */
    String STATUS = "status";

    /**
     * 交易状态信息
     */
    String RESULT_MSG = "resultMsg";

    /**
     * 请求数据签名
     */
    String SIGNATURE = "sign";

    /**
     * 渠道名
     */
    String CHANNEL = "channelId";

    /**
     * 支付流水号
     */
    String RECORD_ID = "recordId";

    /**
     * 渠道支付流水号
     */
    String REPLY_ID = "replyId";

    /**
     * 账单日期
     */
    String BILL_DATE = "billDate";

    /**
     * 授权码
     */
    String AUTH_CODE = "authCode";

    /**
     * 短信类型
     */
    String SMS_TYPE = "smsType";

    /**
     * 交易状态异步通知地址
     */
    String NOTIFY_URL = "notifyUrl";

    /**
     * 交易完成跳转回业务页面地址
     */
    String RETURN_URL = "returnUrl";

    /**
     * 取消支付跳转页面
     */
    String QUIT_URL = "quitUrl";

    /**
     * 客户端IP
     */
    String CLIENT_IP = "clientIp";

    /**
     * 客户设备信息
     */
    String DERVICE = "device";

    /**
     * 用户回送数据
     */
    String RESERVED = "reqReserved";

    /**
     * 回复
     */
    String SUCCESS = "SUCCESS";

}
