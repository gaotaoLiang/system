package com.demo.system.util;

import com.demo.system.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2020/11/18
 */
@Component
@Slf4j
public class SendEmailUtil {

    @Autowired
    private JavaMailSender mailSender;

//    @Value("${spring.mail.username}")
//    private String mailFrom;

    public boolean sendEmailMessages(String mailTo, String subject, String content){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(CommonConstant.EMAIL_ADDRESS);   //邮箱发送者
            mailMessage.setTo(mailTo);    //邮箱接收者
            mailMessage.setSubject(subject);   //邮箱主题
            mailMessage.setText(content);   //邮箱内容
            mailSender.send(mailMessage);
        }catch (Exception e){
            log.info("SendEmailUtil sendEmailMessages error: {}", e.getMessage(), e);
            return false;
        }
        return true;
    }
}
