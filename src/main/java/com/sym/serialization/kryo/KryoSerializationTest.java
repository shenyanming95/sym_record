package com.sym.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sym.serialization.entity.OrderEntity;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author shenyanming
 * Created on 2020/5/20 08:52
 */
public class KryoSerializationTest {

    private byte[] bytes;

    @Test
    public void serialize(){
        Kryo kryo = new Kryo();
        OrderEntity entity = new OrderEntity();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeObject(output, entity);
        output.flush();
        bytes = bos.toByteArray();
        System.out.println(new String(bytes));
    }

    @Test
    public void deserialize(){
        Kryo kryo = new Kryo();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Input input = new Input(bis);
        OrderEntity entity = kryo.readObject(input, OrderEntity.class);
        System.out.println(entity);
    }
}
