package com.ecnu.notehub.dao;

import com.ecnu.notehub.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author onion
 * @date 2019/11/16 -2:01 下午
 */
public interface UserDao extends MongoRepository<User, String> {
    User findByAccount(String email);
}
