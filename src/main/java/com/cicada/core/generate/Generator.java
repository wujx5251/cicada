package com.cicada.core.generate;

/**
 * 生成器
 *
 * @author: WUJXIAO
 * @create: 2018-12-26 08:58
 * @version: 1.0
 */
public interface Generator {

    /**
     * 生成支付记录编号
     *
     * @return 支付记录编号
     */
    String generateNo();

    /**
     * 生成一个新的盐
     *
     * @return 盐
     */
    String generateSalt(int size);

}
