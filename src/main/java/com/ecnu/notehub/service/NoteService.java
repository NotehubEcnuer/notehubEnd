package com.ecnu.notehub.service;

import com.ecnu.notehub.dao.NoteSearchDao;
import com.ecnu.notehub.search.NoteIndex;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withHighlightFields(new HighlightBuilder.Field("title").preTags("<span style=\"color:red\">").postTags("</span>"))
                .withQuery(QueryBuilders.matchQuery("title", title))
                .build();
        return noteSearchDao.search(searchQuery);

    }
}
