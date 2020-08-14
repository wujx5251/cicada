package com.cicada.storage;

import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.CallbackStatus;
import com.cicada.core.enums.CallbackType;
import com.cicada.storage.bean.CallbackDetail;
import com.cicada.storage.bean.CallbackRecord;
import com.cicada.storage.dao.CallbackDetailDao;
import com.cicada.storage.dao.CallbackRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CallbackPersistService extends AbstractPersistService {

    @Autowired
    private CallbackRecordDao recordDao;
    @Autowired
    private CallbackDetailDao detailDao;

    /**
     * 保存通知信息并返回记录id
     *
     * @param context
     * @return
     */
    public long saveNotify(TradingContext context, CallbackType type) {
        CallbackRecord record = toCallBackRecord(context);
        record.setCallbackType(type.value());
        recordDao.insert(record);
        return record.getId();
    }

    /**
     * 保存回调信息
     *
     * @param detail
     * @return
     */
    @Transactional(noRollbackFor = {Throwable.class})
    public int saveCallback(CallbackDetail detail) {
        int rows = detailDao.insert(detail);
        if (CallbackStatus.SUCCESS.value().equals(detail.getFinish())) {
            CallbackRecord record = new CallbackRecord();
            record.setId(detail.getCallbackId());
            record.setFinish(detail.getFinish());
            recordDao.update(record);
        }
        return rows;
    }

}
