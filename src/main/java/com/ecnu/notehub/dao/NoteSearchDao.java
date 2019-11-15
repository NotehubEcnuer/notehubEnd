package com.ecnu.notehub.dao;

import com.ecnu.notehub.search.NoteIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author onion
 * @date 2019/11/15 -9:19 上午
 */
public interface NoteSearchDao extends ElasticsearchRepository<NoteIndex, String> {
    List<NoteIndex> findAllByAuthorName(String authorName);
}
