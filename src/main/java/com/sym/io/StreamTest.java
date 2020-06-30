package com.sym.io;

import com.sym.io.path.PathResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 字节流.
 * 你要明白一点，输入流是从外部将数据读到JVM内存中来，输出流是将数据输出到外部载体中，输入输出的判断参照的是JVM
 *
 * @author shenyanming
 * @Date: 2019-03-07 16:10
 */
public class StreamTest implements Serializable {

    private PathResolver pathResolver = new PathResolver();

    /**
     * 当 InputStream 关闭的时候，就不能再继续操作 InputStream
     */
    @Test
    public void inputStreamCloseTest() throws Exception {

        InputStream inputStream = new FileInputStream(new File("base.txt"));
        System.out.println(inputStream.available());
        inputStream.close();
        System.out.println(inputStream.available());
    }


    /**
     * 当 InputStreamReader 关闭了，就不能继续再操作 InputStreamReader
     */
    @Test
    public void inputStreamReaderCloseTest() throws Exception {
        InputStream inputStream = new FileInputStream(new File("base.txt"));
        InputStreamReader reader = new InputStreamReader(inputStream);
        System.out.println(reader.ready());
        reader.close();
        System.out.println(reader.ready());
    }

    /**
     * 如果 InputStream 关闭了，InputStreamReader还可以继续使用
     */
    @Test
    public void streamInnerCloseTest() throws Exception {
        InputStream inputStream = new FileInputStream(new File("base.jpg"));
        InputStreamReader reader = new InputStreamReader(inputStream);
        System.out.println(reader.read());
        inputStream.close();
        System.out.println(reader.read());
    }


    /**
     * 如果 InputStreamReader 关闭了，InputStream就不能使用了
     * 所以，对于这种IO操作，只需要关闭最外层的IO操作对象就行了
     */
    @Test
    public void streamOuterCloseTest() throws Exception {
        InputStream inputStream = new FileInputStream(new File("F:/1.jpg"));
        InputStreamReader reader = new InputStreamReader(inputStream);
        System.out.println(inputStream.available());
        reader.close();
        System.out.println(inputStream.available());
    }

    /**
     * 文件输入流FileInputStream
     */
    @Test
    public void fileInputStreamTest() throws IOException {
        // 先获取文件真实路径
        String realPath = pathResolver.getRealPath("pic/lol.txt");
        System.out.println("文件磁盘地址：" + realPath);
        // 通过File对象获取一个文件输入流
        FileInputStream fis = new FileInputStream(new File(realPath));
        System.out.println("文件大小" + fis.available());
        // 读取FileInputStream的内容
        //创建一个临时的字节数组缓冲区用来接收字节流的数据
        byte[] temp = new byte[10];
        int i = 0;
        StringBuilder s = new StringBuilder();
        while ((i = fis.read(temp)) != -1) {
            System.out.println("读取字节：" + i);
            s.append(new String(temp, StandardCharsets.UTF_8));
        }
        System.out.println(s);
        System.out.println(fis.available());
    }


    /**
     * 文件输出流FileOutputStream
     * read()方法每次返回已读取的字节数
     */
    @Test
    public void fileOutputStreamTest() throws IOException {
        // 先获取文件路径
        String realPath = pathResolver.getRealPath("pic/lol.txt");
        System.out.println("文件磁盘地址：" + realPath);
        // 通过File对象获取一个文件输入流
        FileInputStream fis = new FileInputStream(new File(realPath));
        // 创建文件输出流用于将文件拷贝到指定目录上
        FileOutputStream fos = new FileOutputStream(new File("E:/test/英雄联盟.txt"));
        byte[] temp = new byte[1024];
        int index = 0;
        //输入流先将1024字节的数据放到temp数组上，然后输出流将temp数据写入，重复执行这个过程直至输入流的数据全读完
        while ((index = fis.read(temp)) != -1) {
            fos.write(temp, 0, index);
        }
        fos.flush();
        // 记得关掉流
    }


