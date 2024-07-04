package egovframework.ateli9r.admin.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserData {
    private String userId;
    private String email;
}
