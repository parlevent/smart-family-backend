package com.yfzm.flawsweeper.form.item.list;

import java.util.List;

public class ListItemResponse {
    private Boolean status;
    private int num;
    private List<ListItemInfo> items;


    public ListItemResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ListItemInfo> getItems() {
        return items;
    }

    public void setItems(List<ListItemInfo> items) {
        this.items = items;
    }
}
