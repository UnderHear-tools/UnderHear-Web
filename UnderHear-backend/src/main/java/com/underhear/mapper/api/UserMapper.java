package com.underhear.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    //在github_user表中检测该用户是否存在

    @Select("select count(1) from user_github where github_id = #{githubId}")
    int countByGithubId(@Param("githubId") Long githubId);

    @Select("select count(1) from user_github where login = #{login}")
    int countByLogin(@Param("login") String login);
}
