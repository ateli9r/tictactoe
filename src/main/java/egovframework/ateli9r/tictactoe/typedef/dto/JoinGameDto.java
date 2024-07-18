package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinGameDto {
    private int gameId;
    private String chngrId;
}
