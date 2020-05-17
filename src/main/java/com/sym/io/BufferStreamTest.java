package com.sym.io;

import com.sym.io.path.PathResolver;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;

/**
 * 字符流，Reader相当于输入流，Writer相当于输出流
 *
 * @author shenyanming
 * @date 2019/5/9 17:18
 */
public class BufferStreamTest {

    /**
     * 有办法获取到源码目录的文件地址吗？
     */
    public final static String PICONSRCPATH = "E:\\idea_workspace\\java测试\\src\\main\\resources\\pic\\";

    private PathResolver pathResolver = new PathResolver();

    /**
     * 文件输入字符流
     */
    @Test
    public void fileReaderTest() throws IOException {
        // 先获取文件路径
        String realpath = pathResolver.getRealPath("pic/lol.txt");
        System.out.println("文件磁盘地址："+realpath);
        // 获取文件输入字符流
        FileReader fileReader = new FileReader(realpath);
        System.out.println("文件编码格式："+fileReader.getEncoding());
        // 读取字符流的数据
        int temp = 0;
        StringBuilder s = new StringBuilder();
        /*
         * read()方法每次从输入流读取一个字符，并且以整数形式返回它，当流结束的时候返回-1
         */
        while( (temp = fileReader.read()) != -1 ){
            s.append((char) temp);
        }
        System.out.println(s);
    }

    /**
     * 文件输出字符流
     *
     * 文件输出字符流可以将数据输出到指定文件去，可以选择覆盖文件原本的内容，也可以选择拼接文件原本的内容；
     * 不过有一点要注意，如果最后不使用flush()或close()方法，数据是不会被写入到文件中的。
     */
    @Test
    public void fileReaderTest2() throws IOException{
        // 字符输出流跟一个文件File关联，表示数据保存到那个文件File上，append=true表示不会覆盖原文件内容，而是拼接到原文件内容后面
        FileWriter fileWriter = new FileWriter(PICONSRCPATH+"lol.txt",true);
        System.out.println("文件编码为："+fileWriter.getEncoding());
        // 使用write()写入数据, 换下一行拼接
        fileWriter.write("\r面朝大海，春暖花开");
        // 切记，不使用flush()或者close()方法，数据是写不进去的
        fileWriter.flush();
    }


    /**
     * 字符串输入字符流()，字符串字符流比较简单
     *
     * 元数据是一个字符串，可以对字符串做字符处理
     * @throws IOException
     */
    @Test
    public void StringReaderTest() throws IOException{

        // 将一个字符串转变为一个字符串输入字符流
        StringReader stringReader = new StringReader("面朝大海，春暖花开");
        // 读取字符流内的所有数据
        int i = 0;
        while( (i = stringReader.read()) != -1 ){
            System.out.print((char)i);
        }
        // 注意：流中的数据只能读取一遍，读完就没了
        // 字符数组里面没有任何数据
        char[] chars = new char[10];
        stringReader.read(chars);
        for( char c : chars ){
            System.out.print(c);
        }
    }

    /**
     * 带有缓冲功能的输入字符流
     *
     * bufferReader是带有缓冲功能的，它可以按行读取文件内容
     */
    @Test
    public void bufferReaderTest() throws IOException{
        // 一个reader只能用一次，操作完以后，流里面的数据就没有了
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(PICONSRCPATH+"lol.txt"));
        BufferedReader bufferedReade2 = new BufferedReader(new FileReader(PICONSRCPATH+"lol.txt"));
        /*
         * 转换为stream对象
         */
        Stream<String> lines = bufferedReader1.lines();
        lines.forEach(System.out::println);
        System.out.println();
        /*
         * 按行读取文件的内容
         */
        String s = "";
        while( (s = bufferedReade2.readLine()) != null ){
            System.out.println(s);
        }
    }

}
