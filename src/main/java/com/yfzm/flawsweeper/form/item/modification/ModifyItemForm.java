package com.yfzm.flawsweeper.form.item.modification;

import java.util.List;

public class ModifyItemForm {
    private String id;
    private String title;
    private List<String> qTag;
    private String qText;
    private Long createTime;
    private String reason;
    private String cAnswer;
    private Boolean switchStatus;

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

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getcAnswer() {
        return cAnswer;
    }

    public void setcAnswer(String cAnswer) {
        this.cAnswer = cAnswer;
    }

    public Boolean getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(Boolean switchStatus) {
        this.switchStatus = switchStatus;
    }
}
