package com.unisk.ad.ssp.util;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

/**
 * 生成模版类
 */
public class TemplateUtils {

	private static GroupTemplate gt;

	public static GroupTemplate create() {
		if (gt != null) {
			return gt;
		}
		synchronized (TemplateUtils.class) {
			if (gt == null) {
				return newInstance();
			}
			return gt;
		}
	}

	private static GroupTemplate newInstance() {
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
		Configuration cfg = null;
		try {
			cfg = Configuration.defaultConfiguration();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return new GroupTemplate(resourceLoader, cfg);
	}

	public static Template getTemplate(String path) {
		return create().getTemplate(path);
	}

}
