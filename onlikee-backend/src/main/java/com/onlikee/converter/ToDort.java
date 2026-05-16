package com.onlikee.converter;

import com.alibaba.fastjson.JSONObject;
import com.onlikee.pojo.dto.request.UserGiteeDort;
import com.onlikee.pojo.dto.request.UserGithubDort;

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

    public static UserGiteeDort toUserGiteeDort(Object rawUserInfo, AuthToken token) {
        JSONObject rawUserInfoJSON = (JSONObject) rawUserInfo;
        UserGiteeDort userGiteeDort = new UserGiteeDort();
        userGiteeDort.setGiteeId(rawUserInfoJSON.getLong("id"));
        userGiteeDort.setName(rawUserInfoJSON.getString("name"));
        userGiteeDort.setAvatarUrl(rawUserInfoJSON.getString("avatar_url"));
        userGiteeDort.setEmail(rawUserInfoJSON.getString("email"));
        userGiteeDort.setBio(rawUserInfoJSON.getString("bio"));
        userGiteeDort.setHtmlUrl(rawUserInfoJSON.getString("html_url"));
        userGiteeDort.setGiteeToken(token.getAccessToken());
        return userGiteeDort;
    }

}
