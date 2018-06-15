package com.yfzm.flawsweeper.controllers;

import com.google.gson.Gson;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.user.deletion.DeleteUsersForm;
import com.yfzm.flawsweeper.form.user.deletion.DeleteUsersResponse;
import com.yfzm.flawsweeper.form.user.list.ListUserInfo;
import com.yfzm.flawsweeper.form.user.list.ListUserResponse;
import com.yfzm.flawsweeper.form.user.state.UserStateForm;
import com.yfzm.flawsweeper.form.user.state.UserStateResponse;
import com.yfzm.flawsweeper.form.user.username.GetUsernameResponse;
import com.yfzm.flawsweeper.models.UserEntity;
import com.yfzm.flawsweeper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.yfzm.flawsweeper.util.Constant.ADMIN_USER;
import static com.yfzm.flawsweeper.util.Util.getAndEncodeJsonData;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ListUserResponse listAllUsers(HttpSession httpSession) {
        ListUserResponse response = new ListUserResponse(false);

        SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (sessionInfo == null || sessionInfo.getIsAdmin() == 0) {
            return response;
        }

        List<UserEntity> userEntities = userService.findAllNormalUsers();
        if (userEntities == null) {
            return response;
        }

        response.setStatus(true);
        response.setNum(userEntities.size());
        ArrayList<ListUserInfo> listUserInfoList = new ArrayList<>();
        for (UserEntity user: userEntities) {
            ListUserInfo userInfo = new ListUserInfo();
            userInfo.setId(user.getUserId());
            userInfo.setUsername(user.getUsername());
            userInfo.setValid(user.getStatus() == 1);
            userInfo.setNum(user.getItems().size());
            userInfo.setPhone(user.getPhone());
            userInfo.setEmail(user.getEmail());
            listUserInfoList.add(userInfo);
        }
        response.setUsers(listUserInfoList);
        return response;
    }

    @PostMapping("/setUsersState")
    public UserStateResponse setUserState(HttpServletRequest request, HttpSession httpSession) {
        UserStateResponse response = new UserStateResponse(false);
        SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (sessionInfo == null || sessionInfo.getIsAdmin() != ADMIN_USER) {
            return response;
        }

        String jsonData = getAndEncodeJsonData(request, "json");
        Gson gson = new Gson();
        UserStateForm form = gson.fromJson(jsonData, UserStateForm.class);
        if (form == null) {
            return response;
        }
        response.setStatus(userService.setUserStateByUserIdList(form));

        return response;
    }

    @PostMapping("/deleteUsers")
    public DeleteUsersResponse deleteUsers(HttpServletRequest request, HttpSession httpSession) {
        DeleteUsersResponse response = new DeleteUsersResponse(false);
        SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (sessionInfo == null || sessionInfo.getIsAdmin() != ADMIN_USER) {
            return response;
        }

        String jsonData = getAndEncodeJsonData(request, "json");
        Gson gson = new Gson();
        DeleteUsersForm form = gson.fromJson(jsonData, DeleteUsersForm.class);
        if (form == null) {
            return response;
        }
        response.setStatus(userService.deleteUsersByUserIdList(form));
        return response;
    }

    @GetMapping("/getUsername")
    public GetUsernameResponse getUserUsername(HttpSession httpSession) {
        GetUsernameResponse response = new GetUsernameResponse(false);
        SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (sessionInfo == null || sessionInfo.getUid() == null) {
            return response;
        }

        String username = userService.getUsernameByUserId(sessionInfo.getUid());
        if (username == null) {
            return response;
        }

        response.setStatus(true);
        response.setUsername(username);
        return response;
    }



}
