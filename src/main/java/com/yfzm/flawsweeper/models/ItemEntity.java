package com.yfzm.flawsweeper.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "item", schema = "smart_family", catalog = "")
public class ItemEntity {
    private String itemId;
    private String title;
    private byte status;
    private String content;
    private String answer;
    private Timestamp createTime;
    private Integer viewCount;
    private Integer editCount;
    private Integer redoCount;
    private byte mode;
    private String reason;
    private UserEntity user;
    private Set<TagEntity> tags;
    private Set<RedoEntity> redoSet;

    @Id
    @Column(name = "item_id", nullable = false, length = 32)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 128)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 128)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "answer", nullable = false, length = 128)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "view_count", nullable = true)
    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Basic
    @Column(name = "edit_count", nullable = true)
    public Integer getEditCount() {
        return editCount;
    }

    public void setEditCount(Integer editCount) {
        this.editCount = editCount;
    }

    @Basic
    @Column(name = "redo_count", nullable = true)
    public Integer getRedoCount() {
        return redoCount;
    }

    public void setRedoCount(Integer redoCount) {
        this.redoCount = redoCount;
    }

    @Basic
    @Column(name = "mode", nullable = false)
    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    @Basic
    @Column(name = "reason", nullable = true, length = 128)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "item_tag", joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    public Set<RedoEntity> getRedoSet() {
        return redoSet;
    }

    public void setRedoSet(Set<RedoEntity> redoSet) {
        this.redoSet = redoSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return mode == that.mode &&
                status == that.status &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(answer, that.answer) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(viewCount, that.viewCount) &&
                Objects.equals(editCount, that.editCount) &&
                Objects.equals(redoCount, that.redoCount) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemId, title, content, answer, createTime, viewCount, editCount, redoCount, mode, status, reason);
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
