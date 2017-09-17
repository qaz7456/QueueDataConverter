package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bean.Body;
import com.bean.CglibBean;
import com.bean.Request;
import com.bean.Response;
import com.bean.TemplateResponseBean;
import com.thoughtworks.xstream.XStream;

public class XMLConvertor {
	private static final Logger logger = LogManager.getLogger(XMLConvertor.class);

	public static String convertor(Map<String, ?> templateMap, Map<String, ?> sourceAttrMap, String type)
			throws Exception {

		// 實體化CglibBean
		CglibBean bean = new CglibBean(templateMap);
		// 賦予字段值，如果不赋值则xml中就沒有這個字段
		for (String template : templateMap.keySet()) {
			bean.setValue(template, templateMap.get(template));
			logger.debug("{}: {}", template, templateMap.get(template));

		}
		// 訂製XML格式
		Object object = bean.getObject();
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		XStream x = new XStream();

		// 標頭
		x.alias(type, object.getClass());
		x.autodetectAnnotations(true);
		// alias屬性為對應的屬性名稱 $cglib_prop_XXX
		for (int i = 0; i < fields.length; i++) {
			x.aliasField(fields[i].getName().substring(12, fields[i].getName().length()), object.getClass(),
					fields[i].getName());

			// x.aliasAttribute(fields[i].getName().substring(12,
			// fields[i].getName().length()), "$cglib_prop_sender");
			// x.useAttributeFor(object.getClass(), "$cglib_prop_sender");
			logger.debug("alias: {} \\ Class definedIn: {} \\ fieldName: {}",
					fields[i].getName().substring(12, fields[i].getName().length()), object.getClass(),
					fields[i].getName());
		}
		// x.useAttributeFor(object.getClass(), "$cglib_prop_product");
		// x.useAttributeFor(object.getClass(), "$cglib_prop_lang");
		String xml = x.toXML(object);

		// xstream有bug，當參數中含有底線時，會轉為雙底線，這邊做簡單處理
		return xml.replaceAll("__", "_");

	}

	public static List<Map<String, String>> getConfigMap() throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder = null;
		try {
			dombuilder = domfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error(e);
		}
		InputStream is = null;
		try {
			is = new FileInputStream("src/converter-config.xml");
		} catch (FileNotFoundException e) {
			logger.error(e);
		}
		Document doc = null;
		try {
			doc = dombuilder.parse(is);
		} catch (SAXException | IOException e) {
			logger.error(e);
		}

		Element root = doc.getDocumentElement();

		NodeList fields = root.getChildNodes();

