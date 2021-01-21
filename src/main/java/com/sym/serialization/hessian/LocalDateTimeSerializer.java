package com.sym.serialization.hessian;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Hessian序列化java8的日期Api会抛出SOF异常, 自定义序列化器处理它
 *
 * @author shenyanming
 * Created on 2020/5/20 11:52
 */
public class LocalDateTimeSerializer extends AbstractSerializer {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if(null == obj){
            out.writeNull();
            return;
        }
        LocalDateTime localDateTime = (LocalDateTime)obj;
        out.writeString(dateTimeFormatter.format(localDateTime));
    }
}
