package com.yfzm.flawsweeper.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ItemTagEntityPK implements Serializable {
    private String itemId;
    private int tagId;

    @Column(name = "item_id", nullable = false, length = 32)
    @Id
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "tag_id", nullable = false)
    @Id
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
        ItemTagEntityPK that = (ItemTagEntityPK) o;
        return tagId == that.tagId &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemId, tagId);
    }
}
