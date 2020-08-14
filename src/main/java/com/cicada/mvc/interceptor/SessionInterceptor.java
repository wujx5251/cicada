package com.cicada.mvc.interceptor;

import com.cicada.storage.bean.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器-验证用户session
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse rsp, Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler)// 放行静态资源
            return true;

        UserInfo user = (UserInfo) req.getSession().getAttribute("user");
        if (null == user) {
            //rsp.sendRedirect("login");
            String requestType = req.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
                rsp.setHeader("sessionstatus", "timeout");
                rsp.setStatus(508);
            } else {
                PrintWriter out = rsp.getWriter();
                out.println("<script>");
                out.println("top.location.href='login';");
                out.println("</script>");
            }

            return false;
        }
        return true;
    }

}