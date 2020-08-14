package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.MerchantInfo;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheEvict;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商户信息
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface MerchantDao {

    /**
     * 新增
     */
    @CacheEvict(keys = "mch_{#info.mchId}")
    int insert(@CacheParam("info") MerchantInfo info);


    /**
     * 更新
     */
    @CacheEvict(keys = "mch_{#info.mchId}",condition = "#info.mchKey!=null")
    int update(@CacheParam("info") MerchantInfo info);

    /**
     * 删除
     */
    @CacheEvict(keys = "mch_{#mchId}")
    int remove(@CacheParam("mchId") String mchId);

    /**
     * Load查询
     */
    @Cache(key = "mch_{#mchId}", ttl = Constants.MINUTES * 10)
    MerchantInfo load(@CacheParam("mchId") @Param("mchId") String mchId);

    /**
     * 获取商户列表
     *
     * @param info
     * @return
     */
    List<MerchantInfo> getList(MerchantInfo info);
}
