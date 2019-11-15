package com.ecnu.notehub.service;

import com.ecnu.notehub.dao.NoteSearchDao;
import com.ecnu.notehub.search.NoteIndex;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author onion
 * @date 2019/11/15 -9:20 上午
 */
@Service
public class NoteService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private NoteSearchDao noteSearchDao;

    public Page<NoteIndex> findByTitle(String title){
//        elasticsearchTemplate.query("");
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", title));
        return noteSearchDao.search(queryBuilder.build());
    }
}
