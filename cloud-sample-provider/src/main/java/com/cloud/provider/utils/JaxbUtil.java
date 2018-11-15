package com.cloud.provider.utils;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author huyaxi
 */
public class JaxbUtil {

    public static String beanToXml(Object obj){
        StringWriter writer = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            //Marshaller.JAXB_FRAGMENT:是否省略xml头信息,true省略，false不省略
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            //Marshaller.JAXB_FORMATTED_OUTPUT:决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshaller.JAXB_ENCODING:xml的编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            writer = new StringWriter();
            marshaller.marshal(obj, writer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return writer.toString();
    }


    public static <T> T xmlToBean(String xml,Class<T> tClass){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            SAXParserFactory sax = SAXParserFactory.newInstance();
            sax.setNamespaceAware(false);
            XMLReader xmlReader = sax.newSAXParser().getXMLReader();
            Source source = new SAXSource(xmlReader, new InputSource(reader));
            return  (T)unmarshaller.unmarshal(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
