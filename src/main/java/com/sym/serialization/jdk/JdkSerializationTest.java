package com.sym.serialization.jdk;

import com.sym.serialization.entity.OrderEntity;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * JDK序列化
 *
 * @author shenyanming
 * Created on 2020/5/20 08:36
 */
public class JdkSerializationTest {

    private byte[] bytes;

    /**
     * 序列化, 省略流关闭
     */
    @Test
    public void serialize() throws IOException {
        OrderEntity orderEntity = new OrderEntity();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(orderEntity);
        oos.flush();
        bytes = bos.toByteArray();
        System.out.println(new String(bytes));
    }

    /**
     * 反序列化, 省略流关闭
     */
    @Test
    public void deserialize() throws IOException, ClassNotFoundException {
        // 先执行序列化
        this.serialize();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        OrderEntity entity = (OrderEntity)ois.readObject();
        System.out.println(entity);
    }
}
