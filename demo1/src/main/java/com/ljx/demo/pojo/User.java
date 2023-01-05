package com.ljx.demo.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String user_name;
    private String password;
    private String email;
}
