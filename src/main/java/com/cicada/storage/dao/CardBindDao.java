package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.CardBind;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheParam;
import org.springframework.stereotype.Repository;

/**
 * 绑卡列表
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface CardBindDao {

    /**
     * 新增
     */
    int insert(CardBind info);

    /**
     * 更新
     */
    int update(CardBind info);

    /**
     * Load查询
     */
    @Cache(key = "bind_{#info.appId}_{#info.bindId}", ttl = Constants.MINUTES * 10)
    CardBind load(@CacheParam("info") CardBind info);

}
