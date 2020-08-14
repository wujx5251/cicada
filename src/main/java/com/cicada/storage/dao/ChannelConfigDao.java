package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.ChannelConfig;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 支付通道配置
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface ChannelConfigDao {

    /**
     * 新增
     */
    int insert(ChannelConfig info);

    /**
     * 删除
     */
    int delete(@Param("id") int id);

    /**
     * 更新
     */
    int update(ChannelConfig info);

    /**
     * Load查询
     */
    @Cache(key = "config_{#info.appId}_{#info.channel}_{#info.product}", ttl = Constants.MINUTES * 10)
    ChannelConfig load(@CacheParam("info") ChannelConfig info);

}
