package com.ljx.demo.mapper;

import com.ljx.demo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @InsertProvider(type = UserMapperProvider.class, method = "saveUserSQL")
    int saveUser(@Param("user_name") String user_name, @Param("password") String password, @Param("email") String email);

    @SelectProvider(type = UserMapperProvider.class, method = "selectUserSQL")
    User selectUser(@Param("user_name") String user_name, @Param("password") String password);

    @SelectProvider(type = UserMapperProvider.class, method = "findUserSQL")
    int findUser(@Param("user_name") String user_name);

    @DeleteProvider(type = UserMapperProvider.class, method = "deleteUserSQL")
    int deleteUser(@Param("user_name") String user_name, @Param("password") String password);
}
