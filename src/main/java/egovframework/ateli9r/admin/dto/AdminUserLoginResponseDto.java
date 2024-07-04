package egovframework.ateli9r.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserLoginResponseDto {
    private boolean success;
    private String msg;
}
