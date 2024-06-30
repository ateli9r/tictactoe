package egovframework.portal.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserSearchRequestDto {
    private String keyword;
}
