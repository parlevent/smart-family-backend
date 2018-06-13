package com.yfzm.flawsweeper.form.item.redo;

public class RedoItemForm {
    private String id;
    private String rAnswer;
    private long rTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getrAnswer() {
        return rAnswer;
    }

    public void setrAnswer(String rAnswer) {
        this.rAnswer = rAnswer;
    }

    public long getrTime() {
        return rTime;
    }

    public void setrTime(long rTime) {
        this.rTime = rTime;
    }
}
