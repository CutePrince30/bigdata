package com.unisk.ad.ssp.util;

import java.io.IOException;

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

	private static Logger log = LoggerFactory.getLogger(JsonUtils.class);
	
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
	 * @param jsonTypeReference
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
}
