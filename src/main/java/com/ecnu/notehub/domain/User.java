package com.ecnu.notehub.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author onion
 * @date 2019/11/7 -1:31 下午
 */
@Document(collection = "user")
@Data
public class User implements Serializable {
    @Id
    private String id;
    private String account;
    private String password;
    private Integer downloads;
    private Integer collects;
    private Integer publishes;
}
