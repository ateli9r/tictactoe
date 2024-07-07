package egovframework.ateli9r.tictactoe.typedef.domain;

import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRecord {
    private String userId;
    private String nickname;
    private RankRecord rank;

    public UserInfoDto toDto() throws Exception {
        return UserInfoDto.builder()
            .userId(userId)
            .nickname(nickname)
            .total(this.rank.getTotal())
            .total(this.rank.getWins())
            .total(this.rank.getLosses())
            .total(this.rank.getDraws())
            .build();
    }
}
