package com.sym.serializable;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 不同序列化工具的简单使用方式
 * <p>
 * Created by shenym on 2019/12/24.
 */
public class SerializationUtil {

    /**
     * 使用 JDK 来完成实体类的序列化, 要求实体类一定要实现 {@link java.io.Serializable}
     * <p>
     * 序列化   :{@link ObjectOutputStream#writeObject(Object)}可以将对象序列化成字节数组
     * 反序列化 :{@link ObjectInputStream#readObject()}可以将字节数组反序列化成对象
     */
    public static class JDK {
        /**
         * 序列化
         */
        public static byte[] serialize(Serializable entity) throws IOException {
            assert entity != null;
            // ByteArrayOutputStream 可以将数据以字节数组的形式输出到内存上; ObjectOutputStream 可以将一个实体类序列化到指定的输出流上
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                // 序列化实体类到指定输出流上..
                oos.writeObject(entity);
                oos.flush();
                return bos.toByteArray();
            }
        }

        /**
         * 反序列化
         */
        public static Object deserialize(byte[] bytes) throws Exception {
            assert bytes != null && bytes.length > 0;
            // ByteArrayInputStream 可以读取内存中的字节数组; ObjectInputStream 可以从输入流中反序列化字节数组
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bis)) {
                // 反序列化实体类
                return ois.readObject();
            }
        }
    }


    /**
     * 使用第三方框架 Hessian 实现对象的序列化和反序列化, 注意序列化的对象必须实现{@link Serializable}
     * <p>
     * 序列化   :{@link HessianOutput#writeObject(Object)}可以将对象序列化成字节数组
     * 反序列化 :{@link HessianInput#readObject()}可以将字节数组反序列化成对象
     */
    public static class Hessian {
        /**
         * 序列化
         */
        public static byte[] serialize(Object o) throws IOException {
            assert null != o;
            ByteArrayOutputStream bos = null;
            HessianOutput hessianOutput = null;
            try {
                bos = new ByteArrayOutputStream();
                hessianOutput = new HessianOutput(bos);
                hessianOutput.writeObject(o);
                hessianOutput.flush();
                return bos.toByteArray();
            } finally {
                if (null != bos) bos.close();
                if (null != hessianOutput) hessianOutput.close();
            }
        }
        /**
         * 反序列化
         */
        public static Object deserialize(byte[] bytes) throws IOException {
            assert null != bytes && bytes.length > 0;
            HessianInput hessianInput = null;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
                hessianInput = new HessianInput(bis);
                return hessianInput.readObject();
            } finally {
                if (null != hessianInput) hessianInput.close();
            }
        }
    }


    /**
     * 使用第三方框架 Kryo 实现对象的序列化和反序列化, 注意Kryo不是线程安全
     * <p>
     * 序列化   :{@link Kryo#writeObject(Output, Object)}可以将对象序列化成字节数组
     * 反序列化 :{@link Kryo#readObject(Input, Class)}可以将字节数组反序列化成对象
     */
    public static class _Kryo {
        /**
         * 序列化
         */
        public static byte[] serialize(Object o) {
            assert null != o;
            Kryo kryo = new Kryo();
            Output output = null;
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                output = new Output(bos);
                kryo.writeObject(output, o);
                output.flush();
                return bos.toByteArray();
            } finally {
                // 关闭 output 会连带关闭底层的流
                if (null != output) output.close();
            }
        }

        /**
         * 反序列化对象
         */
        public static <T> T deserialize(byte[] bytes, Class<T> type) {
            assert null != bytes && bytes.length > 0;
            Kryo kryo = new Kryo();
            Input input = null;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                input = new Input(bis);
                return kryo.readObject(input, type);
            } finally {
                // 关闭 input 会连带关闭底层的流
                if (null != input) input.close();
            }
        }
    }


    /**
     * 使用第三方框架 XStream 实现对象的序列化和反序列化, XStream是线程安全的。
     * <p>
     * 序列化   :{@link XStream#toXML(Object, OutputStream)}可以将对象序列化成字节数组
     * 反序列化 :{@link XStream#fromXML(InputStream)}可以将字节数组反序列化成对象
     */
    public static class _XStream {
        // XStream 是线程安全的
        private static XStream xStream = new XStream(new DomDriver());
        // XML的前缀, 如果需要将对象序列化成xml格式, 就需要加上这个前缀
        private final static String XML_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        /**
         * 序列化对象
         */
        public static byte[] serialize(Object o) {
            assert null != o;
            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream();
                xStream.toXML(o, bos);
                return bos.toByteArray();
            } finally {
                if (null != bos) {
                    try {
                        bos.close();
                    } catch (IOException ignoreException) {
                        // ignore
                    }
                }
            }
        }

        /**
         * 反序列化对象
         */
        public static Object deSerialize(byte[] bytes) {
            assert null != bytes && bytes.length > 0;
            ByteArrayInputStream bis = null;
            try {
                bis = new ByteArrayInputStream(bytes);
                return xStream.fromXML(bis);
            } finally {
                if (null != bis) {
                    try {
                        bis.close();
                    } catch (IOException ignoreException) {
                        // ignore
                    }
                }
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
        public static Object fromXml(String xml) {
            return xStream.fromXML(xml);
        }
    }

}
