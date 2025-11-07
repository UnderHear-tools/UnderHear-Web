package com.underhear.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.underhear.entity.User;

@Mapper
public interface UserMapper {

    @Select("SELECT id, github_id, login, name, avatar_url, email, bio, html_url, github_token, created_at, updated_at " +
            "FROM user WHERE github_id = #{githubId}")
    User findByGithubId(@Param("githubId") Long githubId);

    @Select("SELECT id, github_id, login, name, avatar_url, email, bio, html_url, github_token, created_at, updated_at " +
            "FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Insert("INSERT INTO user (github_id, login, name, avatar_url, email, bio, html_url, github_token) " +
            "VALUES (#{githubId}, #{login}, #{name}, #{avatarUrl}, #{email}, #{bio}, #{htmlUrl}, #{githubToken})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE user SET login = #{login}, name = #{name}, avatar_url = #{avatarUrl}, email = #{email}, " +
            "bio = #{bio}, html_url = #{htmlUrl}, github_token = #{githubToken}, updated_at = NOW() WHERE id = #{id}")
    void update(User user);
}

