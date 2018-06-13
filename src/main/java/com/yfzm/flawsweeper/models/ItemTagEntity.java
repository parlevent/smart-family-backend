package com.yfzm.flawsweeper.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item_tag", schema = "flaw_sweeper", catalog = "")
@IdClass(ItemTagEntityPK.class)
public class ItemTagEntity {
    private String itemId;
    private int tagId;

    @Id
    @Column(name = "item_id", nullable = false, length = 32)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Id
    @Column(name = "tag_id", nullable = false)
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemTagEntity that = (ItemTagEntity) o;
        return tagId == that.tagId &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemId, tagId);
    }
}
