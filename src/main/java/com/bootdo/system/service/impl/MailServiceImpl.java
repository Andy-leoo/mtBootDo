package com.bootdo.system.service.impl;

import com.bootdo.system.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    private String form;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     *  文本邮件
     * @param to    发向方
     * @param subootdoect   标题
     * @param content  内容
     */
    @Override
    public void sendSimpleMail(String to, String subootdoect, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subootdoect);
        message.setText(content);
        message.setFrom(form);

        javaMailSender.send(message);
    }

    /**
     * HTML
     * @param to
     * @param subootdoect
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subootdoect, String content) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setTo(to);
        helper.setSubject(subootdoect);
        helper.setText(content,true);
        helper.setFrom(form);

        javaMailSender.send(mimeMessage);
    }

    /**
     * 带有附件的邮件
     * @param to
     * @param subootdoect
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    @Override
    public void sendAttachmentsMail(String to, String subootdoect, String content, String filePath) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setTo(to);
        helper.setSubject(subootdoect);
        helper.setText(content,true);
        helper.setFrom(form);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String filename = fileSystemResource.getFilename();
        helper.addAttachment(filename,fileSystemResource);

        javaMailSender.send(mimeMessage);
    }

    /**
     * 图片
     * @param to
     * @param subootdoect
     * @param content
     * @param resPath
     */
    @Override
    public void sendInLineResMail(String to, String subootdoect, String content, String resPath ,String rscId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setTo(to);
        helper.setSubject(subootdoect);
        helper.setText(content,true);
        helper.setFrom(form);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(resPath));
        helper.addInline(rscId,fileSystemResource);

        javaMailSender.send(mimeMessage);
    }
}
