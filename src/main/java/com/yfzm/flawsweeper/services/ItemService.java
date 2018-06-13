package com.yfzm.flawsweeper.services;

import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.item.creation.CreateItemForm;
import com.yfzm.flawsweeper.form.item.list.ListItemForm;
import com.yfzm.flawsweeper.form.item.modification.ModifyItemForm;
import com.yfzm.flawsweeper.form.item.redo.RedoItemForm;
import com.yfzm.flawsweeper.models.ItemEntity;

import java.util.List;

public interface ItemService {

    List<ItemEntity> getItemListViaListItemForm(SessionInfo sessionInfo, int pageSize, ListItemForm form);

    ItemEntity getItemDetailByIdAndUser(String itemId, SessionInfo sessionInfo);

    Boolean modifyItemViaModifyItemForm(ModifyItemForm form);

    String createItemAndReturnId(CreateItemForm form, SessionInfo sessionInfo);

    Boolean deleteItemByItemId(String itemId, SessionInfo sessionInfo);

    int saveRedoRecordAndReturnRedoId(RedoItemForm form, SessionInfo sessionInfo);

    List<ItemEntity> getItemListViaUserId(String userId);
}
