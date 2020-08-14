package com.cicada.storage.dao;

import com.cicada.storage.bean.CallbackDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 回调记录详情
 *
 * @author jinxiao.wu 2019-01-09
 */
@Repository
public interface CallbackDetailDao {

    /**
     * 新增
     */
    int insert(CallbackDetail callbackDetail);

    /**
     * Load查询
     */
    List<CallbackDetail> getList(@Param("id") int id);

}
