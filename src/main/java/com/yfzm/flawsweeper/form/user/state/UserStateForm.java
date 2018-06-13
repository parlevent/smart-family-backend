package com.yfzm.flawsweeper.form.user.state;

import java.util.List;

public class UserStateForm {
    private int state;
    private List<String> user_ids;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<String> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<String> user_ids) {
        this.user_ids = user_ids;
    }
}
