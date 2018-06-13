package com.yfzm.flawsweeper.services.impl;

import com.yfzm.flawsweeper.dao.TagDao;
import com.yfzm.flawsweeper.models.TagEntity;
import com.yfzm.flawsweeper.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Set<TagEntity> updateAndReturnTagSet(List<String> tags) {
        Set<TagEntity> tag_set = new HashSet<>();

        for (String tag: tags) {
            TagEntity tagEntity = tagDao.findByTagContent(tag);
            if (tagEntity == null) {
                tagEntity = new TagEntity();
                tagEntity.setTagContent(tag);
                tagDao.save(tagEntity);
            }
            tag_set.add(tagEntity);
        }
        return tag_set;
    }

    @Override
    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        List<TagEntity> tagEntities = tagDao.findAll();
        for (TagEntity tagEntity: tagEntities) {
            tags.add(tagEntity.getTagContent());
        }
        return tags;
    }
}
