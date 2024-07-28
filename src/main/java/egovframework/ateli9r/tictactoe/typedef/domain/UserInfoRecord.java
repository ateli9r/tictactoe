package egovframework.ateli9r.tictactoe.typedef.domain;

import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자정보 레코드
 */
@Getter
@Builder
public class UserInfoRecord {
    private String userId;
    private String nickname;
    private String email;

    private int total;
    private int wins;
    private int losses;
    private int draws;

    public UserInfoDto toDto() throws Exception {
        return UserInfoDto.builder()
            .userId(userId)
            .nickname(nickname)
            .email(email)
            .total(total)
            .wins(wins)
            .losses(losses)
            .draws(draws)
            .build();
    }
}
