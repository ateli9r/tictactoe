package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateGameDto {
    private String title;
    private String ownerId;
}
