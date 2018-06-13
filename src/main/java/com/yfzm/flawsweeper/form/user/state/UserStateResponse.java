package com.yfzm.flawsweeper.form.user.state;

public class UserStateResponse {
    private Boolean status;

    public UserStateResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
