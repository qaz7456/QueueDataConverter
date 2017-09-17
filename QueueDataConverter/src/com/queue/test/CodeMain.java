package com.queue.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bean.TemplateResponseBean;
import com.converter.TemplateResponseConverter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.util.StringConvertor;
import com.util.TransBean;
import com.util.XMLConvertor;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

public class CodeMain {
	private static final Logger logger = LogManager.getLogger(CodeMain.class);

	// public static void main(String[] args) throws ClassNotFoundException {
	// String xml = "<?xml version='1.0' encoding='UTF-8'?><Response
	// service=\"RouteService\"><FileA>FileA</FileA><SourceA>SourceA</SourceA><DescriptionA>DescriptionA</DescriptionA></Response>";
	//
	// DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
	// DocumentBuilder dombuilder = null;
	// try {
	// dombuilder = domfac.newDocumentBuilder();
	// } catch (ParserConfigurationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// InputStream is = null;
	// try {
	// is = new FileInputStream("src/converter-config.xml");
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// Document doc = null;
	// try {
	// doc = dombuilder.parse(is);
	// } catch (SAXException | IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// Element root = doc.getDocumentElement();
	//
	// NodeList level_1_nodes = root.getChildNodes();
	//
	// System.out.println(level_1_nodes.getLength());
	//
	// // first create the properties
	// DynaProperty[] properties = new DynaProperty[]{};
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
	//
	// System.out.println(tag + " : " + value);
	// System.out.println(index);
	// if ("destination".equals(tag)) {
	// dynaBean.set(value, String.class);
	// properties[1] = new DynaProperty(value, String.class);
	// }
	// }
	// }
	// System.out.println("----------------");
	// index++;
	// }
	// }
	//// // next using the properties define the class
	//// DynaClass dynaClass = new BasicDynaClass("dynaClass", null,
	// properties);
	////
	//// // now, with the class, create a new instance
	//// DynaBean dynaBean = null;
	//// try {
	//// dynaBean = dynaClass.newInstance();
	//// } catch (IllegalAccessException | InstantiationException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// }
	//// System.out.println(dynaBean.get("destination"));
	//
	// }

