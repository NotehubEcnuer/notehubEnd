package com.ecnu.notehub.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * @author onion
 * @date 2019/11/29 -5:24 下午
 */
@Data
public class NoteRequest {
    private String authorId;
    private String authorName;
    private String title;
    private Integer authority;
    private MultipartFile file;
    private Set<String> tags;
    private String summary;
    private String content;
    private Integer types;
}
