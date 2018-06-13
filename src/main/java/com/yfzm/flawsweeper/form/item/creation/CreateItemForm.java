package com.yfzm.flawsweeper.form.item.creation;

import java.util.List;

public class CreateItemForm {
    private String title;
    private List<String> qTag;
    private String qText;
    private long createTime;
    private String reason;
    private String cAnswer;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
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
}
