package com.bootdo.system.service;

import javax.mail.MessagingException;

public interface MailService {

    //文本
    void sendSimpleMail(String to, String subject, String content);
    //html
    void sendHtmlMail(String to, String subject, String content) throws MessagingException;
    //附件
    void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException;
    //图片
    void sendInLineResMail(String to, String subject, String content, String resPath, String rscId) throws MessagingException;

}
