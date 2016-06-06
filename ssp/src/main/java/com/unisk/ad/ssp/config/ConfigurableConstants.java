package com.unisk.ad.ssp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 可用Properties文件配置的Constants基类.
 * 本类既保持了Constants的static和final(静态与不可修改)特性,又拥有了可用Properties文件配置的特征,
 * 主要是应用了Java语言中静态初始化代码的特性.
 * <p>
 * 子类可如下编写
 * 
 * <pre>
 *   public class Constants extends ConfigurableConstants {
 *    static {
 *      init(&quot;constants.properties&quot;);
 *   }
 *   public final static String ERROR_BUNDLE_KEY = getProperty(&quot;constant.error_bundle_key&quot;, &quot;errors&quot;); }
 * </pre>
 * 
 * @author sunyunjie
 */
public class ConfigurableConstants {

	protected static final Properties p = new Properties();

	protected static void init(InputStream in) {
		try {
			if (in != null) {
				p.load(in);
			} else {
				throw new RuntimeException("the fileinputstream is null");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * 静态读入属性文件到Properties p变量中
	 */
	protected static void init(String propertyFileName) {
		InputStream in = null;
		// in =
		// ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
		in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileName);
		init(in);
	}

	/**
	 * 封装了Properties类的getProperty函数,使p变量对子类透明.
	 * 
	 * @param key
	 *          property key.
	 * @param defaultValue
	 *          当使用property key在properties中取不到值时的默认值.
	 */
	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	protected static boolean getBoolean(String key, boolean defaultValue) {
		return p.getProperty(key, String.valueOf(defaultValue)).toLowerCase().equals("true");
	}
	
	protected static int getInt(String key, int defaultValue) {
		return Integer.valueOf(p.getProperty(key, String.valueOf(defaultValue)));
	}
	
	protected static long getLong(String key, int defaultValue) {
		return Long.valueOf(p.getProperty(key, String.valueOf(defaultValue)));
	}
	
}
