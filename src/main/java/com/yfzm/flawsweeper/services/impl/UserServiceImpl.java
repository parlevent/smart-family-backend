package com.yfzm.flawsweeper.services.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.yfzm.flawsweeper.dao.ProfileDao;
import com.yfzm.flawsweeper.dao.UserDao;
import com.yfzm.flawsweeper.form.auth.login.LoginForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterForm;
import com.yfzm.flawsweeper.form.auth.register.RegisterResponse;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.user.deletion.DeleteUsersForm;
import com.yfzm.flawsweeper.form.user.list.ListUserInfo;
import com.yfzm.flawsweeper.form.user.profile.GetUserProfileResponse;
import com.yfzm.flawsweeper.form.user.state.UserStateForm;
import com.yfzm.flawsweeper.models.MongoProfileEntity;
import com.yfzm.flawsweeper.models.UserEntity;
import com.yfzm.flawsweeper.services.UserService;
import com.yfzm.flawsweeper.util.Constant;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.yfzm.flawsweeper.util.Constant.ADMIN_USER;
import static com.yfzm.flawsweeper.util.Constant.DEFAULT_PROFILE_PHOTO_NAME;
import static com.yfzm.flawsweeper.util.Constant.NORMAL_USER;
import static com.yfzm.flawsweeper.util.Util.createRandomId;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final GridFsOperations gridFsOperations;

    private DBObject metaData = new BasicDBObject();
    @Resource
    private MongoDbFactory mongoDbFactory;

    @Autowired
    public UserServiceImpl(UserDao userDao, ProfileDao profileDao, GridFsOperations gridFsOperations) {
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.gridFsOperations = gridFsOperations;
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

        MongoProfileEntity profile = new MongoProfileEntity();
        profile.setUserId(uid);
        profile.setUsername(form.getUsername());
        profile.setBio("");
        profile.setEmail(form.getEmail());
        profile.setPhone(form.getPhone());
        try {
            InputStream is = new FileInputStream("src/main/resources/static/" + DEFAULT_PROFILE_PHOTO_NAME);
            metaData.put("type", "image");
            String imageId = gridFsOperations.store(is, DEFAULT_PROFILE_PHOTO_NAME, "image/png", metaData).toString();
            profile.setPhotoId(imageId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        profileDao.save(profile);

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
        for (String uid : uidList) {
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

    @Override
    public GetUserProfileResponse getUserProfileById(String uid) {
        GetUserProfileResponse response = new GetUserProfileResponse(false);
        Optional<MongoProfileEntity> profileEntity = profileDao.findById(uid);
        if (profileEntity.isPresent()) {
            response.setStatus(true);
            response.setUsername(profileEntity.get().getUsername());
            response.setEmail(profileEntity.get().getEmail());
            response.setPhone(profileEntity.get().getPhone());
            response.setBio(profileEntity.get().getBio());
            try {
                GridFSFile imageFile = gridFsOperations.findOne(
                        Query.query(Criteria.where("_id").is(profileEntity.get().getPhotoId())));
                InputStream stream = GridFSBuckets.create(mongoDbFactory.getDb()).openDownloadStream(imageFile.getObjectId());
                byte[] bytes = IOUtils.toByteArray(stream);
                String encoded = Base64.getEncoder().encodeToString(bytes);
                response.setProfilePhoto(encoded);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

}
