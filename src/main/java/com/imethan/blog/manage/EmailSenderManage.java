package com.imethan.blog.manage;

import com.imethan.blog.common.Constant;
import com.imethan.blog.pojo.dto.ResultDto;
import com.imethan.blog.service.SettingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * @Name EmailServiceImpl
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-12 16:05
 */
@Service
@Log4j2
public class EmailSenderManage {

    @Autowired
    private SettingService settingService;

    private JavaMailSenderImpl mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    private String from;

    private String getByName(String name) {
        return settingService.findByModuleAndName(Constant.SETTING_MODULE_EMAIL, name);
    }

    private void initConfig() {
        mailSender = new JavaMailSenderImpl();

        String host = getByName("host");
        mailSender.setHost(host);

        //端口是465或者587
        int port = Integer.valueOf(getByName("port"));
        mailSender.setPort(port);

        String username = getByName("username");
        mailSender.setUsername(username);

        String password = getByName("password");
        mailSender.setPassword(password);

        mailSender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        mailSender.setJavaMailProperties(p);

        from = getByName("from");

        log.info("host={},port={},username={},password={},from={}", host, port, username, password, from);
    }

    /**
     * 发送文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {

        initConfig();

        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public ResultDto sendHtmlMail(String to, String subject, String content) {
        initConfig();

        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            log.info("邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
            return ResultDto.ReturnFail(e.getMessage());
        }
        return ResultDto.ReturnSuccess();
    }

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        initConfig();

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            log.warn("已经发送邮件,to={},subject={},content={}", to, subject, content);
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }
}