		for (int i = 0; i < fields.getLength(); i++) {
			Node node = (Node) fields.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				map = new HashMap<String, String>();
				NodeList settings = node.getChildNodes();
				// System.out.println("----------------");
				for (int j = 0; j < settings.getLength(); j++) {
					Node setting = (Node) settings.item(j);
					if (setting.getNodeType() == Node.ELEMENT_NODE) {
						map.put(setting.getNodeName(), setting.getTextContent());
						// System.out.println(setting.getNodeName() + " : "
						// +setting.getTextContent());
					}
				}
				// System.out.println("----------------");
				list.add(map);
			}
		}

		// for(int i =0;i<list.size();i++){
		// Map<String, String> map2 = list.get(i);
		// for(String key: map2.keySet()){
		// System.out.println(key +" : " + list.get(i).get(key));
		// }
		// }

		// for (int i = 0; i < all_nodeList.getLength(); i++) {
		//
		// Element element = (Element) all_nodeList.item(i);
		// if (element.getNodeType() == Node.ELEMENT_NODE) {
		//
		// String nodeName = element.getNodeName();
		//
		// map.put(nodeName, null);
		//
		// NodeList nodeList = element.getChildNodes();
		//
		// for (int j = 0; j < nodeList.getLength(); j++) {
		// Node node = (Node) nodeList.item(j);
		// if (node.getNodeType() != Node.TEXT_NODE) {
		// System.out.println(node.getNodeName());
		// }
		// }
		// }
		// }

		// Element root = doc.getDocumentElement();
		//
		// NodeList level_1_nodes = root.getChildNodes();
		//
		// System.out.println(level_1_nodes.getLength());
		//
		// // first create the properties
		// DynaProperty[] properties = new DynaProperty[] {};
		//
		// DynaBean dynaBean = new LazyDynaBean();
		//
		// int index = 0;
		// System.out.println("----------------");
		// for (int i = 0; i < level_1_nodes.getLength(); i++) {
		// Node level_1_node = level_1_nodes.item(i);
		// if (level_1_node.getNodeType() == Node.ELEMENT_NODE) {
		// Element level_1_element = (Element) level_1_node;
		// System.out.println(level_1_element.getTagName());
		//
		// NodeList level_2_nodes = level_1_node.getChildNodes();
		//
		// for (int j = 0; j < level_2_nodes.getLength(); j++) {
		// Node level_2_node = level_2_nodes.item(j);
		//
		// if (level_2_node.getNodeType() == Node.ELEMENT_NODE) {
		// Element level_2_element = (Element) level_2_node;
		//
		// String tag = level_2_element.getTagName();
		// String value = level_2_element.getTextContent();
		// logger.debug("index: {} \\ tag: {} \\ value: {}", index, tag, value);
		// map.put(tag, value);
		// list.add(map);
		// }
		// }
		// System.out.println("----------------");
		// index++;
		// }
		// }
		return list;
	}

	public static Map<String, Object> XML2Map(String xml) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder = null;
		try {
			dombuilder = domfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error(e);
		}

		InputSource is = null;

		try {
			is = new InputSource(new StringReader(xml));
		} catch (Exception e) {
		}
		Document doc = null;
		try {
			doc = dombuilder.parse(is);
		} catch (SAXException | IOException e) {
			logger.error(e);
		}

		NodeList all_nodeList = doc.getElementsByTagName("*");
		StringBuilder textContent = new StringBuilder();
		for (int i = 0; i < all_nodeList.getLength(); i++) {

			Element element = (Element) all_nodeList.item(i);

			if (element.getNodeType() == Node.ELEMENT_NODE) {

				String nodeName = element.getNodeName();

				map.put(nodeName, null);

				if (i == 0)
					map.put("XmlType", element.getNodeName());

				textContent.append(nodeName);

				NodeList nodeList = element.getChildNodes();

				for (int j = 0; j < nodeList.getLength(); j++) {
					Node node = (Node) nodeList.item(j);
					if (node.getNodeType() == Node.TEXT_NODE) {
						String text = node.getTextContent();
						textContent.append(" " + text);
						map.put(nodeName, text);

					}
				}
				
				NamedNodeMap element_attr = element.getAttributes();
				
				for (int j = 0; j < element_attr.getLength(); ++j) {
					Node attr = element_attr.item(j);
					String attrName = attr.getNodeName();
					String attrVal = attr.getNodeValue();

					map.put(attrName, attrVal);
				}
			}
			textContent.append("\n");
		}
		System.out.println(textContent.toString());

		return map;

	}

	public static Map<String, Object> XML2AttrMap(String xml) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder = null;
		try {
			dombuilder = domfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error(e);
		}

		InputSource is = null;

		try {
			is = new InputSource(new StringReader(xml));
		} catch (Exception e) {
		}
		Document doc = null;
		try {
			doc = dombuilder.parse(is);
		} catch (SAXException | IOException e) {
			logger.error(e);
		}

		NodeList all_nodeList = doc.getElementsByTagName("*");
		StringBuilder textContent = new StringBuilder();
		for (int i = 0; i < all_nodeList.getLength(); i++) {

			Element element = (Element) all_nodeList.item(i);
			if (element.getNodeType() == Node.ELEMENT_NODE) {

				String nodeName = element.getNodeName();

				map.put(nodeName, null);

				textContent.append(nodeName);

				NamedNodeMap element_attr = element.getAttributes();

				List<Map<String, String>> attrList = new ArrayList<Map<String, String>>();

				for (int j = 0; j < element_attr.getLength(); ++j) {
					Node attr = element_attr.item(j);
					String attrName = attr.getNodeName();
					String attrVal = attr.getNodeValue();
					textContent.append(" " + attrName + "=\"" + attrVal + "\"");

					Map<String, String> attrMap = new HashMap<String, String>();
					attrMap.put(attrName, attrVal);
					attrList.add(attrMap);
				}
				map.put(nodeName, attrList);

			}
			textContent.append("\n");
		}
		System.out.println(textContent.toString());

		return map;

	}

	public static void test(String xml) throws Exception {
		Map<String, Object> templateMap = TransBean.toMap(new TemplateResponseBean());
		Map<String, Object> sourceMap = XMLConvertor.XML2Map(xml);
		Map<String, Object> sourceAttrMap = XMLConvertor.XML2AttrMap(xml);

		List<Map<String, String>> configList = XMLConvertor.getConfigMap();

		System.out.println("templateMap");
		System.out.println("----------------");
		for (String s : templateMap.keySet()) {
			System.out.println(s + " : " + templateMap.get(s));
		}
		System.out.println("----------------");

		System.out.println("\nsourceMap");
		System.out.println("----------------");
		for (String s : sourceMap.keySet()) {
			System.out.println(s + " : " + sourceMap.get(s));
		}
		System.out.println("----------------");

		System.out.println("\nsourceAttrMap");
		System.out.println("----------------");
		for (String s : sourceAttrMap.keySet()) {
			System.out.println(s + " : " + sourceAttrMap.get(s));
		}
		System.out.println("----------------");

		System.out.println("\n設定檔");
		System.out.println("----------------");
		// for (String key : templateMap.keySet()) {
		for (int i = 0; i < configList.size(); i++) {
			// 得到當前設定檔
			Map<String, String> configMap = configList.get(i);

			String destination = configMap.get("destination");
			String source = configMap.get("source");

			// 如果設定的目標欄位和template的欄位一樣的話，,且在傳進來的xml找的到值的話，就把value設為來源欄位
			for (String template : templateMap.keySet()) {
				if (destination.equals(template)) {

					if (sourceMap.get(source) != null) {
						templateMap.put(template, source);
					}
				}
			}
			for (String config : configMap.keySet()) {
				System.out.println(config + " : " + configMap.get(config));
			}
			System.out.println();
		}
		System.out.println("----------------");

		System.out.println("\ntemplateMap");
		System.out.println("----------------");
		for (String s : templateMap.keySet()) {
			System.out.println(s + " : " + templateMap.get(s));
		}
		System.out.println("----------------");

		for (String source : sourceMap.keySet()) {
			for (String template : templateMap.keySet()) {
				if (source.equals(templateMap.get(template))) {
					templateMap.put(template, sourceMap.get(source));
				}
			}
		}
		System.out.println("\ntemplateMap");
		System.out.println("----------------");
		for (String s : templateMap.keySet()) {
			System.out.println(s + " : " + templateMap.get(s));
		}
		System.out.println("----------------");
		String type = (String) sourceMap.get("XmlType");
		// String result = XMLConvertor.convertor(templateMap, sourceAttrMap,
		// type);
		// System.out.println(result);

		Body body = new Body();
		body.setProduct((String) templateMap.get("product"));
		body.setSender((String) templateMap.get("sender"));

		StringWriter sw = new StringWriter();
		
		switch (type) {
		case "Request":
			Request request = new Request();
			request.setHead((String) templateMap.get("head"));
			request.setLang((String) templateMap.get("lang"));
			request.setService((String) templateMap.get("service"));
			request.setBody(body);
			JAXB.marshal(request, sw);
			logger.debug(sw.toString());
			break;
		case "Response":
			Response response = new Response();
			response.setHead((String) templateMap.get("head"));
			response.setLang((String) templateMap.get("lang"));
			response.setService((String) templateMap.get("service"));
			response.setBody(body);
			JAXB.marshal(response, sw);
			logger.debug(sw.toString());
			break;
		}
		// }

		// for (String key : templateMap.keySet()) {
		// logger.debug("templateMap key: {} \\ value: {}", key,
		// templateMap.get(key));
		// }

	}
}