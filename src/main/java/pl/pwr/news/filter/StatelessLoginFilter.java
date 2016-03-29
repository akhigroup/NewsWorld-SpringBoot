package pl.pwr.news.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.auth.TokenAuthenticationService;
import pl.pwr.news.service.auth.UserAuthentication;
import pl.pwr.news.service.auth.UserRequest;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class StatelessLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenAuthenticationService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService;

    @PostConstruct
    public void init() {
        setAuthenticationManager(authenticationManager);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        final User authenticatedUser = (User) userService.loadUserByUsername(authResult.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

        tokenService.addAuthentication(response, userAuthentication);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserRequest user = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);
            System.out.println(user.getUsername()+ " " + user.getPassword());
            return  getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

//            User u = new ObjectMapper().readValue(request.getInputStream(), User.class);
//            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            redirect(response);
            return null;
        }
    }

    private void redirect(HttpServletResponse response) {
        try {
            response.sendRedirect("/auth/unauthorized");
        } catch (IOException e1) {
            response.setStatus(SC_UNAUTHORIZED);
        }
    }
}