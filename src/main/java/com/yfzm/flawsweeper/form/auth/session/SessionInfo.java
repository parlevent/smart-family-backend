package com.yfzm.flawsweeper.form.auth.session;

public class SessionInfo {
    private String uid;
    private Integer isAdmin;

    public SessionInfo(String uid, Integer isAdmin) {
        this.uid = uid;
        this.isAdmin = isAdmin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
