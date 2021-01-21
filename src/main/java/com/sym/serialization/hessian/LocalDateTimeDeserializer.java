package com.sym.serialization.hessian;

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  Hessian序列化java8的日期Api会抛出SOF异常, 自定义序列化器处理它
 *
 * @author shenyanming
 * Created on 2020/5/20 16:40
 */
public class LocalDateTimeDeserializer extends AbstractDeserializer {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public Object readObject(AbstractHessianInput in) throws IOException {
        String string = in.readString();
        try {
            return LocalDateTime.parse(string, dateTimeFormatter);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Class<?> getType() {
        return LocalDateTime.class;
    }

}
