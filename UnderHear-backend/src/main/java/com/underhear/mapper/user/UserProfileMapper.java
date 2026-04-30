package com.underhear.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.underhear.pojo.entity.User;

@Mapper
public interface UserProfileMapper {

    @Select("""
            select *
            from `user`
            where `nickname` = #{nickname}
            """)
    User getUserByNickname(@Param("nickname") String nickname);
}
