package egovframework.ateli9r.tictactoe.repos;

import java.util.ArrayList;
import java.util.List;

import egovframework.ateli9r.tictactoe.typedef.domain.GameRoomRecord;
import egovframework.ateli9r.tictactoe.typedef.domain.UserInfoRecord;
import egovframework.ateli9r.tictactoe.typedef.dto.CreateGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.JoinGameDto;
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
        } else if (userId.equals("test")) {
            return UserInfoRecord.builder()
                .userId("test")
                .nickname("test")
                .email("test@test.com")
                .total(123)
                .wins(50)
                .losses(50)
                .draws(23)
                .build();
        } else if (userId.equals("user1")) {
            return UserInfoRecord.builder()
                .userId("user1")
                .nickname("nickname1")
                .email("user1@example.com")
                .total(78)
                .wins(41)
                .losses(24)
                .draws(13)
                .build();
        }

        return null;
    }

    @Override
    public int signUp(SignUpFormDto request) {
        int chk = 0;
        if (request.getUserId() != null && request.getUserId().length() > 0) chk++;
        if (request.getNickname() != null && request.getNickname().length() > 0) chk++;
        if (request.getEmail() != null && request.getEmail().length() > 0) chk++;
        if (request.getUserPw() != null && request.getUserPw().length() > 0) chk++;
        if (chk == 4) return 1;
        return 0;
    }

    @Override
    public int createGame(CreateGameDto request) {
        int chk = 0;
        if (request.getTitle() != null && !request.getTitle().isEmpty()) chk++;
        if (request.getOwnerId() != null && !request.getOwnerId().isEmpty()) chk++;
        if (chk == 2) return 1;
        return 0;
    }

    @Override
    public List<GameRoomRecord> listGameRoom() {
        List<GameRoomRecord> ret = new ArrayList<>();
        ret.add(GameRoomRecord.builder()
            .ownerId("owner")
            .chngrId("chngr")
            .status("status")
            .board(".........")
            .build());
        return ret;
    }

    @Override
    public int joinGame(JoinGameDto request) {
        int chk = 0;
        if (request.getGameId() > 0) chk++;
        if (request.getChngrId() != null && request.getChngrId().length() > 0) chk++;
        if (chk == 2) return 1;
        return 0;
    }

    @Override
    public GameRoomRecord getGameRoom(int gameId) {
        if (gameId == 1) {
            return GameRoomRecord.builder()
            .ownerId("test")
            .status("W")
            .board(".........")
            .build();
        } else if (gameId == 2) {
            return GameRoomRecord.builder()
            .ownerId("test")
            .chngrId("user46")
            .status("P1")
            // .board("O...O...X")
            .board("....O....")
            .build();
        }
        return null;
    }

    @Override
    public List<UserInfoRecord> listGameRank() {
        List<UserInfoRecord> ret = new ArrayList<>();
        ret.add(UserInfoRecord.builder()
            .userId("test")
            .nickname("test")
            .total(123)
            .wins(100)
            .losses(20)
            .draws(3)
            .build());
        ret.add(UserInfoRecord.builder()
            .userId("user1")
            .nickname("nickname1")
            .total(78)
            .wins(41)
            .losses(24)
            .draws(13)
            .build());
        return ret;
    }

    @Override
    public int updateGame(GameRoomRecord updateGameRoom) {
        return 1;
    }

    /**
     * 테스트 데이터 삭제
     */
    @Override
    public int deleteTestData(String key) {
        return 0;
    }

    /**
     * 테스트 데이터 수정
     */
    @Override
    public int updateTestData(String key) {
        return 0;
    }

    /**
     * 테스트 데이터 등록
     */
    @Override
    public int insertTestData(String key) {
        return 0;
    }

    /**
     * 이메일로 사용자 정보 가져오기
     */
    @Override
    public UserInfoRecord getUserInfoRecordByEmail(String email) {
        if (email.equals("test@test.com")) {
            return this.getUserInfo("test");
        }
        return null;
    }

    /**
     * 사용자 패스워드 변경
     */
    @Override
    public int changePassword(String userId, String userPw) {
        if (userId.equals("test")) {
            return 1;
        }
        return 0;
    }
}
