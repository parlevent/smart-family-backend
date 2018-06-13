package com.yfzm.flawsweeper.dao;

import com.yfzm.flawsweeper.models.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<TagEntity, Integer> {

    TagEntity findByTagContent(String content);
}
