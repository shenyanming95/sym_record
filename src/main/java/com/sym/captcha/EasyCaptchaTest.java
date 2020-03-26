package com.sym.captcha;

import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 基于第三方工具的验证码, 详情看：
 * https://gitee.com/whvse/EasyCaptcha
 * https://github.com/whvcse/EasyCaptcha
 *
 * @author shenym
 * @date 2020/2/8 20:48
 */

public class EasyCaptchaTest {

    /**
     * 手动设置图片属性, 然后输出
     */
    @Test
    public void simpleTest() throws FileNotFoundException {
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


    @Test
    public void pngTest() {
        // png类型
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        // 获取验证码的字符
        captcha.text();
    }

    @Test
    public void gifTest() throws FileNotFoundException {
        // gif类型
        GifCaptcha captcha = new GifCaptcha(130, 48);
        System.out.println(captcha.text());
    }

    @Test
    public void chineseTest() throws IOException, FontFormatException {
        // 中文类型
        ChineseCaptcha captcha = new ChineseCaptcha(150, 48);
        // 设置系统字体
        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        captcha.setLen(6);
        captcha.out(new FileOutputStream(new File("easy-captcha.jpg")));
    }

    @Test
    public void chineseGifTest() {
        // 中文gif类型
        ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48);
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
