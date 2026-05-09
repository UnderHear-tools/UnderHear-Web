package com.underhear.mapper.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserProfileMarkdown;

@Mapper
public interface UserProfileMapper {

    @Select("""
            select *
            from `user`
            where `nickname` = #{nickname}
            """)
    User getUserByNickname(@Param("nickname") String nickname);

    @Select("""
            select *
            from `user_profile_markdown`
            where `uuid` = #{uuid}
            """)
    UserProfileMarkdown getMarkdownByUuid(@Param("uuid") String uuid);

    @Insert("""
            insert into `user_profile_markdown`
            (`uuid`, `content`)
            values
            (#{uuid}, #{content})
            on duplicate key update
                `content` = #{content},
                `updated_at` = current_timestamp
            """)
    int upsertMarkdown(@Param("uuid") String uuid, @Param("content") String content);
}
