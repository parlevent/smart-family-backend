package com.yfzm.flawsweeper.form.item.list;

import java.util.List;

public class ListItemInfo {
    private String id;
    private String title;
    private List<String> qTag;
    private long createTime;
    private Boolean bySelf;
    private Boolean switchStatus;
    private int viewCount;
    private int redoCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getqTag() {
        return qTag;
    }

    public void setqTag(List<String> qTag) {
        this.qTag = qTag;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Boolean getBySelf() {
        return bySelf;
    }

    public void setBySelf(Boolean bySelf) {
        this.bySelf = bySelf;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getRedoCount() {
        return redoCount;
    }

    public void setRedoCount(int redoCount) {
        this.redoCount = redoCount;
    }

    public Boolean getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(Boolean switchStatus) {
        this.switchStatus = switchStatus;
    }
}
