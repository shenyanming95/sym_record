package com.sym.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * {@link File}Api使用方式
 *
 * @author shenyanming
 * @date 2019/5/9 10:59
 */
@Slf4j
public class FileApiTest {

    /**
     * File对象的判断与创建
     */
    @Test
    public void fileDirTest() throws IOException {
        // File可以创建一个文件，也可以创建一个文件夹
        File imgFile = new File("E:/test/1.jpg");
        File dirFile = new File("E:/test/dir");

        // 判断文件是否存在
        System.out.println("1.jpg文件是否存在："+imgFile.exists());
        System.out.println("dir目录是否存在："+dirFile.exists());

        // 如果文件不存在，创建它
        boolean b = imgFile.createNewFile();
        //创建目录
        b = dirFile.mkdir();

        // 判断File的类型
        System.out.println("isFile()方法判断一个File对象是否为文件："+imgFile.isFile());
        System.out.println("isDirectory()方法判断一个File对象是否为目录(即文件夹)"+dirFile.isDirectory());

        // 通过delete()方法可以删除一个文件（立即删除）
        b = imgFile.delete();
        b = dirFile.delete();
    }

    /**
     * File.mkdir()只能创建一级目录
     * File.mkdirs()可以创建多级目录
     *
     * 当只使用一个File对象创建多级目录时，这个File对象代表的是最里层的目录，所以使用delete()方法删除
     * 只会删除最里层的目录。
     */
    @Test
    public void mkdirTest(){
        String path = "E:/test/dir1/dir2/dir3";
        File dirFile = new File(path);
        log.info("因为文件夹还未创建，所以返回：{}", dirFile.isDirectory());
        boolean b = dirFile.mkdirs();
        System.out.println("通过mkdirs()可以创建多级目录，此时判断File对象是否为文件夹："+dirFile.isDirectory());
        // 如果File对象表示多级目录，调用delete()方法，只会删除最里层的目录
        b = dirFile.delete();
    }


    /**
     * 获取一个File对象的父目录
     */
    @Test
    public void fileParentTest() throws IOException {
        String path = "E:/test/2.txt";
        File file = new File(path);
        file.createNewFile();//创建新文件
        
        // 获取它的第一级父目录
        System.out.println(file.getParent());
        File parentFile_1 = file.getParentFile();
        System.out.println("一级父目录，是否存在："+parentFile_1.exists());

        // 由一级父目录获取第二级父目录
        System.out.println(parentFile_1.getParent());
        File parentFile_2 = parentFile_1.getParentFile();
        System.out.println("二级父目录，是否存在："+parentFile_2.exists());

        // 最高只能获取到磁盘目录，例如：c:/    d:/
    }


    /**
     * 获取一个目录下的所有文件：File.listFiles();
     */
    @Test
    public void getFilesAtDirTest() throws IOException {
        File dirFile = new File("F:/安装包");
        File[] files = dirFile.listFiles();
        for( File file : files ){
            System.out.println(file.getName()+" ---- 大小："+file.length()+"字节");
        }
    }


    /**
     * 重命名一个文件
     */
    @Test
    public void renameFileTest() throws IOException {
        File imgFile = new File("E:/test/1.jpg");
        if( !imgFile.exists() ){
            // 如果文件不存在，先创建
            imgFile.createNewFile();
        }
        // 获取父级目录路径
        String parentPath = imgFile.getParent();
        // 重新定义文件名称
        imgFile.renameTo(new File(parentPath+"/1-副本.jpg"));
    }

    /**
     * delete()和deleteOnExit()的区别:
     *
     * delete()会直接删除，不管文件存在不存在
     * deleteOnExit()会在JVM退出的时候才会删除，如果程序一直运行，则不会删除
     * @throws IOException
     */
    @Test
    public void deleteAndDeleteOnExitTest() throws IOException,InterruptedException{
        File f1 = new File("file1.txt");
        File f2 = new File("file2.txt");
        // 先创建空文件
        f1.createNewFile();
        f2.createNewFile();
        // 验证是否存在
        System.out.println("文件f1是否存在？"+f1.exists());
        System.out.println("文件f2是否存在？"+f2.exists());
        /*
         * f1直接调用delete()方法,它会立即被删除
         */
        f1.delete();
        //此时返回false，文件确实被删除了
        log.info("文件f1是否存在？{}", f1.exists());
        /*
         * f2直接调用deleteOnExit()方法，它只能在JVM退出后才会被删除
         */
        f2.deleteOnExit();
        //此时返回true，文件还存在，只有等程序执行完了，文件才会被删除
        log.info("文件f2是否存在？{}", f2.exists());

        Thread.sleep(5000);
    }


    /**
     * 如果使用文件流去创建一个文件，当文件流没有关闭的时候，调用delete()方法是删除不了文件（即使此时文件已经存在磁盘上了）
     */
    @Test
    public void deleteFileIfStreamAliveTest() throws IOException{
        File file = new File("file.txt");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write("天若有情天亦老".getBytes());
        System.out.println("文件是否已生成？"+file.exists());
        /*
         * 此时fos未关闭，调用delete()方法删除不了文件，方法返回false
         */
        System.out.println("文件是否成功删除？"+file.delete());

        /*
         * 必须让fos先关闭，此时调用delete()方法才可以删除文件
         */
        fos.close();
        System.out.println("文件是否成功删除？"+file.delete());
    }

}
