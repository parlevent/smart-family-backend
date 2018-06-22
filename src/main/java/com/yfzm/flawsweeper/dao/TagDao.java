package com.yfzm.flawsweeper.dao;

import com.yfzm.flawsweeper.models.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagDao extends JpaRepository<TagEntity, Integer> {

    TagEntity findByTagContent(String content);

    @Query(value = "select t.tag_content,count(*) from tag t join item_tag t2 on t.tag_id = t2.tag_id group by t.tag_content order by count(*) desc limit 10", nativeQuery = true)
    List getTopItemTag();
}
