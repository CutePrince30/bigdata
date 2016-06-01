package com.unisk.ad.ssp.util;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.TypeSafeTemplate;

public class TemplateUtils {

	private static Handlebars handlebars;
	
	public static Handlebars create() {
		if (handlebars != null) {
			return handlebars;
		}
		synchronized (TemplateUtils.class) {
			if (handlebars == null) {
				return newInstance();
			}
			return handlebars;
		}
	}

	private static Handlebars newInstance() {
		return new Handlebars();
	}
	
	public static <T, S extends TypeSafeTemplate<T>> S getTemplate(String compileStr, final Class<S> type) {
		S template = null;
		try {
			template = TemplateUtils.create().compile(compileStr).as(type);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

}
