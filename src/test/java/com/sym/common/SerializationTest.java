package com.sym.common;

import com.sym.serialization.entity.OrderEntity;
import com.sym.serialization.SerializationUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * 序列化测试
 *
 * @author shenyanming
 * Create on 2021/07/07 19:17
 */
public class SerializationTest {

    @Test
    public void hessianSerializeTest() throws IOException {
        OrderEntity entity = new OrderEntity();
        System.out.println(new String(SerializationUtil.Hessian.serialize(entity)));
    }

    @Test
    public void hessianDeserializeTest() throws IOException {
        OrderEntity entity = new OrderEntity();
        byte[] bytes = SerializationUtil.Hessian.serialize(entity);
        System.out.println(SerializationUtil.Hessian.deserialize(bytes, OrderEntity.class));
    }

    /**
     * 序列化, 省略流关闭
     */
    @Test
    public void jdkSerializeTest() throws IOException {
        OrderEntity orderEntity = new OrderEntity();
        byte[] bytes = SerializationUtil.Jdk.serialize(orderEntity);
        System.out.println(new String(bytes));
    }

    /**
     * 反序列化, 省略流关闭
     */
    @Test
    public void jdkDeserializeTest() throws Exception {
        // 先执行序列化
        byte[] bytes = SerializationUtil.Jdk.serialize(new OrderEntity());
        System.out.println(SerializationUtil.Jdk.deserialize(bytes, OrderEntity.class));
    }

    @Test
    public void kryoSerializeTest() {
        OrderEntity entity = new OrderEntity();
        byte[] bytes = SerializationUtil.Kryo.serialize(entity);
        System.out.println(new String(bytes));
    }

    @Test
    public void kryoDeserializeTest() {
        byte[] bytes = SerializationUtil.Kryo.serialize(new OrderEntity());
        OrderEntity entity = SerializationUtil.Kryo.deserialize(bytes, OrderEntity.class);
        System.out.println(entity);
    }

    @Test
    public void xStreamSerializeTest() {
        String xml = SerializationUtil.XStream.toXml(new OrderEntity());
        System.out.println(xml);
    }

    @Test
    public void xStreamDeserializeTest() {
        String xml = SerializationUtil.XStream.toXml(new OrderEntity());
        OrderEntity orderEntity = SerializationUtil.XStream.fromXml(xml, OrderEntity.class);
        System.out.println(orderEntity);
    }
}
