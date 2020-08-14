package com.cicada.mvc.controller;

import com.cicada.storage.bean.MerchantApp;
import com.cicada.storage.bean.ResultVo;
import com.cicada.storage.dao.MerchantAppDao;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.dao.paging.Page;
import com.dotin.dao.paging.PageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用管理
 */
@Controller
@RequestMapping("/application")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private MerchantAppDao appDao;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo doSearch(MerchantApp info) {
        PageContext.get().setPagination(true);
        try {
            Page pg = PageContext.get().setList(appDao.getList(info));
            return new ResultVo(pg);
        } catch (Exception e) {
            logger.error("加载应用列表错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultVo save(MerchantApp info, HttpServletRequest req) {
        if (StringUtils.isBlank(info.getName())) {
            return new ResultVo(ResultVo.FAIL_CODE, "应用名称不允许为空！");
        }
        if (StringUtils.isBlank(info.getMchId())) {
            return new ResultVo(ResultVo.FAIL_CODE, "应用编码不允许为空！");
        }
        try {
            info.setCreateBy((String) req.getSession().getAttribute("uname"));
            info.setCreateTime(DateUtils.now());
            info.setStatus(1);
            int ret = appDao.insert(info);
            return (ret > 0) ? ResultVo.SUCCESS : ResultVo.FAIL;
        } catch (DuplicateKeyException e) {
            return new ResultVo(ResultVo.FAIL_CODE, "应用编码已存在，请更换后重试！");
        } catch (Exception e) {
            logger.error("应用创建错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultVo update(MerchantApp info, HttpServletRequest req) {
        if (null == info.getAppId()) {
            return new ResultVo(ResultVo.FAIL_CODE, "应用编号不允许为空！");
        }
        if (StringUtils.isBlank(info.getName())) {
            return new ResultVo(ResultVo.FAIL_CODE, "应用名称不允许为空！");
        }
        try {
            info.setModifyBy((String) req.getSession().getAttribute("uname"));
            info.setModifyTime(DateUtils.now());
            int ret = appDao.update(info);
            return (ret > 0) ? ResultVo.SUCCESS : ResultVo.FAIL;
        } catch (DuplicateKeyException e) {
            return new ResultVo(ResultVo.FAIL_CODE, "应用名称已存在，请更换后重试！");
        } catch (Exception e) {
            logger.error("应用更新错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ResultVo remove(String appId) {
        try {
            int ret = appDao.remove(appId);
            return (ret > 0) ? ResultVo.SUCCESS : ResultVo.FAIL;
        } catch (Exception e) {
            logger.error("服务器内部错误，应用删除失败", e);
            return ResultVo.FAIL;
        }
    }


}
