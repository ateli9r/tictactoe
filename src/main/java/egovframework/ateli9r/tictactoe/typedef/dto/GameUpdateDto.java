package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameUpdateDto {
    private int gameId;
    private String playerId;
    private String msg;
}
