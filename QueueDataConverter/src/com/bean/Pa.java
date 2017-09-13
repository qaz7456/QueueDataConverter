package com.bean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Pa")
public class Pa {

    @XStreamAsAttribute
    @XStreamAlias("Milliseconds")
    String ms = "test";


    public static void main(String[] args) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(new Pa());
        System.out.println(xml);
    }
}