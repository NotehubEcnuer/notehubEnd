package com.ecnu.notehub.service;

import com.ecnu.notehub.dto.NoteRequest;
import com.ecnu.notehub.search.NoteIndex;
import org.springframework.data.domain.Page;

/**
 * @author onion
 * @date 2019/11/29 -5:16 下午
 */
public interface NoteService {
    Page<NoteIndex> findByTitle(String title);

    Page<NoteIndex> search(String keyword);

    void addPdf(NoteRequest noteRequest);
}
