package com.yfzm.flawsweeper.services.impl;

import com.yfzm.flawsweeper.dao.ItemDao;
import com.yfzm.flawsweeper.dao.RedoDao;
import com.yfzm.flawsweeper.dao.UserDao;
import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.item.creation.CreateItemForm;
import com.yfzm.flawsweeper.form.item.list.ListItemForm;
import com.yfzm.flawsweeper.form.item.modification.ModifyItemForm;
import com.yfzm.flawsweeper.form.item.redo.RedoItemForm;
import com.yfzm.flawsweeper.models.ItemEntity;
import com.yfzm.flawsweeper.models.RedoEntity;
import com.yfzm.flawsweeper.models.TagEntity;
import com.yfzm.flawsweeper.models.UserEntity;
import com.yfzm.flawsweeper.services.ItemService;
import com.yfzm.flawsweeper.services.TagService;
import com.yfzm.flawsweeper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.yfzm.flawsweeper.util.Constant.*;
import static com.yfzm.flawsweeper.util.Util.createRandomId;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;

    private final TagService tagService;

    private final UserDao userDao;

    private final RedoDao redoDao;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, TagService tagService, UserDao userDao, RedoDao redoDao) {
        this.itemDao = itemDao;
        this.tagService = tagService;
        this.userDao = userDao;
        this.redoDao = redoDao;
    }

    @Override
    public List<ItemEntity> getItemListViaListItemForm(SessionInfo sessionInfo, int pageSize, ListItemForm form) {
        if (sessionInfo.getIsAdmin() == ADMIN_USER) {
            return itemDao.findAllByUserUserId(sessionInfo.getUid());
        }

        String sortMethod = SORT_BY_VIEW_COUNT.equals(form.getMethod()) ? "viewCount" : "createTime";
        Sort sort = new Sort(Sort.Direction.DESC, sortMethod);

        Pageable pageable = PageRequest.of(form.getPage(), pageSize, sort);
//        Page<ItemEntity> items = itemDao.findAllByUserUserId(uid, pageable);
        Page<ItemEntity> items =
                itemDao.findAllByUserUserIdOrUserType(sessionInfo.getUid(), ADMIN_USER, pageable);

//        Specification<ItemEntity> spec = new Specification<ItemEntity>() {
//            @Override
//            public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Expression<UserEntity> user = root.get("user").as(UserEntity.class);
//
//            }
//        };
//
//        Page<ItemEntity> items = itemDao.findAll(spec, pageable);

        List<ItemEntity> itemList = new ArrayList<>();
        String word = form.getWord();
        if (items == null) {
            return itemList;
        }

        if (word == null || word.equals("")) {
            for (ItemEntity item: items) {
                itemList.add(item);
            }
            return itemList;
        }

        for (ItemEntity item : items) {


            Set<TagEntity> tagEntities = item.getTags();
            if (tagEntities == null) {
                continue;
            }
            for (TagEntity tagEntity: tagEntities) {
                if (word.equals(tagEntity.getTagContent())) {
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }

    @Override
    public ItemEntity getItemDetailByIdAndUser(String itemId, SessionInfo sessionInfo) {
        ItemEntity item = itemDao.findByItemId(itemId);
        if (item.getMode() == PUBLIC_ITEM_MODE ||
                item.getUser().getUserId().equals(sessionInfo.getUid()) ||
                sessionInfo.getIsAdmin() == ADMIN_USER) {
            item.setViewCount(item.getViewCount() + 1);
            itemDao.saveAndFlush(item);
            return item;
        }
        return null;
    }

    @Override
    public Boolean modifyItemViaModifyItemForm(ModifyItemForm form) {
        String itemId = form.getId();
        Long createTime = form.getCreateTime();
        if (itemId == null || createTime == null) {
            return false;
        }

        ItemEntity item = itemDao.findByItemId(form.getId());
        if (item == null) {
            return false;
        }

        item.setCreateTime(new Timestamp(form.getCreateTime()));
        if (form.getTitle() != null) item.setTitle(form.getTitle());
        if (form.getqText() != null) item.setContent(form.getqText());
        if (form.getcAnswer() != null) item.setAnswer(form.getcAnswer());
        if (form.getReason() != null) item.setReason(form.getReason());
        item.setEditCount(item.getEditCount() + 1);
        if (form.getqTag() != null) item.setTags(tagService.updateAndReturnTagSet(form.getqTag()));
        itemDao.saveAndFlush(item);
        return true;
    }

    @Override
    public String createItemAndReturnId(CreateItemForm form, SessionInfo sessionInfo) {
        ItemEntity item = new ItemEntity();
        UserEntity user = userDao.findByUserId(sessionInfo.getUid());

        String itemId = createRandomId();
        Integer isAdmin = sessionInfo.getIsAdmin();

        if (user == null ||
                form.getTitle() == null ||
                form.getqText() == null ||
                form.getcAnswer() == null ||
                form.getCreateTime() == 0) {
            return null;
        }

        item.setItemId(itemId);
        item.setTitle(form.getTitle());
        item.setContent(form.getqText());
        item.setAnswer(form.getcAnswer());
        item.setCreateTime(new Timestamp(form.getCreateTime()));
        item.setViewCount(0);
        item.setEditCount(0);
        item.setRedoCount(0);
        item.setMode((isAdmin == 0) ? PRIVATE_ITEM_MODE : PUBLIC_ITEM_MODE);
        item.setReason(form.getReason());

        item.setUser(user);

        if (form.getqTag() != null) {
            item.setTags(tagService.updateAndReturnTagSet(form.getqTag()));
        }

        itemDao.saveAndFlush(item);

        return itemId;
    }

    @Override
    public Boolean deleteItemByItemId(String itemId, SessionInfo sessionInfo) {
        ItemEntity item = itemDao.findByItemId(itemId);
        if (item == null ||
                (!item.getUser().getUserId().equals(sessionInfo.getUid())) &&
                        sessionInfo.getIsAdmin() != ADMIN_USER) {
            return false;
        }
        itemDao.delete(item);
        return true;
    }

    @Override
    public int saveRedoRecordAndReturnRedoId(RedoItemForm form, SessionInfo sessionInfo) {
        if (form.getId() == null || form.getrAnswer() == null || form.getrTime() == 0) {
            return 0;
        }

        ItemEntity item = itemDao.findByItemId(form.getId());
        if (item == null ||
                (!item.getUser().getUserId().equals(sessionInfo.getUid()) &&
                        item.getMode() != PUBLIC_ITEM_MODE)) {
            return 0;
        }

        int redo_id = (int) (new Date()).getTime();
        RedoEntity redoEntity = new RedoEntity();
        redoEntity.setRedoId(redo_id);
        redoEntity.setAnswer(form.getrAnswer());
        redoEntity.setRedoTime(new Timestamp(form.getrTime()));
        redoEntity.setUser(userDao.findByUserId(sessionInfo.getUid()));
        redoEntity.setItem(item);
        redoDao.saveAndFlush(redoEntity);

        item.setRedoCount(item.getRedoCount() + 1);
        itemDao.saveAndFlush(item);

        return redo_id;
    }

    @Override
    public List<ItemEntity> getItemListViaUserId(String userId) {
        return itemDao.findAllByUserUserId(userId);
    }
}
