package com.bootdo.common.utils;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtils {


	private static ObjectMapper ObjectMapper = new ObjectMapper();

	static {
		// config
		ObjectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		ObjectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		ObjectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
		ObjectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
	}

	public static <T> String obootdo2String(T src) {
		if (src == null) {
			return null;
		}
		try {
			return src instanceof String ? (String) src : ObjectMapper.writeValueAsString(src);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T string2Obootdo(String src, TypeReference<T> typeReference) {
		if (src == null || typeReference == null) {
			return null;
		}
		try {
			return (T) (typeReference.getType().equals(String.class) ? src : ObjectMapper.readValue(src, typeReference.getClass()));
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * Bean对象转JSON
	 * 
	 * @param Object
	 * @param dataFormatString
	 * @return
	 */
	public static String beanToJson(Object Object, String dataFormatString) {
		if (Object != null) {
			if (StringUtils.isEmpty(dataFormatString)) {
				return JSONObject.toJSONString(Object);
			}
			return JSON.toJSONStringWithDateFormat(Object, dataFormatString);
		} else {
			return null;
		}
	}

	/**
	 * Bean对象转JSON
	 * 
	 * @param Object
	 * @return
	 */
	public static String beanToJson(Object Object) {
		if (Object != null) {
			return JSON.toJSONString(Object);
		} else {
			return null;
		}
	}

	/**
	 * String转JSON字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String stringToJsonByFastjson(String key, String value) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>(16);
		map.put(key, value);
		return beanToJson(map, null);
	}

	/**
	 * 将json字符串转换成对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object jsonToBean(String json, Object clazz) {
		if (StringUtils.isEmpty(json) || clazz == null) {
			return null;
		}
		return JSON.parseObject(json, clazz.getClass());
	}

	/**
	 * json字符串转map
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		return JSON.parseObject(json, Map.class);
	}


	/**
	 * @author Andy-J<br>
	 * @version 1.0<br>
	 * @createDate 2020/6/18 15:53 <br>
	 * @desc  将 string json 转成 list 对象
	 */
	public static <T> List<T> parseArray(String jsonStr , Class<T> cls){
		List<T> list = new ArrayList<>();

		try {
			list = JSON.parseArray(jsonStr,cls);

		}catch (Exception e){
			System.out.println("解析json，返回自定义对象失败");
		}

		return list;

	}
}
