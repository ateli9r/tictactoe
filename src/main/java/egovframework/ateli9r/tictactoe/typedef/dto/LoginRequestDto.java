package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
    private String userId;
    private String userPw;
}
