package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CreateGameDto {
    @Setter
    private Long gameId;
    
    private String title;
    private String ownerId;
}
