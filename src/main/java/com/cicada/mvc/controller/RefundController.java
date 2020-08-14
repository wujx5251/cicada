package com.cicada.mvc.controller;

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
import java.util.Map;

/**
 * 退款款管理
 */
@Controller
@RequestMapping("/refund")
public class RefundController {

    private static final Logger logger = LoggerFactory.getLogger(RefundController.class);

    @Autowired
    private TradeRecordDao tradeDao;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo doSearch(HttpServletRequest req) {
        PageContext.get().setPagination(true);

        try {
            Map<String, Object> params = PayUtils.parseParams(req);
            params.put("tradeType", 2);
            Page pg = PageContext.get().setList(tradeDao.getList(params));
            return new ResultVo(pg);
        } catch (Exception e) {
            logger.error("加载退款列表错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo detail(String recordId) {
        try {
            return new ResultVo(tradeDao.getDetail(recordId, 2));
        } catch (Exception e) {
            logger.error("加载退款详情", e);
            return ResultVo.FAIL;
        }
    }


}
