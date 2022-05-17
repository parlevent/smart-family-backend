package com.yfzm.flawsweeper.form.item.detail;

import java.util.List;

public class GetItemDetailResponse {
    private Boolean status;
    private String id;
    private String title;
    private String qText;
    private String reason;
    private List<String> qTag;
    private String cAnswer;
    private long createTime;
    private Boolean bySelf;
    private Boolean switchStatus;
    private int viewCount;
    private int editCount;
    private int redoCount;
    private List<RedoInfo> redo;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

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

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getqTag() {
        return qTag;
    }

    public void setqTag(List<String> qTag) {
        this.qTag = qTag;
    }

    public String getcAnswer() {
        return cAnswer;
    }

    public void setcAnswer(String cAnswer) {
        this.cAnswer = cAnswer;
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

    public int getEditCount() {
        return editCount;
    }

    public void setEditCount(int editCount) {
        this.editCount = editCount;
    }

    public int getRedoCount() {
        return redoCount;
    }

    public void setRedoCount(int redoCount) {
        this.redoCount = redoCount;
    }

    public List<RedoInfo> getRedo() {
        return redo;
    }

    public void setRedo(List<RedoInfo> redo) {
        this.redo = redo;
    }

    public Boolean getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(Boolean switchStatus) {
        this.switchStatus = switchStatus;
    }
}
