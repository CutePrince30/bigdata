package com.unisk.ad.ssp.util;

import com.unisk.ad.ssp.model.Log;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class LogUtils {

    public static final String LOG_SEPERATOR = "";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String genarateLogLine(Log log) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ");
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
        sb.append(defaultIfEmpty(log.getZoneId()));
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

}
