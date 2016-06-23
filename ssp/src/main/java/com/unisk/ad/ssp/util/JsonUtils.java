package com.unisk.ad.ssp.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换工具类
 * 
 * @author CutePrince
 *
 */
public class JsonUtils {

	private static Logger log = LoggerFactory.getLogger("ssp");
	
	private final static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}

	private JsonUtils() {}

	public static String encode(Object obj) {
		try {
			synchronized (objectMapper) {
				return objectMapper.writeValueAsString(obj);
			}
		}
		catch (JsonGenerationException e) {
			log.error("encode(Object)", e); //$NON-NLS-1$
		}
		catch (JsonMappingException e) {
			log.error("encode(Object)", e); //$NON-NLS-1$
		}
		catch (IOException e) {
			log.error("encode(Object)", e); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * 将json string反序列化成对象
	 *
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T decode(String json, Class<T> valueType) {
		try {
			synchronized (objectMapper) {
				return objectMapper.readValue(json, valueType);
			}
		}
		catch (JsonParseException e) {
			log.error("decode(String, Class<T>)", e);
		}
		catch (JsonMappingException e) {
			log.error("decode(String, Class<T>)", e);
		}
		catch (IOException e) {
			log.error("decode(String, Class<T>)", e);
		}
		return null;
	}

	/**
	 * 将json array反序列化为对象
	 *
	 * @param json
	 * @param typeReference
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T decode(String json, TypeReference<T> typeReference) {
		try {
			synchronized (objectMapper) {
				return (T) objectMapper.readValue(json, typeReference);
			}
		}
		catch (JsonParseException e) {
			log.error("decode(String, JsonTypeReference<T>)", e);
		}
		catch (JsonMappingException e) {
			log.error("decode(String, JsonTypeReference<T>)", e);
		}
		catch (IOException e) {
			log.error("decode(String, JsonTypeReference<T>)", e);
		}
		return null;
	}

	public static String findValueAsText(String json, String field) {
		if (StringUtils.isEmpty(json)) {
			return "";
		}
		synchronized (objectMapper) {
			try {
				return readValueAsText(objectMapper.readTree(json), field);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static List<String> readValuesAsText(JsonNode json, String field) {
		return json.findValuesAsText(field);
	}

	public static String readValueAsText(JsonNode json, String field) {
		List<String> list = json.findValuesAsText(field);
		if (list.size() != 0) {
			return list.get(0);
		}
		return "";
	}

	public static JsonNode readTree(String json) {
		synchronized (objectMapper) {
			try {
				return objectMapper.readTree(json);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
