package com.underhear.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.underhear.pojo.entity.User;

@Mapper
public interface UserMapper {

    @Select("""
            select u.*
            from user u
            join user_github ug on u.uuid = ug.uuid
            where ug.github_id = #{githubId}
            """)
    User findUserByGithubId(@Param("githubId") Long githubId);
}
