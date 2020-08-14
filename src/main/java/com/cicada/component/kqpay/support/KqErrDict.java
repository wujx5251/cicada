package com.cicada.component.kqpay.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 快钱-错误码字典
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public final class KqErrDict {

    private static final Map<String, String> ERR_MSG = new HashMap<String, String>();

    static {
        ERR_MSG.put("51", "卡余额不足，请换卡交易");
        ERR_MSG.put("I8", "金额超限或其他");
        ERR_MSG.put("T6", "验证码错误，请核对您的验证码信息或重新获取");
        ERR_MSG.put("01", "请联系发卡行，或核对卡信息后重新输入");
        ERR_MSG.put("62", "受限制的卡，请换卡重试");
        ERR_MSG.put("61", "超出取款转账金额限制，联系发卡行");
        ERR_MSG.put("OT", "交易金额太小");
        ERR_MSG.put("68", "无法在正常时间内获得交易应答，请稍后重试");
        ERR_MSG.put("54", "卡片已过期，请换卡后交易");
        ERR_MSG.put("OR", "EDC.外部跟踪编号重复");
        ERR_MSG.put("16", "金额超出限制");
        ERR_MSG.put("Y1", "身份认证失败");
        ERR_MSG.put("KJ", "交易失败");
        ERR_MSG.put("HW", "手机号码不符");
        ERR_MSG.put("96", "系统异常、失效，请联系快钱");
        ERR_MSG.put("91", "请稍候重新交易，或联系快钱");
        ERR_MSG.put("IA", "请提供正确的手机号");
        ERR_MSG.put("HU", "有效期不符");
        ERR_MSG.put("05", "不予承兑");
        ERR_MSG.put("BA", "卡信息错误次数超限，请联系发卡行");
        ERR_MSG.put("14", "无效卡号（无此号），请换卡重试");
        ERR_MSG.put("OG", "单笔或日限额超过上限，请联系快钱");
        ERR_MSG.put("20", "卡信息提供有误");
        ERR_MSG.put("T0", "验证码已失效，请重新获取");
        ERR_MSG.put("ZG", "请重新签约");
        ERR_MSG.put("CB", "银行系统异常、失效，请稍后重试");
        ERR_MSG.put("25", "未找到原始交易，请重新交易");
        ERR_MSG.put("R0", "交易不予承兑，请换卡重试");
        ERR_MSG.put("HZ", "证件号不符");
        ERR_MSG.put("I7", "CVV2 或有效期错");
        ERR_MSG.put("30", "卡片故障，请换卡重试");
        ERR_MSG.put("65", "超出取款/消费次数限制");
        ERR_MSG.put("A4", "授权.找不到授权终端");
        ERR_MSG.put("I4", "请提供正确的卡有效期，卡有效期是在卡号下面的 4 位数字");
        ERR_MSG.put("BB", "CVV 错误次数超限");
        ERR_MSG.put("W4", "姓名与开户时登记的不一致");
        ERR_MSG.put("02", "请联系快钱公司");
        ERR_MSG.put("92", "与银行通信网络故障，请稍后重试");
        ERR_MSG.put("G3", "超出系统当日金额限制");
        ERR_MSG.put("07", "特定条件下没收卡");
        ERR_MSG.put("80", "交易拒绝");
        ERR_MSG.put("KG", "卡状态、户口无效或不存在，拒绝交易对照");
        ERR_MSG.put("N9", "请持卡人重新进行卡信息验证");
        ERR_MSG.put("OB", "不受理的银行卡，请换卡重试");
        ERR_MSG.put("08", "请与银行联系");
        ERR_MSG.put("03", "无效商户");
        ERR_MSG.put("EQ", "未找到绑定关系");
        ERR_MSG.put("HI", "当天流水号重复");
        ERR_MSG.put("L8", "BIN.找不到路由");
        ERR_MSG.put("I2", "请提供正确的验证码（CVV2），验证码在卡背面签名栏后的三位数字串");
        ERR_MSG.put("41", "挂失卡");
        ERR_MSG.put("W6", "手机号、身份证号码、姓名与开户时登记的不一致");
        ERR_MSG.put("NH", "卡已锁");
        ERR_MSG.put("OQ", "EDC.销售日限额已经用完");
        ERR_MSG.put("B5", "系统维护中，请稍后再试");
        ERR_MSG.put("57", "不允许持卡人进行的交易");
        ERR_MSG.put("M2", "Merchant.商户状态不匹配");
        ERR_MSG.put("36", "受限制的卡");
        ERR_MSG.put("BC", "无效卡");
        ERR_MSG.put("CD", "卡状态异常或户名证件号不符");
        ERR_MSG.put("W6", "手机号、身份证号码、姓名与开户时登记的不一致");
        ERR_MSG.put("HW", "手机号码不符");
        ERR_MSG.put("T6", "验证码错误，请核对您的验证码信息或重新获取");
        ERR_MSG.put("01", "请联系发卡行，或核对卡信息后重新输入");
        ERR_MSG.put("HZ", "证件号不符");
        ERR_MSG.put("HX", "姓名不符");
        ERR_MSG.put("BA", "卡信息错误次数超限，请联系发卡行");
        ERR_MSG.put("62", "受限制的卡，请换卡重试");
        ERR_MSG.put("68", "无法在正常时间内获得交易应答，请稍后重试");
        ERR_MSG.put("02", "请联系快钱公司");
        ERR_MSG.put("L8", "BIN.找不到路由");
        ERR_MSG.put("HY", "证件类型不符");
        ERR_MSG.put("W0", "手机号与开户时登记的不一致");
        ERR_MSG.put("IA", "请提供正确的手机号");
        ERR_MSG.put("Y1", "身份认证失败");
        ERR_MSG.put("I1", "请提供正确的持卡人姓名");
        ERR_MSG.put("HU", "有效期不符");
        ERR_MSG.put("20", "卡信息提供有误");
        ERR_MSG.put("CB", "银行系统异常、失效，请稍后重试");
        ERR_MSG.put("I8", "金额超限或其他");
        ERR_MSG.put("MR", "商户不支持的卡类型");
        ERR_MSG.put("14", "无效卡号（无此号），请换卡重试");
        ERR_MSG.put("OB", "不受理的银行卡，请换卡重试");
        ERR_MSG.put("ZG", "请重新签约");
        ERR_MSG.put("T0", "验证码已失效，请重新获取");
        ERR_MSG.put("W4", "姓名与开户时登记的不一致");
        ERR_MSG.put("I3", "请提供正确的证件号码，必须与申请银行卡时的证件号码一致");
        ERR_MSG.put("I7", "CVV2 或有效期错");
        ERR_MSG.put("KJ", "交易失败");
        ERR_MSG.put("cc", "此卡未在银行预留绑定手机号，请联系发卡行");
        ERR_MSG.put("NH", "卡已锁");
        ERR_MSG.put("HV", "CVV2 不符");
        ERR_MSG.put("12", "无效交易，请联系快钱");
        ERR_MSG.put("OR", "EDC.外部跟踪编号重复");
        ERR_MSG.put("OS", "EDC.找不到对应的结算商户");
        ERR_MSG.put("I4", "请提供正确的卡有效期，卡有效期是在卡号下面的 4 位数字");
        ERR_MSG.put("04", "无效终端");
        ERR_MSG.put("I2", "请提供正确的验证码（CVV2），验证码在卡背面签名栏后的三位数字串");
        ERR_MSG.put("LG", "该银行卡未开通银联在线支付业务");
        ERR_MSG.put("08", "请与银行联系");
        ERR_MSG.put("AP", "不支持的证件类型/证件号");
        ERR_MSG.put("91", "请稍候重新交易，或联系快钱");
        ERR_MSG.put("Q2", "有效期错，请核实重输或联系发卡行");
        ERR_MSG.put("30", "卡片故障，请换卡重试");
        ERR_MSG.put("96", "系统异常、失效，请联系快钱");
        ERR_MSG.put("ZH", "商户不支持的短信验证模式");
        ERR_MSG.put("54", "卡片已过期，请换卡后交易");
        ERR_MSG.put("CD", "卡状态异常或户名证件号不符");
        ERR_MSG.put("M2", "Merchant.商户状态不匹配");
        ERR_MSG.put("53", "无此储蓄卡账户");
    }

    public static String getErrMsg(String code) {
        return ERR_MSG.get(code);
    }
}