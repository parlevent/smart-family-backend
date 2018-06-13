package com.yfzm.flawsweeper.controllers;

import com.google.gson.Gson;
import com.yfzm.flawsweeper.form.auth.login.LoginForm;
import com.yfzm.flawsweeper.form.auth.login.LoginResponse;
import com.yfzm.flawsweeper.form.auth.logout.LogoutResponse;
import com.yfzm.flawsweeper.form.auth.register.RegisterForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterResponse;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.models.UserEntity;
import com.yfzm.flawsweeper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.yfzm.flawsweeper.util.Util.getAndEncodeJsonData;


@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/loginAuth")
    public LoginResponse login(LoginForm loginForm, HttpSession session) {
        if (loginForm.getType() == null) loginForm.setType(0);

        UserEntity user = userService.findUserViaLoginForm(loginForm);

        LoginResponse response = new LoginResponse();
        response.setStatus(false);

        if (user != null) {
            response.setStatus(true);
            session.setAttribute("sessionInfo", new SessionInfo(user.getUserId(), user.getType()));
        }
        response.setStatus(user != null);

        return response;
    }

    @GetMapping("/logoutNow")
    public LogoutResponse logout(HttpSession session) {
        LogoutResponse response = new LogoutResponse();
        response.setStatus(false);
        if (session.getAttribute("sessionInfo") != null) {
            response.setStatus(true);
            session.removeAttribute("sessionInfo");
        }
        return response;
    }

    @PostMapping("/doRegister")
    public RegisterResponse register(HttpServletRequest request, HttpSession session) {

        String JsonData = getAndEncodeJsonData(request, "json");
        if (JsonData == null) {
            return new RegisterResponse(false);
        }

        Gson gson = new Gson();
        RegisterForm form = gson.fromJson(JsonData, RegisterForm.class);

        return userService.createUserAndReturnResponse(form, session);
    }
}
