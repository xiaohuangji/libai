package com.dajie.wika.weixin.util;

import com.dajie.wika.weixin.model.ReplyMessage.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wills on 2/10/14.
 */
public class XmlUtil {

    private static XStream marshal_xStream=null;

    private static XStream unmarshal_xStream=null;

    public static String toXml(Object o){
        if(marshal_xStream==null){
            marshal_xStream=new XStream(new Xpp3Driver());
            marshal_xStream.autodetectAnnotations(true);
        }
        return marshal_xStream.toXML(o);
    }

    public static Map<String,String> fromXml(String xml){
        if(unmarshal_xStream==null){
            unmarshal_xStream=new XStream();
            unmarshal_xStream.alias("xml",Object.class);
            unmarshal_xStream.registerConverter(new Converter() {
                @Override
                public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

                }

                @Override
                public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
                    Map<String,String> map=new HashMap<String, String>();
                    while(reader.hasMoreChildren()) {
                        reader.moveDown();
                        String key = reader.getNodeName();
                        String lowKey=key.substring(0,1).toLowerCase()+key.substring(1);
                        String value = reader.getValue();
                        map.put(lowKey,value);
                        reader.moveUp();
                    }
                    return map;
                }

                @Override
                public boolean canConvert(Class aClass) {
                    return true;
                }
            });
        }
        return (Map)unmarshal_xStream.fromXML(xml);
    }
}
