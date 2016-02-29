package pl.pwr.news.webapp;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwr.news.service.MailService;

/**
 * Created by Evelan-E6540 on 27.02.2016.
 */
@Controller
@Log4j
public class HomeController {

    @Autowired
    MailService mailService;


    @RequestMapping(value = "/")
    public String home(Model model) {
        mailService.sendSimpleMessage();

        return "index";
    }

}
