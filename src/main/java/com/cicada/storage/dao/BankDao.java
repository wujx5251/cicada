package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.BankInfo;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 银行支持列表
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface BankDao {

    /**
     * 新增
     */
    int insert(BankInfo info);

    /**
     * 删除
     */
    int delete(@Param("id") int id);

    /**
     * 更新
     */
    int update(BankInfo info);

    /**
     * Load查询
     */
    BankInfo load(@Param("id") int id);

    /**
     * getList查询
     */
    @Cache(key = "bank_list_{#info.channel}_{#info.cardType}", ttl = Constants.DAY)
    List<BankInfo> getList(@CacheParam("info") BankInfo info);

}
