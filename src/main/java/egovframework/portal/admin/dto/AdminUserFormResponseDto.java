package egovframework.portal.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserFormResponseDto {
    private boolean success;
    private String msg;
}
