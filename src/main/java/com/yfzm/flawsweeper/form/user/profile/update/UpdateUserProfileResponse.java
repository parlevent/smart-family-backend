package com.yfzm.flawsweeper.form.user.profile.update;

public class UpdateUserProfileResponse {
    private Boolean status;

    public UpdateUserProfileResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
