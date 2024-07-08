package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatusResponseDto {
    private boolean success;
    private String msg;    
}
