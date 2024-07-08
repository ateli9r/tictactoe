package egovframework.ateli9r.tictactoe.typedef.domain;

import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoRecord {
    private String userId;
    private String nickname;

    private int total;
    private int wins;
    private int losses;
    private int draws;

    public UserInfoDto toDto() throws Exception {
        return UserInfoDto.builder()
            .userId(userId)
            .nickname(nickname)
            .total(total)
            .wins(wins)
            .losses(losses)
            .draws(draws)
            .build();
    }
}