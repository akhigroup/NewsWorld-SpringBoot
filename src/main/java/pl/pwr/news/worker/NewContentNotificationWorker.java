package pl.pwr.news.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.user.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Created by Rafal on 2016-04-21.
 */
@Component
public class NewContentNotificationWorker {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;


    @Scheduled(cron = "0 0 9 1/1 * ?")
    public void notifyUserWithPopularArticles() {

        List<Article> popular = getPopularArticles();
        List<User> userList = userService.findAll();
        for (User user : userList) {
            String message = prepareMessage(user, popular);
            sendMail(user.getEmail(), message);
        }
    }

    private List<Article> getPopularArticles() {
        Page<Article> popular = articleService.findPopular(0, 5);
        return popular.getContent();
    }

    private String prepareMessage(User user, List<Article> popular) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Witaj %s!<br>", user.getUsername()));
        builder.append("Zobacz co dziś dla Ciebie przygotowaliśmy:<br><br>");
        for (Article article : popular) {
            builder.append(String.format("* <a href=\"%s\"> %s </a> <br>", article.getLink(),article.getTitle()));
        }
        return builder.toString();
    }

    private void sendMail(String recipient, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(recipient);
            helper.setFrom("notify@newsAtworld");
            helper.setSubject("Nowości w NewsAtWorld!");
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
        }
        javaMailSender.send(mail);
    }


}
