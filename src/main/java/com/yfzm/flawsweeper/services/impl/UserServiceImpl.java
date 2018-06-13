package com.yfzm.flawsweeper.services.impl;

import com.yfzm.flawsweeper.dao.UserDao;
import com.yfzm.flawsweeper.form.auth.login.LoginForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterResponse;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.user.deletion.DeleteUsersForm;
import com.yfzm.flawsweeper.form.user.list.ListUserInfo;
import com.yfzm.flawsweeper.form.user.state.UserStateForm;
import com.yfzm.flawsweeper.models.UserEntity;
import com.yfzm.flawsweeper.services.UserService;
import com.yfzm.flawsweeper.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.yfzm.flawsweeper.util.Constant.ADMIN_USER;
import static com.yfzm.flawsweeper.util.Constant.NORMAL_USER;
import static com.yfzm.flawsweeper.util.Util.createRandomId;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity findUserViaLoginForm(LoginForm form) {
        return userDao.findByUsernameAndPasswordAndTypeAndStatus(
                form.getUsername(),
                form.getPassword(),
                form.getType(),
                (byte) 1
        );
    }

    @Override
    public RegisterResponse createUserAndReturnResponse(RegisterForm form, HttpSession session) {
        RegisterResponse response = new RegisterResponse(false);

        if (form.getUsername() == null ||
                form.getPassword() == null ||
                form.getEmail() == null ||
                form.getPhone() == null) {
            response.setErrCode(Constant.RegisterErrorCode.INVALID_FORM);
            response.setErrMsg("表单错误");
            return response;
        }

        if (userDao.findByUsername(form.getUsername()) != null) {
            response.setErrCode(Constant.RegisterErrorCode.DUPLICATE_USERNAME);
            response.setErrMsg("该用户名已被占用");
            return response;
        }

        String uid = createRandomId();
        UserEntity user = new UserEntity();
        user.setUserId(uid);
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setStatus((byte) 1);
        user.setType(0);
        userDao.saveAndFlush(user);

        response.setStatus(true);
        response.setUserId(uid);
        session.setAttribute("sessionInfo", new SessionInfo(uid, 0));
        return response;
    }

    @Override
    public List<UserEntity> findAllNormalUsers() {
        return userDao.findAllByType(NORMAL_USER);
    }

    @Override
    public Boolean setUserStateByUserIdList(UserStateForm form) {
        List<String> uidList = form.getUser_ids();
        if (uidList == null) {
            return false;
        }
        for (String uid: uidList) {
            UserEntity user = userDao.findByUserId(uid);
            if (user != null) {
                user.setStatus((byte) form.getState());
                userDao.save(user);
            }
        }
        userDao.flush();
        return true;
    }

    @Override
    public Boolean deleteUsersByUserIdList(DeleteUsersForm form) {
        List<String> uidList = form.getUser_ids();
        if (uidList == null) {
            return false;
        }
        for (String uid : uidList) {
            UserEntity user = userDao.findByUserId(uid);
            if (user != null) {
                userDao.delete(user);
            }
        }
        userDao.flush();
        return true;
    }

    @Override
    public String getUsernameByUserId(String uid) {
        UserEntity userEntity = userDao.findByUserId(uid);
        if (userEntity == null) {
            return null;
        }
        return userEntity.getUsername();
    }
}
