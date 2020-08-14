package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.RecordInfo;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheEvict;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交易流水
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface RecordInfoDao {

    /**
     * 新增
     */
    @CacheEvict(keys = {"record_{#info.mchId}_{#info.recordId}", "record_{#info.recordId}"})
    int insert(RecordInfo info);

    /**
     * 更新
     */
    @CacheEvict(keys = {"record_{#info.mchId}_{#info.recordId}", "record_{#info.recordId}"})
    int update(@CacheParam("info") RecordInfo info);

    /**
     * Load查询
     */
    @Cache(key = "record_{#mchId}_{#recordId}", ttl = Constants.MINUTES * 10)
    RecordInfo load(@CacheParam("recordId") @Param("recordId") String recordId,
                    @CacheParam("mchId") @Param("mchId") String mchId);

    /**
     * Load查询
     */
    @Cache(key = "record_{#recordId}", ttl = Constants.MINUTES * 10)
    RecordInfo loadById(@CacheParam("recordId") @Param("recordId") String recordId);

}
