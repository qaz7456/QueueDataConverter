package com.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.queue.test.CodeMain;

public class TransBean {
	private static final Logger logger = LogManager.getLogger(TransBean.class);
	public static Map<String, Object> toMap(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 過濾class
				if (!key.equals("class")) {
					// 得到對應的getter
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					logger.debug("key: {} \\ value: {} " ,key,value);
					map.put(key, value);
				}

			}
		} catch (Exception e) {
			logger.debug("transBean2Map Error " + e);
		}

		return map;

	}
}
