package pl.pwr.news.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Created by Rafal on 2016-02-29.
 */
@Service
public class MailServiceImpl implements MailService {


    @Autowired
    private JavaMailSender mailSender;



    @Override
    public void sendSimpleMessage() {


        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("rkpieniazek@gmail.com");

        msg.setText("wiadomosc testowa, tresc");
        msg.setSubject("subject");
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {

            System.err.println(ex.getMessage());
        }


//        MimeMessage mail = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//            helper.setTo("rkpieniazek@gmail.com");
//            helper.setFrom("noreply@newsAtworld");
//            helper.setSubject("wiadomosc testowa");
//            helper.setText("wiadomosc testowa, tresc",false);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        mailSender.send(mail);
    }
}
