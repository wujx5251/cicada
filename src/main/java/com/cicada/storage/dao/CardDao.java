package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.CardInfo;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 银行卡信息
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface CardDao {

    /**
     * 新增
     */
    int insert(CardInfo info);

    /**
     * 更新
     */
    int update(CardInfo info);

    /**
     * Load查询
     */
    @Cache(key = "card_{#recordId}", ttl = Constants.MINUTES * 10)
    CardInfo load(@CacheParam("recordId") @Param("recordId") String recordId);


}