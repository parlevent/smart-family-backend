package com.yfzm.flawsweeper.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "redo", schema = "flaw_sweeper", catalog = "")
public class RedoEntity {
    private int redoId;
    private String answer;
    private Timestamp redoTime;
    private ItemEntity item;
    private UserEntity user;

    @Id
    @Column(name = "redo_id", nullable = false)
    public int getRedoId() {
        return redoId;
    }

    public void setRedoId(int redoId) {
        this.redoId = redoId;
    }

    @Basic
    @Column(name = "answer", nullable = true, length = 128)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "redo_time", nullable = false)
    public Timestamp getRedoTime() {
        return redoTime;
    }

    public void setRedoTime(Timestamp redoTime) {
        this.redoTime = redoTime;
    }

    @ManyToOne()
    @JoinColumn(name = "item_id")
    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedoEntity that = (RedoEntity) o;
        return redoId == that.redoId &&
                Objects.equals(answer, that.answer) &&
                Objects.equals(redoTime, that.redoTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(redoId, answer, redoTime);
    }
}
