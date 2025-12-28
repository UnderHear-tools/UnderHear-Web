package com.underhear.mapper.api;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.underhear.pojo.entity.User;

@Mapper
public interface UserMapper {

    @Select("""
            select u.*
            from user u
            join user_github ug on u.uuid = ug.uuid
            where ug.github_id = #{githubId}
            """)
    User getUserByGithubId(@Param("githubId") Long githubId);

    @Update("""
            update `user`
            set `last_login_at` = #{lastLoginAt},
                `last_login_source` = #{lastLoginSource}
            where `uuid` = #{uuid}
            """)
    int updateUserLastLoginByUuid(@Param("uuid") String uuid,
                                  @Param("lastLoginAt") LocalDateTime lastLoginAt,
                                  @Param("lastLoginSource") String lastLoginSource);
}
