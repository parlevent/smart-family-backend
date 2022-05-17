package com.yfzm.flawsweeper.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "smart_family", catalog = "")
public class TagEntity {
    private int tagId;
    private String tagContent;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id", nullable = false)
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag_content", nullable = false, length = 32)
    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return tagId == tagEntity.tagId &&
                Objects.equals(tagContent, tagEntity.tagContent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tagId, tagContent);
    }
}
