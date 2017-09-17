package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bean.CglibBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class StringConvertor {
	private static final Logger logger = LogManager.getLogger(StringConvertor.class);

	public static Map<String, Object> convertor(Map<String, Object> map, String xml, String type) throws Exception {

		CglibBean bean = new CglibBean(map);

		Object obj = bean.getObject();

		Class<?> clazz = obj.getClass();

		XStream xs = new XStream(new DomDriver());
		xs.alias(type, clazz);

		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			xs.aliasField(fields[i].getName().substring(12, fields[i].getName().length()), clazz, fields[i].getName());
			logger.debug("alias: {} \\ Class definedIn: {} \\ fieldName: {}",
					fields[i].getName().substring(12, fields[i].getName().length()), clazz,
					fields[i].getName());
		}

		Object obj2 = (Object) xs.fromXML(xml);

		Class<?> clazz2 = obj2.getClass();
		Method[] m3 = clazz2.getDeclaredMethods();
		for (Method method : m3) {

			if (method.getName().indexOf("get") > -1) {
				String methodName = method.getName();
				System.out.println(methodName);
				String value = (String) method.invoke(obj2, new Object[0]);
				String key = methodName.substring(3, methodName.length());
				map.put(key, value);
			}
		}

		return map;

	}
}