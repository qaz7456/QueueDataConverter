package com.converter;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bean.TemplateResponseBean;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.util.XMLConvertor;

public class TemplateResponseConverter implements Converter {
	private static final Logger logger = LogManager.getLogger(TemplateResponseConverter.class);

	@Override
	public boolean canConvert(Class type) {
		return type.equals(TemplateResponseBean.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

		TemplateResponseBean response = (TemplateResponseBean) source;

		writer.addAttribute("service", response.getService());

		writer.startNode("File");
//		writer.setValue(response.getFile());
//		writer.endNode();
//		writer.startNode("Source");
//		writer.setValue(response.getSource());
//		writer.endNode();
//		writer.startNode("Description");
//		writer.setValue(response.getDescription());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		TemplateResponseBean response = new TemplateResponseBean();
		List<Map<String, String>> configList = null;
		try {
			// 得到多筆設定檔
			configList = XMLConvertor.getConfigMap();
		} catch (Exception e) {
			logger.error(e);
		}
		// 判斷是否還有節點未讀取
		while (reader.hasMoreChildren()) {
			response.setService(reader.getAttribute("service"));
			// 開始讀取
			reader.moveDown();

			String value = reader.getValue();
//			switch (reader.getNodeName()) {
//			case "FileA":
//				String aString = "File";
//				response.setFile(value);
//				break;
//			case "SourceA":
//				response.setSource(value);
//				break;
//			case "DescriptionA":
//				response.setDescription(value);
//				break;
//			case "service":
//				response.setService(value);
//				break;
//			}
			logger.debug("node: {} \\ value: {}", reader.getNodeName(), value);
			reader.moveUp();
		}
		return response;
	}

}
