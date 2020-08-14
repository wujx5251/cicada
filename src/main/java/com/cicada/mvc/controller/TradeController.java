package com.cicada.mvc.controller;

import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ProductType;
import com.cicada.storage.bean.ResultVo;
import com.cicada.storage.dao.TradeRecordDao;
import com.cicada.utils.PayUtils;
import com.dotin.dao.paging.Page;
import com.dotin.dao.paging.PageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 付款管理
 */
@Controller
@RequestMapping("/trade")
public class TradeController {

    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeRecordDao tradeDao;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo doSearch(HttpServletRequest req) {
        PageContext.get().setPagination(true);

        try {
            Page pg = PageContext.get().setList(tradeDao.getList(PayUtils.parseParams(req)));
            return new ResultVo(pg);
        } catch (Exception e) {
            logger.error("加载商户列表错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo detail(String recordId) {
        try {
            //tradeDao.getList(recordId)
            return new ResultVo();
        } catch (Exception e) {
            logger.error("加载商户列表错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping(value = "/getChannels", method = RequestMethod.GET)
    @ResponseBody
    public String getChannels() {
        return Channel.toJson();
    }

    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    @ResponseBody
    public String getProducts() {
        return ProductType.toJson();
    }

}
