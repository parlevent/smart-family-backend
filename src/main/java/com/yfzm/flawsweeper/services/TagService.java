package com.yfzm.flawsweeper.services;

import com.yfzm.flawsweeper.models.TagEntity;

import java.util.List;
import java.util.Set;

public interface TagService {

    Set<TagEntity> updateAndReturnTagSet(List<String> tags);

    List<String> getAllTags();

}
