package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 게임방 DTO
 */
@Getter
@Builder
public class GameRoomDto {
    private Long gameId;
    private String ownerId;
    private String chngrId;
    private String status;
    private String board;
}
