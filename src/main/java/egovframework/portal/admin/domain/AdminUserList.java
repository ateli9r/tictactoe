package egovframework.portal.admin.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserList {
    private String userId;
    private String email;
}
