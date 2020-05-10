package com.sym.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import java.net.URL;

/**
 * 基于apache软件下的commons-mail的Java邮件工具类
 * <p>
 * Created by 沈燕明 on 2018/10/8.
 */
public class CommonsMailUtil {

    public static void main(String[] args) {
        sendHtmlEmail();
    }

    /**
     * 发送简单文本邮件
     */
    public static void sendSimpleMail() {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.163.com");
            email.setSSLOnConnect(true);
            email.setSslSmtpPort("465");
            email.setAuthenticator(new DefaultAuthenticator("qaz542569199@163.com", "qaz542569199"));
            email.setFrom("qaz542569199@163.com", "沈燕明");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("542569199@qq.com");
            email.addCc("1142930719@qq.com"); //抄送
            email.setDebug(true);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带附件的邮件
     */
    public static void sendAttachEmail() {
        try {
            // 创建附件
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath("D:\\用户图片\\20.jpg");
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("Picture of John");
            attachment.setName("美女.jpg"); // 设置附件名称

            // Create the email message
            MultiPartEmail email = new MultiPartEmail();
            email.setDebug(true);
            email.setHostName("smtp.163.com");
            email.setSSLOnConnect(true);
            email.setSslSmtpPort("465");
            email.setAuthentication("qaz542569199@163.com", "qaz542569199");
            email.addTo("542569199@qq.com");
            email.setFrom("qaz542569199@163.com");
            email.setSubject("The picture");
            email.setMsg("Here is the picture you wanted");

            // add the attachment
            email.attach(attachment);

            // send the email
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送HTML格式的邮件
     */
    public static void sendHtmlEmail() {
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setDebug(true);
            email.setSSLOnConnect(true);
            email.setSslSmtpPort("465");
            email.setAuthentication("qaz542569199@163.com", "qaz542569199");
            email.setHostName("smtp.163.com");
            email.addTo("542569199@qq.com", "沈燕明");
            email.setFrom("qaz542569199@163.com");
            email.setSubject("Test email with inline image");

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = email.embed(url, "Apache logo");
            // set the html message
            email.setCharset("utf-8");
            email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\">牛逼了</html>");
            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");
            // send the email
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
