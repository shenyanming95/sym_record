package com.sym.captcha;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * 使用Java自带的api进行原始作画
 *
 * @author shenym
 * @date 2020/2/8 20:45
 */

public class OriginDrawTest {

    @Test
    public void drawTest() throws IOException {
        //图像数据缓冲区，生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便的操作这个图片
        BufferedImage bi = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
        //绘制图片
        Graphics img = bi.getGraphics();
        //设置颜色
        Color c = new Color(200, 150, 255);
        img.setColor(c);
        //画图片的背景
        img.fillRect(0, 0, 68, 22);

        //构造随机数的数组
        char[] charArr = "ABCDEFGHIJKLMNOPQRSTUVWSYZ0123456789".toCharArray();
        int len = charArr.length;
        int index = 0;

        // 取随机数
        SecureRandom random = new SecureRandom();
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            //获得随机下标
            index = random.nextInt(len);
            //每个验证码的颜色随机
            img.setColor(new Color(random.nextInt(88), random.nextInt(188), random.nextInt(255)));
            //将随机数画到图片中
            img.drawString(charArr[index] + "", (i * 15) + 3, 18);
            //保存验证码
            randomCode.append(charArr[index]);
        }
        // 写到输出流上, 如果是web环境, 就获取Response的输出流
        ImageIO.write(bi, "jpg", new FileImageOutputStream(new File("temp.jpg")));
    }
}
