package com.yfzm.flawsweeper.services;

import com.yfzm.flawsweeper.form.auth.login.LoginForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterResponse;
import com.yfzm.flawsweeper.form.user.deletion.DeleteUsersForm;
import com.yfzm.flawsweeper.form.user.profile.GetUserProfileResponse;
import com.yfzm.flawsweeper.form.user.state.UserStateForm;
import com.yfzm.flawsweeper.models.MongoProfileEntity;
import com.yfzm.flawsweeper.models.UserEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    UserEntity findUserViaLoginForm(LoginForm form);

    RegisterResponse createUserAndReturnResponse(RegisterForm form, HttpSession session);

    List<UserEntity> findAllNormalUsers();

    Boolean setUserStateByUserIdList(UserStateForm form);

    Boolean deleteUsersByUserIdList(DeleteUsersForm form);

    String getUsernameByUserId(String uid);

    GetUserProfileResponse getUserProfileById(String uid);
}
