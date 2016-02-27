package pl.pwr.news.webapp;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Evelan-E6540 on 27.02.2016.
 */
@Controller
@Log4j
public class HomeController {

    @RequestMapping(value = "/")
    public String home(Model model) {
        return "index";
    }
}
