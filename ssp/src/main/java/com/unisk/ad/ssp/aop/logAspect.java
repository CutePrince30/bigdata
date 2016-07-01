package com.unisk.ad.ssp.aop;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.model.Log;
import com.unisk.ad.ssp.util.JsonUtils;
import com.unisk.ad.ssp.util.LogUtils;
import com.unisk.ad.ssp.util.UrlUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 日志记录切面
 *
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Aspect
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger("syslog");

    public static final String SSP_PULL = "ssp_pull";
    public static final String SSP_SHOW = "ssp_show";
    public static final String SSP_CLICK = "ssp_click";

    public static final String SOURCE = "ssp";
    public static final String VERSION = "1";

    @AfterReturning("execution(* com.unisk.ad.ssp.controller.app.*Controller.show(..))")
    public void afterAppShow(JoinPoint point) {
        Object[] args = point.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        final String ip = request.getRemoteHost();
        final String zoneid = request.getParameter("zoneid");
        final String pushid = request.getParameter("pushid");
        final String mediaName = request.getParameter("appname");

        new Thread() {
            @Override
            public void run() {
                Log log = new Log(SSP_SHOW, VERSION, SOURCE, null, new Date(),
                        null, zoneid, pushid, ip, null, null, null, mediaName);
                String log_str = LogUtils.genarateLogLine(log);
                logger.info(log_str);
            }
        }.start();
    }

    @AfterReturning("execution(* com.unisk.ad.ssp.controller.web.*Controller.show(..))")
    public void afterWebShow(JoinPoint point) {
        Object[] args = point.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        final String ip = request.getRemoteHost();
        final String zoneid = request.getParameter("zoneid");
        final String pushid = request.getParameter("pushid");
        final String mediaName = request.getParameter("site_name");

        new Thread() {
            @Override
            public void run() {
                Log log = new Log(SSP_SHOW, VERSION, SOURCE, null, new Date(),
                        null, zoneid, pushid, ip, null, null, null, mediaName);
                String log_str = LogUtils.genarateLogLine(log);
                logger.info(log_str);
            }
        }.start();
    }

    @AfterReturning("execution(* com.unisk.ad.ssp.controller.app.*Controller.click(..))")
    public void afterAppClick(JoinPoint point) {
        Object[] args = point.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        final String ip = request.getRemoteHost();
        final String zoneid = request.getParameter("zoneid");
        final String pushid = request.getParameter("pushid");
        final String mediaName = request.getParameter("appname");

        new Thread() {
            @Override
            public void run() {
                Log log = new Log(SSP_CLICK, VERSION, SOURCE, null, new Date(),
                        null, zoneid, pushid, ip, null, null, null, mediaName);
                String log_str = LogUtils.genarateLogLine(log);
                logger.info(log_str);
            }
        }.start();
    }

    @AfterReturning("execution(* com.unisk.ad.ssp.controller.web.*Controller.click(..))")
    public void afterWebClick(JoinPoint point) {
        Object[] args = point.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        final String ip = request.getRemoteHost();
        final String zoneid = request.getParameter("zoneid");
        final String pushid = request.getParameter("pushid");
        final String mediaName = request.getParameter("site_name");

        new Thread() {
            @Override
            public void run() {
                Log log = new Log(SSP_CLICK, VERSION, SOURCE, null, new Date(),
                        null, zoneid, pushid, ip, null, null, null, mediaName);
                String log_str = LogUtils.genarateLogLine(log);
                logger.info(log_str);
            }
        }.start();
    }

    @AfterReturning("execution(* com.unisk.ad.ssp.dispatcher.BidderRespDispatcher.generateResp(..))")
    public void afterPull(JoinPoint point) {
        final Object[] args = point.getArgs();
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> paramMap = (Map<String, Object>) args[3];
                String ip = paramMap.get("ip") != null ? paramMap.get("ip").toString() : null;

                String jsonStr = args[2].toString();
                JsonNode jsonNode = JsonUtils.readTree(jsonStr);
                String zoneid = JsonUtils.readValueAsText(jsonNode, "impid");
                String wurl = JsonUtils.readValueAsText(jsonNode, "wurl");

                Map<String, String> urlMap = UrlUtils.URLRequest(wurl);
                String pushid = urlMap.get("pushid");
                String mediaName = urlMap.get("appname");

                Log log = new Log(LogAspect.SSP_PULL, LogAspect.VERSION, LogAspect.SOURCE, null, new Date(),
                        null, zoneid, pushid, ip, null, null, null, mediaName);
                String log_str = LogUtils.genarateLogLine(log);
                logger.info(log_str);
            }
        }.start();
    }

}
