package net.pccw.uc.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class MailBox {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailBox.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${app.mail.subject}")
    private String subject;

    @Value("${app.mail.to}")
    private String to;

    public void sendHtmlMail(String content){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        String mailSubject = subject;
        String[] toArray = null;

        try {
            helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setFrom(from);

            helper.setSubject(mailSubject);
            helper.setText(content, true);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

        mailSender.send(message);
    }

}
