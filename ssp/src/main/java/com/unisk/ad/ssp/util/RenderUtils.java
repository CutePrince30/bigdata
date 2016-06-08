package com.unisk.ad.ssp.util;

import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class RenderUtils {

    private static Logger log = LoggerFactory.getLogger(RenderUtils.class);

    private static Template t = TemplateUtils.getTemplate("/template/response_template.beetl");;

    public static String render(int code, String message, Object data) {
        t.binding("code", code);
        t.binding("message", message);
        t.binding("data", data);

        String res = t.render();
        if (log.isDebugEnabled()) {
            log.debug("ssp返回值: ", res);
        }

        return res;
    }

}
