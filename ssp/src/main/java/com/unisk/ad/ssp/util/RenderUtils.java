package com.unisk.ad.ssp.util;

import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;
import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class RenderUtils {

    private static Logger log = LoggerFactory.getLogger("ssp");

    private static Template app_template = TemplateUtils.getTemplate("/template/response2app_template.beetl");
    private static Template web_template = TemplateUtils.getTemplate("/template/response2web_template.beetl");

    public static String render(MediaType mt, Operate op, int code, String message, Object data) {
        String result = null;
        switch (mt) {
            case WEB:
                result = renderWeb(op, code, message, data);
                break;
            case APP:
                result = renderApp(op, code, message, data);
                break;
        }
        return result;
    }

    public static String renderApp(Operate op, int code, String message, Object data) {
        app_template.binding("code", code);
        app_template.binding("message", message);
        app_template.binding("data", data);

        String res = app_template.render();
        if (log.isDebugEnabled()) {
            log.debug("ssp返回值: ", res);
        }

        return res;
    }

    public static String renderWeb(Operate op, int code, String message, Object data) {
        web_template.binding("code", code);
        web_template.binding("message", message);
        web_template.binding("data", data);
        web_template.binding("operate", op.getName());

        String res = web_template.render();
        if (log.isDebugEnabled()) {
            log.debug("ssp返回值: ", res);
        }

        return res;
    }

}
