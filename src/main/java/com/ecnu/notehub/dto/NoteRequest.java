package com.ecnu.notehub.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author onion
 * @date 2019/11/29 -5:24 下午
 */
@Data
public class NoteRequest {
    @NotEmpty
    private String authorId;
    private String authorName;
    @NotEmpty
    private String title;
    @Range(min = 0, max = 2, message = "笔记权限错误")
    private Integer authority;
    private MultipartFile file;
    private Set<String> tags;
    private String summary;
    @NotEmpty
    private String content;
    @Range(min = 0, max = 3, message = "笔记类型错误")
    private Integer types;
}
