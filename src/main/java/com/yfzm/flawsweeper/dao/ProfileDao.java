package com.yfzm.flawsweeper.dao;

import com.yfzm.flawsweeper.models.MongoProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileDao extends MongoRepository<MongoProfileEntity, String> {


}
