package com.yfzm.flawsweeper.dao;

import com.yfzm.flawsweeper.models.ItemEntity;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItemDao extends JpaRepository<ItemEntity, String>, JpaSpecificationExecutor<ItemEntity> {

    List<ItemEntity> findAllByUserUserId(String userId);

    Page<ItemEntity> findAllByUserUserId(String userId, Pageable pageable);

    Page<ItemEntity> findAllByUserUserIdOrUserType(String userId, int type, Pageable pageable);

    ItemEntity findByItemIdAndUserUserId(String itemId, String uid);

    ItemEntity findByItemId(String itemId);
}
