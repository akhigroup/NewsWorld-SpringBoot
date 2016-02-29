package pl.pwr.news.webapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.news.model.user.CurrentUser;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.MessageService;
import pl.pwr.news.service.UserService;
import pl.pwr.news.webapp.form.UserForm;
import pl.pwr.news.webapp.form.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Rafal on 2016-02-28.
 */

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    private UserValidator validator;

    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model, @AuthenticationPrincipal CurrentUser activeUser) {
        if(activeUser != null){
            return "redirect:/logout";
        }else {
            model.addAttribute(new UserForm());
            return "register";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitRegister(@Valid @ModelAttribute("userForm") UserForm userForm,
                                 BindingResult result,
                                 Model model,
                                 HttpServletRequest request) {

        validator.validate(userForm, result);
        if (result.hasErrors()) {
            model.addAttribute(userForm);
            return "register";
        }

        User user = createUserFromFormData(userForm);
        String hash = userService.generateActivateAccountUniqueHash(user);
        user.setActivationHash(hash);
        userService.save(user);
        //mailService.sendRegisterActivationMail(request.getServerName(),user.getEmail(), hash);

        model.addAttribute("activationEmail", messageService.getMessage("register.activation.email"));

        return "register";
    }

    private User createUserFromFormData(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userForm.getPassword()));
        return user;
    }
}
