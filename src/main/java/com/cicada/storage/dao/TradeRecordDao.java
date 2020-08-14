package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.TradeRecord;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheEvict;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 交易记录（支付,退款记录）
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface TradeRecordDao {

    /**
     * 新增
     */
    int insert(TradeRecord info);

    /**
     * 更新
     */
    @CacheEvict(keys = {"order_{#info.recordId}", "detail_{#info.recordId}"})
    int update(@CacheParam("info") TradeRecord info);

    /**
     * Load查询
     */
    @Cache(key = "order_{#recordId}", ttl = Constants.MINUTES * 10)
    TradeRecord load(@CacheParam("recordId") @Param("recordId") String recordId);

    /**
     * 获取交易详情
     */
    @Cache(key = "detail_{#recordId}", ttl = Constants.MINUTES * 10)
    Map<?, ?> getDetail(@CacheParam("recordId") @Param("recordId") String recordId, @Param("tradeType") int tradeType);

    /**
     * 获取交易列表
     *
     * @param info
     * @return
     */
    List<?> getList(Map info);
}
