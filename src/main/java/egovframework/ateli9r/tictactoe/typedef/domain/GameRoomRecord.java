package egovframework.ateli9r.tictactoe.typedef.domain;

import egovframework.ateli9r.tictactoe.typedef.dto.GameRoomDto;
import lombok.Builder;
import lombok.Getter;

/**
 * 게임방 레코드
 */
@Getter
@Builder
public class GameRoomRecord {
    private String ownerId;
    private String chngrId;
    private String status;
    private String board;

    public GameRoomDto toDto() throws Exception {
        return GameRoomDto.builder()
            .ownerId(ownerId)
            .chngrId(chngrId)
            .status(status)
            .board(board)
            .build();
    }
}
