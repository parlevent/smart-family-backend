package com.yfzm.flawsweeper.dao;

import com.yfzm.flawsweeper.models.ItemEntity;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface ItemDao extends JpaRepository<ItemEntity, String>, JpaSpecificationExecutor<ItemEntity> {

    List<ItemEntity> findAllByUserUserId(String userId);

    Page<ItemEntity> findAll(Pageable pageable);
    List<ItemEntity> findAll();

    Page<ItemEntity> findAllByUserUserId(String userId, Pageable pageable);

    Page<ItemEntity> findAllByUserUserIdOrUserType(String userId, int type, Pageable pageable);

    ItemEntity findByItemIdAndUserUserId(String itemId, String uid);

    ItemEntity findByItemId(String itemId);

    @Query(value = "select count(*) from item", nativeQuery = true)
    List getItemTotalNum();

    @Query(value = "select count(*) from item where item.mode = ?1", nativeQuery = true)
    List getItemNum(byte userType);


    @Query(value = "select u.username,count(*) from item i join user u on i.user_id = u.user_id group by i.user_id order by count(*) desc", nativeQuery = true)
    List getTopItemInfo();

    @Query(value = "select count(*) from item where item.create_time <= date_sub(curdate(),interval ?1 month) and item.create_time > date_sub(curdate(),interval ?2 month)", nativeQuery = true)
    List getItemNumBetween(int begin, int end);

}
