package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpFormDto {
    private String userId;
    private String userPw;
    private String nickname;
    private String email;
    private String token;
}
