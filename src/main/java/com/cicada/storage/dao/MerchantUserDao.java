package com.cicada.storage.dao;

import com.cicada.storage.bean.MerchantUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商户用户
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
@Repository
public interface MerchantUserDao {

    /**
     * 新增
     */
    int insert(MerchantUser info);

    /**
     * 删除
     */
    int delete(@Param("id") int id);

    /**
     * 更新
     */
    int update(MerchantUser info);

    /**
     * Load查询
     */
    MerchantUser load(@Param("id") int id);

}
