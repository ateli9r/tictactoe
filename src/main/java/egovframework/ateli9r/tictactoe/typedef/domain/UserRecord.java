package egovframework.ateli9r.tictactoe.typedef.domain;

import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;

public class UserRecord {
    public String userId;
    public String nickname;
    public RankRecord rank;

    public UserInfoDto toDto() throws Exception {
        return UserInfoDto.builder()
            .userId(userId)
            .nickname(nickname)
            .total(this.rank.total)
            .total(this.rank.wins)
            .total(this.rank.losses)
            .total(this.rank.draws)
            .build();
    }
}
