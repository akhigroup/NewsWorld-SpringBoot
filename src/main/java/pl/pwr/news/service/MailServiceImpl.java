package pl.pwr.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Rafal on 2016-02-29.
 */
public class MailServiceImpl implements MailService {


    @Autowired
    private JavaMailSender javaMailSender;



    @Override
    public void sendSimpleMessage() {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo("rkpieniazek@gmail.com");
            helper.setFrom("noreply@news@world");
            helper.setSubject("wiadomosc testowa");
            helper.setText("wiadomosc testowa, tresc",true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
        }
        javaMailSender.send(mail);


    }
}
