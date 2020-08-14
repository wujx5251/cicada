package com.cicada.storage;

import com.cicada.core.bean.TradingContext;
import com.cicada.storage.bean.CardBind;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.utils.crypto.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardBindService extends AbstractPersistService {

    public CardBind bind(TradingContext context) {
        CardBind bind = new CardBind();
        bind.setBindId(SecurityUtils.signMd5sm(context.getRecordId()));
        bind.setRecordId(context.getRecordId());
        bind.setCardId(context.getCard().getId());
        bind.setMchId(context.getApplication().getMchId());
        bind.setAppId(context.getApplication().getAppId());
        bind.setChannel(context.getChannel().value());
        bind.setToken(context.getCard().getToken());
        bind.setShortCardNo(PayUtils.getShortCardNo(context.getCard().getNo(), false));
        bind.setEncCardNo(DigestUtils.md5Hex(context.getCard().getNo()));
        bind.setCardType(context.getCard().getType().value());
        bind.setStatus(1);
        bind.setCreateTime(new Date());
        bind.setBindTime(bind.getCreateTime());
        bind.setLastRefreshTime(context.getCard().getRefreshDate());
        cardBindDao.insert(bind);
        return bind;
    }


}
