package com.ecnu.notehub.service;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @author onion
 * @date 2019/12/4 -7:16 下午
 */
public interface MailService {
    void sendMail(String to, String subject,String content);
    void sendHtmlMail(String to, String subject, String text, Map<String, String> attachmentMap) throws MessagingException;
}
