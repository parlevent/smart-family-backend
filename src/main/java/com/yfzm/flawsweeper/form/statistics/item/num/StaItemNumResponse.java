package com.yfzm.flawsweeper.form.statistics.item.num;

public class StaItemNumResponse {
    private Boolean status;
    private long num;

    public StaItemNumResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
