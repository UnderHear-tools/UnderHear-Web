package com.underhear.mapper.application;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.underhear.pojo.entity.Application;

@Mapper
public interface ApplicationCreateMapper {

    @Insert("""
            insert into `application`
            (`appid`, `owner_uuid`, `creation_method`, `framework`, `app_name`, `app_english_name`,
             `app_url`, `visibility`, `app_description`, `storage_path`, `original_filename`,
             `file_type`, `file_size`)
            values
            (#{appid}, #{ownerUuid}, #{creationMethod}, #{framework}, #{appName}, #{appEnglishName},
             #{appUrl}, #{visibility}, #{appDescription}, #{storagePath}, #{originalFilename},
             #{fileType}, #{fileSize})
            """)
    int insertApplication(Application application);
}
