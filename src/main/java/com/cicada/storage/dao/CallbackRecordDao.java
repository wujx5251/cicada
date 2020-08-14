package com.cicada.storage.dao;

import com.cicada.storage.bean.CallbackRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 回调记录
 *
 * @author jinxiao.wu 2019-01-09
 */
@Repository
public interface CallbackRecordDao {

    /**
     * 新增
     */
    int insert(CallbackRecord callbackRecord);

    /**
     * 更新
     */
    int update(CallbackRecord callbackRecord);

    /**
     * Load查询
     */
    CallbackRecord load(@Param("id") int id);

}
