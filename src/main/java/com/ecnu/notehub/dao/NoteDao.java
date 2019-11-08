package com.ecnu.notehub.dao;

import com.ecnu.notehub.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author onion
 * @date 2019/11/7 -7:40 下午
 */
@Repository
public interface NoteDao extends MongoRepository<Note, String> {
}
