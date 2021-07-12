package com.sym.quickstart;

import cn.hutool.core.util.RandomUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Java 验证码(图片) 测试类.
 * 第三方工具的验证码, 详情看：
 * https://gitee.com/whvse/EasyCaptcha
 * https://github.com/whvcse/EasyCaptcha
 *
 * @author shenyanming
 * Create on 2021/07/07 16:56
 */
public class CaptchaTest {

    /**
     * 使用 jdk 自带api实现
     */
    @Test
    public void jdkTest() throws IOException {
        // 长宽
        int width = 89;
        int height = 30;

        // 图像数据缓冲区，生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便的操作这个图片
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 绘制图片
        Graphics img = bi.getGraphics();
        // 设置颜色
        Color c = new Color(255, 255, 255);
        img.setColor(c);
        // 画图片的背景
        img.fillRect(0, 0, width, height);
        // 设置文字格式
        img.setFont(new Font("细明本", Font.PLAIN, 20));

        // 验证码个数
        int randomCodeLength = 6;

        // 生成随机数
        StringBuilder randomCodeString = new StringBuilder();
        ThreadLocalRandom random = RandomUtil.getRandom();
        for (int i = 0; i < randomCodeLength; i++) {
            // 每个验证码的颜色随机
            img.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            // 将随机数画到图片中
            String temp = RandomUtil.randomString(1);
            img.drawString(temp, (i * 15) + 3, 18);
            //保存验证码
            randomCodeString.append(temp);
        }
        // 写到输出流上, 如果是web环境, 就获取Response的输出流
        ImageIO.write(bi, "jpg", new FileImageOutputStream(new File("temp.jpg")));
        System.out.println("验证码：" + randomCodeString.toString());
    }


    /**
     * 手动设置图片属性, 然后输出.
     *
     * @see com.wf.captcha.GifCaptcha
     */
    @Test
    public void jpgTest() throws FileNotFoundException {
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体, 有默认字体，可以不用设置
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        // 获取验证码
        String code = specCaptcha.text().toLowerCase();
        System.out.println("验证码：" + code);
        // 输出图片
        specCaptcha.out(new FileOutputStream(new File("easy-captcha.jpg")));
    }

    /**
     * @see com.wf.captcha.ChineseGifCaptcha
     */
    @Test
    public void chineseTest() throws IOException {
        // 中文类型
        ChineseCaptcha captcha = new ChineseCaptcha(150, 48);
        // 设置系统字体
        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        captcha.setLen(6);
        captcha.out(new FileOutputStream(new File("easy-captcha.jpg")));
    }

    @Test
    public void arithmeticCaptchaTest() throws FileNotFoundException {
        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(220, 48);
        // 几位数运算，默认是两位
        captcha.setLen(5);
        // 获取运算的公式：3+2=?
        System.out.println("算数公式：" + captcha.getArithmeticString());
        // 获取运算的结果：5
        System.out.println("算数结果：" + captcha.text());
        captcha.out(new FileOutputStream(new File("easy-captcha.jpg")));
    }
}
