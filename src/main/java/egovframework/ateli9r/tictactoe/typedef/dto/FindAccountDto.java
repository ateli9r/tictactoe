package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class FindAccountDto {
    private String findMode;
    private String email;
    private String verifyCode;
}
