package pl.pwr.news.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Rafal on 2016-02-29.
 */
@Service
public class MailServiceImpl implements MailService {


    @Autowired
    private JavaMailSenderImpl javaMailSender;



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
