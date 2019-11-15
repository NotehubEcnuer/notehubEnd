package com.ecnu.notehub.search;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @author onion
 * @date 2019/11/15 -9:01 上午
 */
@Document(indexName = "note2")
@Data
public class NoteIndex {
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String authorId;
    @Field(type = FieldType.Keyword)
    private String authorName;
    @Field(type = FieldType.Integer, index = false)
    private Integer authority;
    @Field(type = FieldType.Date, index = false)
    private LocalDateTime createTime;
    @Field(type = FieldType.Date, index = false)
    private LocalDateTime updateTime;
    @Field(type = FieldType.Integer, index = false)
    private Integer downloads;
    @Field(type = FieldType.Integer, index = false)
    private Integer stars;
    @Field(type = FieldType.Integer, index = false)
    private Integer hates;
    @Field(type = FieldType.Integer, index = false)
    private Integer visits;
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String summary;
    @Field(type = FieldType.Text, analyzer = "whitespace")
    private String tags;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;


}