	public static void main(String[] args) throws ClassNotFoundException {

		String xml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"RouteService\"><FileA>FileA</FileA><SourceA>SourceA</SourceA><DescriptionA>DescriptionA</DescriptionA></Response>";
		String xml2 = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"RouteService\"><File>FileA</File><Source>SourceA</Source><Description>DescriptionA</Description></Response>";
		String test = "<Request service=\"SALE_ORDER_STATUS_PUSH_SERVICE\" lang=\"zh-CN\"><Body><SaleOrderStatusRequest><CompanyCode>W8860571504</CompanyCode><SaleOrders><SaleOrder><WarehouseCode>886DCA</WarehouseCode><ErpOrder>20170803TW1</ErpOrder><WayBillNo>289081343391</WayBillNo><ShipmentId>OXMS201708030114140703</ShipmentId><Waves>EW886A17080300102</Waves><CartNum>1</CartNum><GridNum>0001</GridNum><Carrier>顺丰速运</Carrier><CarrierProduct>島内件(80CM0.5-1.5KG)</CarrierProduct><IsSplit>N</IsSplit><Steps><Step><EventTime>2017-08-0315:31:25</EventTime><EventAddress>WOM</EventAddress><Status>1400</Status><Note>您的订单已取消,取消原因：客户要求取消订单</Note></Step></Steps></SaleOrder></SaleOrders></SaleOrderStatusRequest></Body></Request>";
		// String IO_TASK_NO = "4413051710269522";
		// String DET_FLAG = "1";
		// Object[] params = new Object[] { IO_TASK_NO, DET_FLAG };
		//
		// Map<String, String> m = new HashMap<String, String>();
		// m.put("IO_TASK_NO", "4413051710269522");
		// m.put("DET_FLAG", "1");
		//
		// String s = "";
		// try {
		// s = XMLConvertor.convertor(m, "PARAM");
		//
		// System.out.println(s);
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// Map<String, String> ma1 = new HashMap<String, String>();
		// ma1.put("File", "2");
		// ma1.put("Source", "3");
		// ma1.put("Description", "4");
		//
		// try {
		// ma1 = StringConvertor.convertor(ma1, xml2, "Response");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("----------------");
		//
		// try {
		// s = XMLConvertor.convertor(ma1, "Response");
		//
		// System.out.println(s);
		// System.out.println("----------------");
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 先把TemplateResponseBean轉成java bean map
		Map<String, Object> templateMap = TransBean.toMap(new TemplateResponseBean());
		
//		Map<String, Object> map = null;
//		try {
//			map = XMLConvertor.XML2AttrMap(test);
//			map = XMLConvertor.XML2Map(test);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		 System.out.println(map.toString());
		
			try {
				XMLConvertor.test(test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 
		 
		 
//		List<Map<String, String>> configList = null;
//		try {
//			// 得到多筆設定檔
//			configList = XMLConvertor.getConfigMap();
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		for (String key : templateMap.keySet()) {
//			templateMap.put(key, "");
//		}
//		Map<String, Object> ma1 = new HashMap<String, Object>();
//		try {
//			ma1 = StringConvertor.convertor(templateMap, test, "Request");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (String key : ma1.keySet()) {
//			logger.debug("ma1 key: {} \\ value: {}", key, ma1.get(key));
//		}

		
//		for (String key : templateMap.keySet()) {
//			for (int i = 0; i < configList.size(); i++) {
//				// 得到當前設定檔
//				Map<String, String> map = configList.get(i);
//				for (int j = 0; j < map.size(); j++) {
//					String source = map.get("source");
//					String destination = map.get("destination");
//					String description = map.get("destination");
//
//					if (key.equals(destination)) {
//						templateMap.put(key, templateMap.get(key));
//					}
//				}
//			}
//			logger.debug("templateMap key: {} \\ value: {}", key, templateMap.get(key));
//		}
		
		// map.put("description", 1);
		// map.put("file", 2);
		// map.put("service", 3);
		// map.put("lang", 5);
		// map.put("source", 4);
		//
		// String s = "";
		// try {
		// s = XMLConvertor.convertor(map, "Response");
		//
		// System.out.println(s);
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// String tag = "Response";
		// Class<?> aliasClass = TemplateResponseBean.class;
		// String result = new CodeMain().Conversion(xml, tag, aliasClass);
		//
		// System.out.println(result);
	}

	public String Conversion(String xml, String tag, Class<?> aliasClass) {
		XStream xstream = new XStream(new DomDriver());
		// final Map<String, Class<?>> properties =
		// new HashMap<String, Class<?>>();
		// properties.put("service", String.class);
		// properties.put("File", String.class);
		// properties.put("Source",String.class);
		// properties.put("Description", String.class);
		//
		// final Class<?> beanClass =
		// createBeanClass("TemplateResponseBean", properties);

		xstream.alias(tag, aliasClass);

		xstream.registerConverter(new TemplateResponseConverter());
		TemplateResponseBean templateResponseBean = (TemplateResponseBean) xstream.fromXML(xml);
		xml = xstream.toXML(templateResponseBean);

		return xml;
	}

	public static Class<?> createBeanClass(
			/* fully qualified class name */
			final String className,
			/* bean properties, name -> type */
			final Map<String, Class<?>> properties) {

		final BeanGenerator beanGenerator = new BeanGenerator();

		/* use our own hard coded class name instead of a real naming policy */
		beanGenerator.setNamingPolicy(new NamingPolicy() {
			@Override
			public String getClassName(final String prefix, final String source, final Object key,
					final Predicate names) {
				return className;
			}
		});
		BeanGenerator.addProperties(beanGenerator, properties);
		return (Class<?>) beanGenerator.createClass();
	}

}
