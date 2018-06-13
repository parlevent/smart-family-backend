package com.yfzm.flawsweeper.form.user.deletion;

public class DeleteUsersResponse {
    private Boolean status;

    public DeleteUsersResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
