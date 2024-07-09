package egovframework.ateli9r.tictactoe.typedef.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMailFormDto {
    private String mailTo;
    private String title;
    private String content;
}
