package com.yfzm.flawsweeper.controllers;

import com.google.gson.Gson;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.item.creation.CreateItemForm;
import com.yfzm.flawsweeper.form.item.creation.CreateItemResponse;
import com.yfzm.flawsweeper.form.item.deletion.DeleteItemForm;
import com.yfzm.flawsweeper.form.item.deletion.DeleteItemResponse;
import com.yfzm.flawsweeper.form.item.detail.GetItemDetailForm;
import com.yfzm.flawsweeper.form.item.detail.GetItemDetailResponse;
import com.yfzm.flawsweeper.form.item.detail.RedoInfo;
import com.yfzm.flawsweeper.form.item.list.ListItemForm;
import com.yfzm.flawsweeper.form.item.list.ListItemInfo;
import com.yfzm.flawsweeper.form.item.list.ListItemResponse;
import com.yfzm.flawsweeper.form.item.modification.ModifyItemForm;
import com.yfzm.flawsweeper.form.item.modification.ModifyItemResponse;
import com.yfzm.flawsweeper.form.item.redo.RedoItemForm;
import com.yfzm.flawsweeper.form.item.redo.RedoItemResponse;
import com.yfzm.flawsweeper.form.item.tag.GetAllTagsResponse;
import com.yfzm.flawsweeper.models.ItemEntity;
import com.yfzm.flawsweeper.models.RedoEntity;
import com.yfzm.flawsweeper.models.TagEntity;
import com.yfzm.flawsweeper.services.ItemService;
import com.yfzm.flawsweeper.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.yfzm.flawsweeper.util.Constant.ADMIN_USER;
import static com.yfzm.flawsweeper.util.Constant.PAGE_SIZE;
import static com.yfzm.flawsweeper.util.Util.getAndEncodeJsonData;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final TagService tagService;

    @Autowired
    public ItemController(ItemService itemService, TagService tagService) {
        this.itemService = itemService;
        this.tagService = tagService;
    }


    @GetMapping("/entry")
    public GetItemDetailResponse getItemDetail(GetItemDetailForm form, HttpSession session) {
        GetItemDetailResponse response = new GetItemDetailResponse();
        response.setStatus(false);
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        if (sessionInfo == null || form.getId() == null) {
            return response;
        }

        ItemEntity item = itemService.getItemDetailByIdAndUser(form.getId(), sessionInfo);
        response.setStatus(true);
        response.setId(item.getItemId());
        response.setTitle(item.getTitle());
        List<String> tags = new ArrayList<>();
        Set<TagEntity> tag_set = item.getTags();
        for (TagEntity tag : tag_set) {
            tags.add(tag.getTagContent());
        }
        response.setqTag(tags);
        response.setReason(item.getReason());
        response.setqText(item.getContent());
        response.setcAnswer(item.getAnswer());
        response.setCreateTime(item.getCreateTime().getTime());
        response.setBySelf(item.getMode() == 1);
        response.setSwitchStatus(item.getStatus() == 1);
        response.setViewCount(item.getViewCount());
        response.setEditCount(item.getEditCount());
        response.setRedoCount(item.getRedoCount());
        List<RedoInfo> redoInfos = new ArrayList<>();
//        Set<RedoEntity> redo_set = item.getRedoSet();
        for (RedoEntity redo : item.getRedoSet()) {
            if (redo.getUser().getUserId().equals(sessionInfo.getUid())) {
                RedoInfo r = new RedoInfo();
                r.setrAnswer(redo.getAnswer());
                r.setrTime(redo.getRedoTime().getTime());
                redoInfos.add(r);
            }
        }
        response.setRedo(redoInfos);
        return response;
    }

    @PostMapping("/entry")
    public ModifyItemResponse modifyItem(HttpServletRequest request, HttpSession session) {
        ModifyItemResponse response = new ModifyItemResponse();
        response.setStatus(false);

        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        String JSONString = getAndEncodeJsonData(request, "json");

        if (sessionInfo == null || JSONString == null) {
            return response;
        }

        Gson gson = new Gson();
        ModifyItemForm form = gson.fromJson(JSONString, ModifyItemForm.class);

        response.setStatus(itemService.modifyItemViaModifyItemForm(form));
        return response;
    }

    @PutMapping("/entry")
    public CreateItemResponse addItem(HttpServletRequest request, HttpSession session) {
        CreateItemResponse response = new CreateItemResponse();
        response.setStatus(false);

        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        String JSONString = getAndEncodeJsonData(request, "json");

        if (sessionInfo == null || JSONString == null) {
            return response;
        }

        Gson gson = new Gson();
        CreateItemForm form = gson.fromJson(JSONString, CreateItemForm.class);

        String itemId = itemService.createItemAndReturnId(form, sessionInfo);
        if (itemId == null) {
            return response;
        }
        response.setStatus(true);
        response.setId(itemId);
        return response;
    }

    @DeleteMapping("/entry")
    public DeleteItemResponse deleteItem(@RequestParam("id") String itemId, HttpSession session) {
        DeleteItemResponse response = new DeleteItemResponse();
        response.setStatus(false);

        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        if (sessionInfo == null || itemId == null) {
            return response;
        }

        response.setStatus(itemService.deleteItemByItemId(itemId, sessionInfo));
        return response;
    }


    @GetMapping("/list")
    public ListItemResponse getList(ListItemForm form, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        if (sessionInfo == null) {
            return new ListItemResponse(false);
        }

        List<ItemEntity> items = itemService.getItemListViaListItemForm(sessionInfo, PAGE_SIZE, form);

        return createItemListViaItemEntities(items);
    }

    @GetMapping("/list-only")
    public ListItemResponse getSpecificUserItemList(String userId, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        if (sessionInfo == null || sessionInfo.getIsAdmin() != ADMIN_USER) {
            return new ListItemResponse(false);
        }
        List<ItemEntity> items = itemService.getItemListViaUserId(userId);
        return createItemListViaItemEntities(items);
    }


    @GetMapping("/tags")
    public GetAllTagsResponse getAllTags() {
        GetAllTagsResponse response = new GetAllTagsResponse();
        response.setTags(tagService.getAllTags());
        return response;
    }


    @PostMapping("/redo")
    public RedoItemResponse redoItem(HttpServletRequest request, HttpSession session) {
        RedoItemResponse response = new RedoItemResponse();
        response.setStatus(false);

        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        String JSONString = getAndEncodeJsonData(request, "json");

        if (sessionInfo == null || JSONString == null) {
            return response;
        }

        Gson gson = new Gson();
        RedoItemForm form = gson.fromJson(JSONString, RedoItemForm.class);

        int redoId = itemService.saveRedoRecordAndReturnRedoId(form, sessionInfo);
        if (redoId != 0) {
            response.setStatus(true);
            response.setRedoId(redoId);
        }
        return response;
    }


    private ListItemResponse createItemListViaItemEntities(List<ItemEntity> items) {
        ListItemResponse response = new ListItemResponse(true);
        response.setNum(items.size());
        List<ListItemInfo> listItemInfos = new ArrayList<>();
        for (ItemEntity item : items) {
            ListItemInfo info = new ListItemInfo();
            info.setId(item.getItemId());
            info.setTitle(item.getTitle());
            // tags
            Set<TagEntity> tag_set = item.getTags();
            List<String> tags = new ArrayList<>();
            for (TagEntity tag : tag_set) {
                tags.add(tag.getTagContent());
//                System.out.println(tag.getTagContent());
            }
            info.setqTag(tags);
            info.setCreateTime(item.getCreateTime().getTime());
            info.setBySelf(item.getMode() == 1);
            info.setSwitchStatus(item.getStatus() == 1);
            info.setRedoCount(item.getRedoCount());
            info.setViewCount(item.getViewCount());

            listItemInfos.add(info);
        }
        response.setItems(listItemInfos);
        return response;
    }

}
