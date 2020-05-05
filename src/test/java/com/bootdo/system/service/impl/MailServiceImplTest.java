package com.bootdo.system.service.impl;

import com.bootdo.system.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private MailService service;

    @Test
    public void sendSimpleMail() {
        service.sendSimpleMail("865756596@qq.com",  "这是一封测试邮件","测试");
    }

    @Test
    public void sendHtmlMail()throws MessagingException {
        String content = new StringBuffer("<html>\n")
                .append("<body>\n")
                .append("<h3> hello world </h3>\n")
                .append("</body>\n")
                .append("</html>").toString();
        service.sendHtmlMail("865756596@qq.com",  "这是一封html邮件",content);
    }

    @Test
    public void sendAttachmentsMail() throws MessagingException {
        String filepath = "C:\\Users\\Andy-J\\Desktop\\JAVA\\姜啸Java.docx";
        service.sendAttachmentsMail("865756596@qq.com",  "这是一封带有附件邮件","请看附件",filepath);
    }

    @Test
    public void sendInLineResMail() throws MessagingException{
        String imgpath = "C:\\in.jpg";
        String srcId = "in";
        String content = new StringBuffer("<html>\n")
                .append("<body>\n")
                .append("<img src=\'cid="+srcId+"\'></img> ")
                .append("</body>\n")
                .append("</html>").toString();
        service.sendInLineResMail("865756596@qq.com",  "这是一封带有图片邮件",content,imgpath,srcId);
    }
}