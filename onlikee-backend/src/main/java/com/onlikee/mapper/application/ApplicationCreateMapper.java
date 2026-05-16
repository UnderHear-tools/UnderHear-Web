package com.onlikee.mapper.application;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.onlikee.pojo.entity.Application;

@Mapper
public interface ApplicationCreateMapper {

    @Select("""
            select count(1)
            from `application`
            where `app_english_name` = #{appEnglishName}
            """)
    int countByAppEnglishName(@Param("appEnglishName") String appEnglishName);

    @Insert("""
            insert into `application`
            (`appid`, `owner_uuid`, `creation_method`, `framework`, `app_name`, `app_english_name`,
             `app_url`, `visibility`, `app_description`, `original_filename`,
             `original_file_type`, `original_file_size`)
            values
            (#{appid}, #{ownerUuid}, #{creationMethod}, #{framework}, #{appName}, #{appEnglishName},
             #{appUrl}, #{visibility}, #{appDescription}, #{originalFilename},
             #{originalFileType}, #{originalFileSize})
            """)
    int insertApplication(Application application);
}
