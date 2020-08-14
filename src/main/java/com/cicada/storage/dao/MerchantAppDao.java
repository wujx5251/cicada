package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.MerchantApp;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheEvict;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 支付app
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface MerchantAppDao {

    /**
     * 新增
     */
    @CacheEvict(keys = "app_{#info.appId}")
    int insert(@CacheParam("info") MerchantApp info);

    /**
     * 删除
     */
    @CacheEvict(keys = "app_{#appId}")
    int remove(@CacheParam("appId") String appId);

    /**
     * 更新
     */
    int update(MerchantApp info);

    /**
     * Load查询
     */
    @Cache(key = "app_{#appId}", ttl = Constants.MINUTES * 10)
    MerchantApp load(@CacheParam("appId") @Param("appId") String appId);

    /**
     * 获取应用列表
     *
     * @param info
     * @return
     */
    List<MerchantApp> getList(MerchantApp info);
}
