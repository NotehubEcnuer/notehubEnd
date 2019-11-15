package com.ecnu.notehub.dao;

import com.ecnu.notehub.search.NoteIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author onion
 * @date 2019/11/15 -9:19 上午
 */
public interface NoteSearchDao extends ElasticsearchRepository<NoteIndex, String> {
}
