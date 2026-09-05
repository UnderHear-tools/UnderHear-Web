package com.onlikee.auth.oauth.converter;

import com.alibaba.fastjson.JSONObject;
import com.onlikee.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.auth.oauth.model.dto.UserGithubDTO;
import me.zhyd.oauth.model.AuthToken;

public final class ToDTO {

    private ToDTO() {
    }

    public static UserGithubDTO toUserGithubDTO(Object rawUserInfo, AuthToken token) {
        JSONObject rawUserInfoJSON = (JSONObject) rawUserInfo;
        UserGithubDTO userGithubDTO = new UserGithubDTO();
        userGithubDTO.setGithubId(rawUserInfoJSON.getLong("id"));
        userGithubDTO.setName(rawUserInfoJSON.getString("name"));
        userGithubDTO.setAvatarUrl(rawUserInfoJSON.getString("avatar_url"));
        userGithubDTO.setEmail(rawUserInfoJSON.getString("email"));
        userGithubDTO.setBio(rawUserInfoJSON.getString("bio"));
        userGithubDTO.setHtmlUrl(rawUserInfoJSON.getString("html_url"));
        userGithubDTO.setGithubToken(token.getAccessToken());
        return userGithubDTO;
    }

    public static UserGiteeDTO toUserGiteeDTO(Object rawUserInfo, AuthToken token) {
        JSONObject rawUserInfoJSON = (JSONObject) rawUserInfo;
        UserGiteeDTO userGiteeDTO = new UserGiteeDTO();
        userGiteeDTO.setGiteeId(rawUserInfoJSON.getLong("id"));
        userGiteeDTO.setName(rawUserInfoJSON.getString("name"));
        userGiteeDTO.setAvatarUrl(rawUserInfoJSON.getString("avatar_url"));
        userGiteeDTO.setEmail(rawUserInfoJSON.getString("email"));
        userGiteeDTO.setBio(rawUserInfoJSON.getString("bio"));
        userGiteeDTO.setHtmlUrl(rawUserInfoJSON.getString("html_url"));
        userGiteeDTO.setGiteeToken(token.getAccessToken());
        return userGiteeDTO;
    }
}
