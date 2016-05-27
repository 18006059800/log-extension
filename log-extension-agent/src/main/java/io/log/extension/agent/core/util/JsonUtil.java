package io.log.extension.agent.core.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static <T> String marshal(T t) {
		// 添加功能，让时间格式更具有可读性
		mapper.setDateFormat(dateFormat);
		String result = "";
		try {
			result = mapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static <T> T unmarshal(String content, Class<T> type) {
		T obj = null;
		try {
			obj = mapper.readValue(content, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
