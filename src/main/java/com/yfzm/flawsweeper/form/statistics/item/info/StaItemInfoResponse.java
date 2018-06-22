package com.yfzm.flawsweeper.form.statistics.item.info;

import java.util.List;

public class StaItemInfoResponse {
    private Boolean status;
    private long adminItemNum;
    private long normalItemNum;
    private List<StaItemInfoEntry> userItemInfo;

    public StaItemInfoResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getAdminItemNum() {
        return adminItemNum;
    }

    public void setAdminItemNum(long adminItemNum) {
        this.adminItemNum = adminItemNum;
    }

    public long getNormalItemNum() {
        return normalItemNum;
    }

    public void setNormalItemNum(long normalItemNum) {
        this.normalItemNum = normalItemNum;
    }

    public List<StaItemInfoEntry> getUserItemInfo() {
        return userItemInfo;
    }

    public void setUserItemInfo(List<StaItemInfoEntry> userItemInfo) {
        this.userItemInfo = userItemInfo;
    }
}
