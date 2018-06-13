package com.yfzm.flawsweeper.form.user.list;

import java.util.List;

public class ListUserResponse {
    private Boolean status;
    private int num;
    private List<ListUserInfo> users;

    public ListUserResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ListUserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<ListUserInfo> users) {
        this.users = users;
    }
}
