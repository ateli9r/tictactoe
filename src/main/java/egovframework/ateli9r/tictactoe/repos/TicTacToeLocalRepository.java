package egovframework.ateli9r.tictactoe.repos;

import egovframework.ateli9r.tictactoe.typedef.domain.RankRecord;
import egovframework.ateli9r.tictactoe.typedef.domain.UserRecord;

public class TicTacToeLocalRepository implements TicTacToeRepository {

    @Override
    public boolean login(String userId, String userPw) {
        if (userId.equals("login_ok")
            && userPw.equals("31e14f8175cb0980deb6ae418d94e9bcd25e3820a4a587ec1038f383faa525d4")) {
            return true;
        }
        return false;
    }

    @Override
    public UserRecord getUserInfo(String userId) {
        if (userId.equals("login_ok")) {
            return UserRecord.builder()
                .userId("login_ok")
                .nickname("테스트")
                .rank(RankRecord.builder()
                    .total(123)
                    .wins(50)
                    .losses(50)
                    .draws(23)
                    .build())
                .build();
        }
        return null;
    }

}
