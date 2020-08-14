package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.RecordDetail;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交易明细
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface RecordDetailDao {

    /**
     * 新增
     */
    int insert(RecordDetail info);

    /**
     * 更新
     */
    int update(RecordDetail info);

    /**
     * Load查询
     */
    @Cache(key = "pay_data_{#recordId}", ttl = Constants.MINUTES * 10)
    RecordDetail load(@CacheParam("recordId") @Param("recordId") String recordId);

    @Cache(key = "pay_{#recordId}", ttl = Constants.MINUTES * 10)
    RecordDetail loadPayInfo(@CacheParam("recordId") @Param("recordId") String recordId);
}
