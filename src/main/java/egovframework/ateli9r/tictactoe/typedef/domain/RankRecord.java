package egovframework.ateli9r.tictactoe.typedef.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankRecord {
    private int total;
    private int wins;
    private int losses;
    private int draws;
}
