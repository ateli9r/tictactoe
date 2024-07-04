package egovframework.ateli9r.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserFormRequestDto {
    private String userId;
    private String userPw;
    private String email;
}
