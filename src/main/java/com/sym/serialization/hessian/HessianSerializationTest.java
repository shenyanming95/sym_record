package com.sym.serialization.hessian;

import com.caucho.hessian.io.ExtSerializerFactory;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import com.sym.serialization.entity.OrderEntity;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author shenyanming
 * Created on 2020/5/20 08:47
 */
public class HessianSerializationTest {

    private byte[] bytes;
    private SerializerFactory serializerFactory;

    public void init(){
        // Hessian不支持java8的时间api
        ExtSerializerFactory extSerializerFactory = new ExtSerializerFactory();
        extSerializerFactory.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer());
        extSerializerFactory.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer());
        serializerFactory = new SerializerFactory();
        serializerFactory.addFactory(extSerializerFactory);
    }

    @Test
    public void serialize() throws IOException {
        OrderEntity entity = new OrderEntity();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(bos);
        hessianOutput.setSerializerFactory(serializerFactory);
        hessianOutput.writeObject(entity);
        hessianOutput.flush();
        bytes = bos.toByteArray();
        System.out.println(new String(bytes));
    }

    @Test
    public void deserialize() throws IOException {
        this.serialize();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        HessianInput hessianInput = new HessianInput(bis);
        hessianInput.setSerializerFactory(serializerFactory);
        OrderEntity entity = (OrderEntity)hessianInput.readObject();
        System.out.println(entity);
    }
}
