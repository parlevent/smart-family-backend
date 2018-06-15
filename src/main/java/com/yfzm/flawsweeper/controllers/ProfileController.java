package com.yfzm.flawsweeper.controllers;

import com.google.gson.Gson;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.user.profile.get.GetUserProfileResponse;
import com.yfzm.flawsweeper.form.user.profile.update.UpdateUserProfileForm;
import com.yfzm.flawsweeper.form.user.profile.update.UpdateUserProfileResponse;
import com.yfzm.flawsweeper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;

import static com.yfzm.flawsweeper.util.Util.getAndEncodeJsonData;

@RestController
@RequestMapping("/userProfile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public GetUserProfileResponse getUserProfile(HttpSession httpSession) {
        SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (sessionInfo == null || sessionInfo.getUid() == null) {
            return new GetUserProfileResponse(false);
        }
        return userService.getUserProfileById(sessionInfo.getUid());
    }

    @PostMapping
    public UpdateUserProfileResponse updateUserProfile(HttpServletRequest request, HttpSession httpSession) {
        UpdateUserProfileResponse response = new UpdateUserProfileResponse(false);

        SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
        String JSONString = getAndEncodeJsonData(request, "json");

        if (sessionInfo == null || JSONString == null) {
            return response;
        }

        Gson gson = new Gson();
        UpdateUserProfileForm form = gson.fromJson(JSONString, UpdateUserProfileForm.class);

        response.setStatus(userService.updateUserProfileViaForm(form, sessionInfo.getUid()));
        return response;
    }

    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public UpdateUserProfileResponse updateUserProfilePhoto(HttpServletRequest request, HttpSession session) {
        UpdateUserProfileResponse response = new UpdateUserProfileResponse(false);
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        if (sessionInfo == null) {
            return response;
        }

        String filename;
        InputStream inputStream;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            filename = file.getOriginalFilename();
            inputStream = file.getInputStream();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return response;
        }

        response.setStatus(userService.updateUserProfilePhoto(sessionInfo.getUid(), filename, inputStream));
        return response;
    }
}