    /**
     * 对象输出流可以序列化对象，但是它需要借助其它的输出流，例如文件输出流或者数组输出流
     * ObjectOutputStream
     */
    @Test
    public void objectOutputStreamTest() throws IOException {
        // 创建一个可序列化的对象
        IoObject obj = new IoObject(1, "sym", 173.5);

        // 使用对象输出流，必须依赖于一个输出流，它可以是文件输出流FileOutputSteam，也可以是数组输出流ByteArrayOutputStream
        FileOutputStream fos = new FileOutputStream("E:/test/IoObject.txt");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 根据上面两个输出流，创建出对象输出流
        ObjectOutputStream oosByFile = new ObjectOutputStream(fos);
        ObjectOutputStream oosByArray = new ObjectOutputStream(bos);

        // 将对象写到输出流上
        oosByFile.writeObject(obj);
        oosByArray.writeObject(obj);

        // 文件流直接写到磁盘上
        fos.flush();
        // 数组流可以将对象转换成字节数组
        byte[] array = bos.toByteArray();
        System.out.println("对象字节数组大小：" + array.length);
    }


    /**
     * 对象输入流，可以从外部将数据反序列化成一个对象
     */
    @Test
    public void objectInputStreamTest() throws IOException, ClassNotFoundException {
        // 创建一个文件输入流获取数据
        FileInputStream fis = new FileInputStream("E:/test/IoObject.txt");
        // 根据文件输入流，可以创建一个对象输入流
        ObjectInputStream ois = new ObjectInputStream(fis);
        System.out.println(ois.readObject());
    }


    /**
     * 包装流 PrintStream
     */
    @Test
    public void printStreamTest() throws IOException, InterruptedException {
        // 用来将数据输出到指定文件中
        FileOutputStream fos = new FileOutputStream("E:/test/obj.txt");
        // 包装流，将数据流写到fos流中
        PrintStream ps = new PrintStream(fos);
        // 准备数据
        IoObject obj = new IoObject(1, "sym", 173.5);
        //文件先写入6666
        ps.println("6666");
        Thread.sleep(8000);
        // 过了8s后，再写入对象数据
        ps.println(obj);
    }


    /**
     * 数组输入流 byteArrayInputStream 可以将一个数组转换成输入流
     */
    @Test
    public void byteArrayInputStreamTest() throws IOException {
        // 创建字节数组，然后再创建数组输入流
        byte[] byteArray = "Hello!World!...".getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        //读取数组输入流里面的数据，重新将其还原成字符串
        byte[] temp = new byte[77];
        StringBuilder sb = new StringBuilder();
        while (bis.read(temp) != -1) {
            sb.append(new String(temp));
        }
        System.out.println(sb.toString());
    }


    /**
     * 数组输出流 ByteArrayOutputStream 可以将数据写入到它自身内
     */
    @Test
    public void byteArrayOutputStreamTest() throws IOException {
        // 创建数组输出流不需要额外的参数
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // 通过write()方法将数据写入到bos流中
        System.out.println("初始化时，bos的大小为：" + bos.size());
        byte[] data = "IO输入输出".getBytes();
        //此时会把data数组的数据写入到bos数组输出流中
        bos.write(data);
        System.out.println("write()后,bos的大小为：" + bos.size());
        // 可以将数组输出流的数据写入到其它输出流中，比如说文件输出流
        FileOutputStream fos = new FileOutputStream("E:/test/arrayData.txt");
        bos.writeTo(fos);
        fos.flush();
    }


    /**
     * 我们上传文件时经常会用到available来读取字节，能知道有多少个字节需要读取，这个方法从本地文件读取数据时一般不会出现问题，
     * 但是通过网路传输就会出现数据传输不完整的情况，因为网络通讯是间断性的一串字节往往分几批进行发送。本地程序调用available()方法
     * 有时得到0，这可能是对方还没有响应，也可能是对方已经响应了，但是数据还没有送达本地。对方发送了9000个字节给你，也许分成3批到达，
     * 这你就要调用3次available()方法才能将数据总数全部得到。
     */
    public void availableTest() {

    }


    /**
     * 内部对象
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IoObject implements Serializable {
        private int id;
        private String name;
        private double height;
    }


}
