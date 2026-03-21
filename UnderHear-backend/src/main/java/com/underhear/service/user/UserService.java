package com.underhear.service.user;

import com.underhear.pojo.entity.User;

public interface UserService {

    // 鏍规嵁 UUID 鏌ョ敤鎴凤紝鏌ヤ笉鍒板氨杩斿洖绌?
    User getUserByUuid(String uuid);

    // 鏍规嵁 GitHub ID 鏌ョ敤鎴凤紝鏌ヤ笉鍒板氨杩斿洖绌?
    User getUserByGithubId(Long githubId);

    // 鏍规嵁 Gitee ID 鏌ョ敤鎴凤紝鏌ヤ笉鍒板氨杩斿洖绌?
    User getUserByGiteeId(Long giteeId);

    // 鏇存柊鏈€鍚庣櫥褰曚俊鎭紝杩斿洖鍙楀奖鍝嶈鏁?
    int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource);

    // 璁板綍涓€娆＄櫥褰曟潵婧愶紝杩斿洖鍙楀奖鍝嶈鏁?
    int insertUserLoginRecord(String uuid, String loginSource);
}
