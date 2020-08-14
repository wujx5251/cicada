package com.cicada.core.exporter;

import com.cicada.core.Pay;
import com.cicada.core.bean.Response;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.storage.bean.BankInfo;
import com.cicada.storage.dao.BankDao;
import com.cool.core.annotation.ApiMethod;
import com.cool.core.annotation.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付服务
 */
@ApiService(namespace = "trade")
public class DefaultPayExporter extends AbstractPayExporter {

    @Autowired
    private BankDao bankDao;

    @ApiMethod("bank.list")
    public Object getBankList(BankInfo info) {
        return bankDao.getList(info);
    }

    @Override
    public Object doHandle(TradingContext context, Pay component) {
        try {
            tryLock(context.getProductType(), context.getRecordId());
            TradingResult result = component.doPay(context);
            context.setResult(result);
            if (result.getData() instanceof Response) {//扩展数据
                context.getMessage().setExtraData(((Response) result.getData()).getExtra());
            }
            return result;
        } finally {
            unlock(context.getProductType(), context.getRecordId());
        }
    }
}
