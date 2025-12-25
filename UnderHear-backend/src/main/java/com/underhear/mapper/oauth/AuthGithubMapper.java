package com.underhear.mapper.oauth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGithub;

@Mapper
public interface AuthGithubMapper {

    //在oauth_github表中检测该用户是否存在
    @Select("select count(1) from user_github where github_id = #{githubId}")
    int countByGithubId(@Param("githubId") Long githubId);

    
    //github登录时注册插入user_github和user表记录
    @Insert("""
            insert into `user_github`
            (`uuid`, `github_id`, `name`, `avatar_url`, `email`, `bio`, `html_url`, `github_token`)
            values
            (#{uuid}, #{githubId}, #{name}, #{avatarUrl}, #{email}, #{bio}, #{htmlUrl}, #{githubToken})
            """)
    int insertUserGithub(UserGithub userGithub);

    @Insert("""
            insert into `user`
            (`uuid`, `nickname`, `avatar_url`, `create_at`, `update_at`)
            values
            (#{uuid}, #{nickName}, #{avatarUrl}, #{create_at}, #{update_at})
            """)
    int insertUser(User user);

    default void saveUserGithubAndUser(UserGithub userGithub, User user) {
        insertUserGithub(userGithub);
        insertUser(user);
    }
}
