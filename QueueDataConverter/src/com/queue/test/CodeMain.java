package com.queue.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bean.TemplateResponseBean;
import com.converter.TemplateAttributeResponseConverter;
import com.converter.TemplateResponseConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CodeMain {
	private static final Logger logger = LogManager.getLogger(CodeMain.class);

	public static void main(String[] args) throws ClassNotFoundException {

		String xml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"RouteService\"><FileA>FileA</FileA><SourceA>SourceA</SourceA><DescriptionA>DescriptionA</DescriptionA></Response>";
		String tag = "Response";

		new CodeMain().Conversion(xml, tag);
		
		System.out.println(new CodeMain().Conversion(xml, tag));
	}

	public String Conversion(String xml, String tag) {
		XStream xstream = new XStream(new DomDriver());

		xstream.alias(tag, TemplateResponseBean.class);

		xstream.registerConverter(new TemplateResponseConverter());
		TemplateResponseBean templateResponseBean = (TemplateResponseBean) xstream.fromXML(xml);
		xml = xstream.toXML(templateResponseBean);

		return xml;
	}

}
