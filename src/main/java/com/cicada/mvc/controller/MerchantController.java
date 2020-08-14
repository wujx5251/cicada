package com.cicada.mvc.controller;

import com.cicada.core.generate.Generator;
import com.cicada.storage.bean.MerchantInfo;
import com.cicada.storage.bean.ResultVo;
import com.cicada.storage.dao.MerchantDao;
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
 * 商户管理
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private Generator generator;
    
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ResponseBody
    public ResultVo doSearch(MerchantInfo info) {
        PageContext.get().setPagination(true);
        try {
            Page pg = PageContext.get().setList(merchantDao.getList(info));
            return new ResultVo(pg);
        } catch (Exception e) {
            logger.error("加载商户列表错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultVo save(MerchantInfo info, HttpServletRequest req) {
        if (StringUtils.isBlank(info.getName())) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户名称不允许为空！");
        }
        if (StringUtils.isBlank(info.getMchId())) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户编码不允许为空！");
        }
        try {
            info.setCreateBy((String) req.getSession().getAttribute("uname"));
            info.setCreateTime(DateUtils.now());
            info.setMchKey(generator.generateSalt(24));
            info.setStatus(1);
            int ret = merchantDao.insert(info);
            return (ret > 0) ? ResultVo.SUCCESS : ResultVo.FAIL;
        } catch (DuplicateKeyException e) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户编码已存在，请更换后重试！");
        } catch (Exception e) {
            logger.error("商户创建错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultVo update(MerchantInfo info, HttpServletRequest req) {
        if (null == info.getMchId()) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户编号不允许为空！");
        }
        if (StringUtils.isBlank(info.getName())) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户名称不允许为空！");
        }
        try {
            info.setModifyBy((String) req.getSession().getAttribute("uname"));
            info.setModifyTime(DateUtils.now());
            int ret = merchantDao.update(info);
            return (ret > 0) ? ResultVo.SUCCESS : ResultVo.FAIL;
        } catch (DuplicateKeyException e) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户名称已存在，请更换后重试！");
        } catch (Exception e) {
            logger.error("商户更新错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/refresh")
    @ResponseBody
    public ResultVo refresh(MerchantInfo info, HttpServletRequest req) {
        if (null == info.getMchId()) {
            return new ResultVo(ResultVo.FAIL_CODE, "商户编号不允许为空！");
        }
        try {
            info.setModifyBy((String) req.getSession().getAttribute("uname"));
            info.setModifyTime(DateUtils.now());
            info.setMchKey(generator.generateSalt(24));
            int ret = merchantDao.update(info);
            return (ret > 0) ? new ResultVo(info.getMchKey()) : ResultVo.FAIL;
        } catch (Exception e) {
            logger.error("商户密钥更新错误", e);
            return ResultVo.FAIL;
        }
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ResultVo remove(String mchId) {
        try {
            int ret = merchantDao.remove(mchId);
            return (ret > 0) ? ResultVo.SUCCESS : ResultVo.FAIL;
        } catch (Exception e) {
            logger.error("服务器内部错误，商户删除失败", e);
            return ResultVo.FAIL;
        }
    }


}
