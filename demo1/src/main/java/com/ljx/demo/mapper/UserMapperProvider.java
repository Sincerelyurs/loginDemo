package com.ljx.demo.mapper;

import org.apache.ibatis.jdbc.SQL;

public class UserMapperProvider {
    public String saveUserSQL(){
        return new SQL()
                .INSERT_INTO("user")
                .VALUES("user_name", "#{user_name}")
                .VALUES("password", "#{password}")
                .VALUES("email", "#{email}")
                .toString();
    }

    public String selectUserSQL(){
        return new SQL()
                .SELECT("*")
                .FROM("user")
                .WHERE("`user_name`="+"'${user_name}'")
                .WHERE("`password`="+"'${password}'")
                .toString();
    }

    public String findUserSQL(){
        return new SQL()
                .SELECT("COUNT(*)")
                .FROM("user")
                .WHERE("`user_name`="+"'${user_name}'")
                .toString();
    }

    public String deleteUserSQL(){
        return new SQL()
                .DELETE_FROM("user")
                .WHERE("`user_name`="+"'${user_name}'")
                .WHERE("`password`="+"'${password}'")
                .toString();
    }
}
