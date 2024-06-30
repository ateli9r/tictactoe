package egovframework.portal.admin.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserSearch {
    private String keyword;
}
