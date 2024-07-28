package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {
    private String userId;
    private String nickname;
    private String email;
    private int total;
    private int wins;
    private int losses;
    private int draws;
}
