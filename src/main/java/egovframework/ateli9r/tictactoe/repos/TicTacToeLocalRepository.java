package egovframework.ateli9r.tictactoe.repos;

import egovframework.ateli9r.tictactoe.typedef.domain.UserInfoRecord;
import egovframework.ateli9r.tictactoe.typedef.dto.SignUpFormDto;

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
    public UserInfoRecord getUserInfo(String userId) {
        if (userId.equals("login_ok")) {
            return UserInfoRecord.builder()
                .userId("login_ok")
                .nickname("테스트")
                .total(123)
                .wins(50)
                .losses(50)
                .draws(23)
                .build();
        }
        return null;
    }

    @Override
    public int signUp(SignUpFormDto request) {
        int chk = 0;
        if (request.getUserId().length() > 0) chk++;
        if (request.getNickname().length() > 0) chk++;
        if (request.getEmail().length() > 0) chk++;
        if (request.getPassword().length() > 0) chk++;
        if (chk == 4) return 1;
        return 0;
    }
}
