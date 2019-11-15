package com.ecnu.notehub.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/7 -1:03 下午
 */
@Document(collection = "note")
@Data
public class Note implements Serializable {
    @Id
    private String id;
    private String authorId;
    private String authorName;
    private String title;
    private Integer authority;
    private Date createTime;
    private Date updateTime;
    private List<String> tags;
    private String summary;
    private Integer stars;
    private Integer visits;
    private Integer hates;
    private Integer downloads;
    private String content;
    private Integer types;

}
