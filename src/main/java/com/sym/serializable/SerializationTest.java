package com.sym.serializable;

import com.sym.serializable.domain.BankEntity;
import com.sym.serializable.domain.OrderEntity;
import com.sym.serializable.domain.extend.Son;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 序列化与反序列化测试类
 * <p>
 * Created by shenym on 2019/12/24.
 */
public class SerializationTest {

    private OrderEntity orderEntity;
    private BankEntity bankEntity;

    @Before
    public void init() {
        orderEntity = new OrderEntity();
        orderEntity.setOrderId(100100L)
                .setCost(new BigDecimal(12580.110))
                .setOrderName("you_are_right")
                .setOrderStatus(OrderEntity.OrderStatus.ACTIVE)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        bankEntity = new BankEntity();
        bankEntity.setId(9527L)
                .setBankCode("ABC")
                .setBankName("中国人民银行")
                .setEffective(Boolean.TRUE)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
    }

    /**
     * JDK序列化测试
     */
    @Test
    public void byJdk() throws Exception {
        // 序列化测试
        byte[] bytes = SerializationUtil.JDK.serialize(orderEntity);
        System.out.println("JDK序列化结果：" + bytes.length);

        // 反序列化测试
        Object object = SerializationUtil.JDK.deserialize(bytes);
        System.out.println("JDK反序列化结果：" + object);
    }


    /**
     * JDK 序列化, 如果父类未实现{@link java.io.Serializable}而子类实现了,
     * 那么在反序列化的时候, 父类的属性会以默认值展示
     */
    @Test
    public void byJdk2() throws Exception {
        Son son = new Son('男', 70.23, 25, "沈燕明");
        // 序列化测试
        byte[] bytes = SerializationUtil.JDK.serialize(son);
        System.out.println("JDK序列化结果：" + bytes.length);

        // 反序列化测试
        Object object = SerializationUtil.JDK.deserialize(bytes);
        System.out.println("JDK反序列化结果：" + object);
    }


    /**
     * Hessian序列化测试
     */
    @Test
    public void byHessian() throws IOException {
        // 序列化
        byte[] bytes = SerializationUtil.Hessian.serialize(orderEntity);
        System.out.println("Hessian序列化结果：" + bytes.length);

        // 反序列化
        Object object = SerializationUtil.Hessian.deserialize(bytes);
        System.out.println("Hessian反序列化结果：" + object);
    }


    /**
     * Kryo序列化测试
     */
    @Test
    public void byKryo() {
        // 序列化
        byte[] bytes = SerializationUtil._Kryo.serialize(bankEntity);
        System.out.println("Kryo序列化结果：" + bytes.length);

        // 反序列化
        BankEntity entity = SerializationUtil._Kryo.deserialize(bytes, BankEntity.class);
        System.out.println("Kryo反序列化结果：" + entity);
    }


    /**
     * XStream序列化测试
     */
    @Test
    public void byXStream() {
        // 序列化
        byte[] bytes = SerializationUtil._XStream.serialize(bankEntity);
        System.out.println("XStream序列化结果：" + bytes.length);

        // 反序列化
        Object entity = SerializationUtil._XStream.deSerialize(bytes);
        System.out.println("XStream反序列化结果：" + entity);
    }


    /**
     * XStream序列化测试, 使用 xml.
     */
    @Test
    public void ByXStream2(){
        // 序列化
        String xml = SerializationUtil._XStream.toXml(bankEntity);
        System.out.println("XStream序列化结果：\n" + xml);

        // 反序列化
        Object entity = SerializationUtil._XStream.fromXml(xml);
        System.out.println("XStream反序列化结果：" + entity);
    }
}
