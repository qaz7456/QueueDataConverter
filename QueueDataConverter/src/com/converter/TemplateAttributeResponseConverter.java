package com.converter;

import com.bean.TemplateResponseBean;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class TemplateAttributeResponseConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.equals(TemplateResponseBean.class);
	}

	@Override
	public String toString(Object obj) {
		return ((TemplateResponseBean) obj).getService();
	}

	@Override
	public Object fromString(String str) {
		TemplateResponseBean templateResponseBean = new TemplateResponseBean();
		templateResponseBean.setService(str);
		return templateResponseBean;
	}

}
