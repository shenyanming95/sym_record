package com.sym.serialization;

import com.caucho.hessian.io.*;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.dozer.DozerBeanMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * 序列化工具
 *
 * @author shenyanming
 * @date 2019/12/24
 */
public class SerializationUtil {

    private static DozerBeanMapper mapper;
    private static SerializerFactory serializerFactory;

    static {
        // Dozer不支持java8时间api, 需要对其增强
        List<String> mappingFileUrls = Collections.singletonList("dozerJdk8Converters.xml");
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingFileUrls);

        // Hessian也不支持java8时间api，也需要对其增强
        ExtSerializerFactory extSerializerFactory = new ExtSerializerFactory();
        extSerializerFactory.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        extSerializerFactory.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        serializerFactory = new SerializerFactory();
        serializerFactory.addFactory(extSerializerFactory);
    }

    /**
     * 使用 JDK 来完成实体类的序列化, 要求实体类一定要实现 {@link Serializable}
     * 序列化   :{@link ObjectOutputStream#writeObject(Object)}可以将对象序列化成字节数组
     * 反序列化 :{@link ObjectInputStream#readObject()}可以将字节数组反序列化成对象
     */
    public static class Jdk {
        /**
         * 序列化
         */
        public static byte[] serialize(Serializable entity) throws IOException {
            assert null != entity;
            // ByteArrayOutputStream 可以将数据以字节数组的形式输出到内存上;
            // ObjectOutputStream 可以将一个实体类序列化到指定的输出流上
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                // 序列化实体类到指定输出流上..
                oos.writeObject(entity);
                oos.flush();
                return bos.toByteArray();
            }
        }

        /**
         * 反序列化
         */
        public static <T> T deserialize(byte[] bytes, Class<T> t) throws Exception {
            assert bytes != null && bytes.length > 0;
            assert null != t;
            // ByteArrayInputStream 可以读取内存中的字节数组;
            // ObjectInputStream 可以从输入流中反序列化字节数组;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                // 反序列化实体类
                Object object = ois.readObject();
                return mapper.map(object, t);
            }
        }
    }


    /**
     * 使用第三方框架 Hessian 实现对象的序列化和反序列化, 注意序列化的对象必须实现{@link Serializable}
     * 序列化   :{@link HessianOutput#writeObject(Object)}可以将对象序列化成字节数组
     * 反序列化 :{@link HessianInput#readObject()}可以将字节数组反序列化成对象
     */
    public static class Hessian {
        /**
         * 序列化
         */
        public static byte[] serialize(Object o) throws IOException {
            assert null != o;
            Hessian2Output hessian2Output = null;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                hessian2Output = new Hessian2Output(bos);
                hessian2Output.setSerializerFactory(serializerFactory);
                hessian2Output.writeObject(o);
                hessian2Output.flush();
                return bos.toByteArray();
            } finally {
                if (null != hessian2Output) {
                    hessian2Output.close();
                }
            }
        }

        /**
         * 反序列化
         */
        public static <T> T deserialize(byte[] bytes, Class<T> t) throws IOException {
            assert null != bytes && bytes.length > 0;
            assert null != t;
            Hessian2Input hessian2Input = null;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
                hessian2Input = new Hessian2Input(bis);
                hessian2Input.setSerializerFactory(serializerFactory);
                Object object = hessian2Input.readObject();
                return mapper.map(object, t);
            } finally {
                if (null != hessian2Input) {
                    hessian2Input.close();
                }
            }
        }
    }


    /**
     * 使用第三方框架 Kryo 实现对象的序列化和反序列化, 注意Kryo不是线程安全
     * 序列化   :{@link com.esotericsoftware.kryo.Kryo#writeObject(Output, Object)}可以将对象序列化成字节数组
     * 反序列化 :{@link com.esotericsoftware.kryo.Kryo#readObject(Input, Class)}可以将字节数组反序列化成对象
     */
    public static class Kryo {
        /**
         * 序列化
         */
        public static byte[] serialize(Object o) {
            assert null != o;
            com.esotericsoftware.kryo.Kryo kryo = new com.esotericsoftware.kryo.Kryo();
            Output output = null;
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                output = new Output(bos);
                kryo.writeObject(output, o);
                output.flush();
                return bos.toByteArray();
            } finally {
                if (null != output) {
                    // 关闭 output 会连带关闭底层的流
                    output.close();
                }
            }
        }

        /**
         * 反序列化对象
         */
        public static <T> T deserialize(byte[] bytes, Class<T> type) {
            assert null != bytes && bytes.length > 0;
            com.esotericsoftware.kryo.Kryo kryo = new com.esotericsoftware.kryo.Kryo();
            Input input = null;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                input = new Input(bis);
                return kryo.readObject(input, type);
            } finally {
                if (null != input) {
                    // 关闭 input 会连带关闭底层的流
                    input.close();
                }
            }
        }
    }


    /**
     * 使用第三方框架 XStream 实现对象的序列化和反序列化, XStream是线程安全的。
     * <p>
     * 序列化   :{@link com.thoughtworks.xstream.XStream#toXML(Object, OutputStream)}可以将对象序列化成字节数组
     * 反序列化 :{@link com.thoughtworks.xstream.XStream#fromXML(InputStream)}可以将字节数组反序列化成对象
     */
    public static class XStream {
        // XStream 是线程安全的
        private static com.thoughtworks.xstream.XStream xStream = new com.thoughtworks.xstream.XStream(new DomDriver());

        // XML的前缀, 如果需要将对象序列化成xml格式, 就需要加上这个前缀
        private final static String XML_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        /**
         * 序列化对象
         */
        public static byte[] serialize(Object o) {
            assert null != o;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                xStream.toXML(o, bos);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 反序列化对象
         */
        public static <T> T deSerialize(byte[] bytes, Class<T> t) {
            assert null != bytes && bytes.length > 0;
            assert null != t;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
                Object object = xStream.fromXML(bis);
                return mapper.map(object, t);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 序列化成xml
         */
        public static String toXml(Object o) {
            return XML_PREFIX + xStream.toXML(o);
        }

        public static String toXml(Object o, Class<?> type) {
            // 默认根目录标签会起 实体类的全类名,
            xStream.alias(type.getSimpleName(), type);
            return toXml(o);
        }

        /**
         * 反序列化成xml
         */
        public static <T> T fromXml(String xml, Class<T> t) {
            return mapper.map(xStream.fromXML(xml), t);
        }
    }

    /**
     * Hessian序列化java8的日期Api会抛出SOF异常, 自定义序列化器处理它
     *
     * @author shenyanming
     * Created on 2020/5/20 11:52
     */
    public static class LocalDateTimeSerializer extends AbstractSerializer {

        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
            if (null == obj) {
                out.writeNull();
                return;
            }
            LocalDateTime localDateTime = (LocalDateTime) obj;
            out.writeString(dateTimeFormatter.format(localDateTime));
        }
    }

    /**
     * Hessian序列化java8的日期Api会抛出SOF异常, 自定义序列化器处理它
     *
     * @author shenyanming
     * Created on 2020/5/20 16:40
     */
    public static class LocalDateTimeDeserializer extends AbstractDeserializer {

        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        @Override
        public Object readObject(AbstractHessianInput in) throws IOException {
            String string = in.readString();
            try {
                return LocalDateTime.parse(string, dateTimeFormatter);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public Class<?> getType() {
            return LocalDateTime.class;
        }

    }

}
