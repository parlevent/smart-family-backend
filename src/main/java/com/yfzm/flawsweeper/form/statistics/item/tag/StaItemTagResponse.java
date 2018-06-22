package com.yfzm.flawsweeper.form.statistics.item.tag;

import java.util.List;

public class StaItemTagResponse {
    private Boolean status;
    private List<StaItemTagEntry> itemTagInfo;

    public StaItemTagResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<StaItemTagEntry> getItemTagInfo() {
        return itemTagInfo;
    }

    public void setItemTagInfo(List<StaItemTagEntry> itemTagInfo) {
        this.itemTagInfo = itemTagInfo;
    }
}
