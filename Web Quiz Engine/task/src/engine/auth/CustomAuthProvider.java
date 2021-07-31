package engine.auth;

import engine.QuizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
    @Autowired
    UserService service;

    List<User> list = new ArrayList<>();
    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        Import();
        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmail().equals(username)
                    && passwordEncoder.matches(password, list.get(i).getPassword())) {
                QuizController.setAuthor(username);
                return new UsernamePasswordAuthenticationToken
                        (username, password, Collections.emptyList());
            }
        }
        throw new BadCredentialsException("External system authentication failed");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void Import(){
        list = service.getAllUser();
    }
}
