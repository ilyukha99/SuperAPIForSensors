package org.sas.security.auth_register;

import org.sas.dao.UserDAO;
import org.sas.model.User;
import org.sas.responses.AuthResponse;
import org.sas.responses.Response;
import org.sas.security.jwt.JwtProvider;
import org.sas.services.UserService;
import org.sas.responses.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class AuthController {
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private UserDAO userDAO;

    @Autowired
    public AuthController(@NonNull UserService userService, @NonNull JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setUserDAO(@NonNull UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        String login = registrationRequest.getLogin();
        if (login == null) {
            return new ResponseEntity<>(new HttpResponse(1, "login can't be null"),
                    HttpStatus.BAD_REQUEST);
        }

        if (registrationRequest.getPassword() == null) {
            return new ResponseEntity<>(new HttpResponse(2, "password can't be null"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userDAO.loginExists(login)) {
            return new ResponseEntity<>(new HttpResponse(3, "login already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(login, registrationRequest.getPassword(), registrationRequest.getTimeZone());
        userService.saveNewUser(user);
        return new ResponseEntity<>(new HttpResponse(0, ""), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authorizeUser(@RequestBody @Valid AuthRequest authRequest) {
        try {
            userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
            String token = jwtProvider.generateToken(authRequest.getLogin(), 3);
            userService.updateUserToken(token, authRequest.getLogin());
            return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
        }
        catch (UsernameNotFoundException exception) {
            logger.warning("User with login: \"" + authRequest.getLogin() + "\" not found");
        }
        return new ResponseEntity<>(new AuthResponse("Failed"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/generateToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> generateSensorToken(@RequestHeader("Authorization") String bearerHeader) {
        String userToken;
        if (StringUtils.hasText(bearerHeader) && bearerHeader.startsWith("Bearer ")) {
            userToken = bearerHeader.substring(7);
        }
        else {
            return new ResponseEntity<>(new HttpResponse(1, "invalid Authorization header provided"),
                    HttpStatus.BAD_REQUEST);
        }

        if (!userDAO.tokenExists(userToken)) {
            return new ResponseEntity<>(new HttpResponse(2, "this token does not exists"),
                    HttpStatus.BAD_REQUEST);
        }

        String login = jwtProvider.getLoginFromToken(userToken);
        if (login == null) {
            return new ResponseEntity<>(new HttpResponse(3, "invalid user token provided"),
                    HttpStatus.BAD_REQUEST);
        }

        if (!userDAO.loginExists(login)) {
            return new ResponseEntity<>(new HttpResponse(4, "no such user found"),
                    HttpStatus.BAD_REQUEST);
        }

        String sensorToken = jwtProvider.generateToken(login, 365);
        userService.updateSensorToken(sensorToken, login);
        return new ResponseEntity<>(new AuthResponse(sensorToken), HttpStatus.OK);
    }
}
