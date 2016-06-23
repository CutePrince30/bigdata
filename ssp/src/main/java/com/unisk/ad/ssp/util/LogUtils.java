package com.unisk.ad.ssp.util;

import com.unisk.ad.ssp.model.Log;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class LogUtils {

    public static final String LOG_SEPERATOR = "^A";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String genarateLogLine(Log log) {
        StringBuilder sb = new StringBuilder();

        sb.append(log.getCode());
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getName()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getVersion()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getSource()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getExchangeUserId()));
        sb.append(LOG_SEPERATOR);
        sb.append(sdf.format(log.getTime()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getUserId()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getAdId()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getPushId()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getIp()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getRegion()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getBidPrice()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getCostPrice()));
        sb.append(LOG_SEPERATOR);
        sb.append(defaultIfEmpty(log.getMediaName()));

        return sb.toString();
    }

    private static String defaultIfEmpty(String str) {
        return StringUtils.defaultIfEmpty(str, "");
    }

    public static void main(String[] args) {
        Log log = new Log();
        log.setAdId("123123123");
        log.setCode(200);
        log.setIp("127.0.0.1");
        log.setName("rtb_show");
        log.setUserId("107986961782312");
        log.setVersion("2312312");
        log.setTime(new Date());
        System.out.println(genarateLogLine(log));
    }

}
