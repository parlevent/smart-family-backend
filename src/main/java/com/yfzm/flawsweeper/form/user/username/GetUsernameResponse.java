package com.yfzm.flawsweeper.form.user.username;

public class GetUsernameResponse {
    private Boolean status;
    private String username;

    public GetUsernameResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
