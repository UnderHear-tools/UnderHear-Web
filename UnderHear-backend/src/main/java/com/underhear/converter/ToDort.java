package com.underhear.converter;

import com.alibaba.fastjson.JSONObject;
import com.underhear.pojo.dto.request.UserGithubDort;

import me.zhyd.oauth.model.AuthToken;

public final class ToDort {

    private ToDort() {
    }

    public static UserGithubDort toUserGithubDort(Object rawUserInfo, AuthToken token) {
        JSONObject rawUserInfoJSON = (JSONObject) rawUserInfo;
        UserGithubDort userGithubDort = new UserGithubDort();
        userGithubDort.setGithubId(rawUserInfoJSON.getLong("id"));
        userGithubDort.setName(rawUserInfoJSON.getString("name"));
        userGithubDort.setAvatarUrl(rawUserInfoJSON.getString("avatar_url"));
        userGithubDort.setEmail(rawUserInfoJSON.getString("email"));
        userGithubDort.setBio(rawUserInfoJSON.getString("bio"));
        userGithubDort.setHtmlUrl(rawUserInfoJSON.getString("html_url"));
        userGithubDort.setGithubToken(token.getAccessToken());
        return userGithubDort;
    }

}
