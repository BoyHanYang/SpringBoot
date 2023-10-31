package com.example.springboot.entity;

import lombok.Data;

/**
 * @Author yangbohan
 * @Date 2023/10/23 23:55
 */
@Data
public class User {

    private Integer id;

    private String userName;

    private String password;

    private String nickName;

    private String avatar;

}
