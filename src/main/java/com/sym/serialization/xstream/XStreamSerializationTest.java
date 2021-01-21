package com.sym.serialization.xstream;

import com.sym.serialization.entity.OrderEntity;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author shenyanming
 * Created on 2020/5/20 08:55
 */
public class XStreamSerializationTest {

    private byte[] bytes;
    private String xmlPrefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    @Test
    public void serialize(){
        OrderEntity entity = new OrderEntity();

        XStream xStream = new XStream(new DomDriver());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        xStream.toXML(entity, bos);
        bytes = bos.toByteArray();
        System.out.println(new String(bytes));
    }

    @Test
    public void deserialize(){
        this.serialize();

        XStream xStream = new XStream(new DomDriver());
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        OrderEntity entity = (OrderEntity) xStream.fromXML(bis);
        System.out.println(entity);
    }
}
