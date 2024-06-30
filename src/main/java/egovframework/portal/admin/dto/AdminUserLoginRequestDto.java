package egovframework.portal.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserLoginRequestDto {
    private String userId;
    private String userPw;

    // public String getUserPw() {
    //     return "sha-256_hashed_password";
    // }
}
