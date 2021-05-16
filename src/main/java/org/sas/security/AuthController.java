package org.sas.security;

import org.sas.model.User;
import org.sas.services.UserService;
import org.sas.utils.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.logging.Logger;


@Controller
public class AuthController {
    private final Logger logger = Logger.getLogger(AuthController.class.getName());
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        if (registrationRequest.getLogin() == null) {
            return new ResponseEntity<>(new HttpResponse(1, "login can't be null"),
                    HttpStatus.BAD_REQUEST);
        }
        if (registrationRequest.getPassword() == null) {
            return new ResponseEntity<>(new HttpResponse(2, "password can't be null"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User(registrationRequest.getLogin(), registrationRequest.getPassword(),
                registrationRequest.getTimeZone());
        userService.saveUser(user);
        return new ResponseEntity<>(new HttpResponse(0, ""), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authorizeUser(@RequestBody AuthRequest authRequest) {
        try {
            userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
            String token = jwtProvider.generateToken(authRequest.getLogin());
            userService.updateToken(token, authRequest.getLogin());
            return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
        }
        catch (UsernameNotFoundException exception) {
            logger.warning("User with login: \"" + authRequest.getLogin() + "\" not found");
        }
        return new ResponseEntity<>(new AuthResponse("Failed"), HttpStatus.BAD_REQUEST);
    }
}
