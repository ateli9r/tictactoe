package tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import egovframework.ateli9r.tictactoe.model.TicTacToeModel;
import egovframework.ateli9r.tictactoe.repos.MessageLocalRepository;
import egovframework.ateli9r.tictactoe.repos.MessageRepository;
import egovframework.ateli9r.tictactoe.repos.TicTacToeLocalRepository;
import egovframework.ateli9r.tictactoe.repos.TicTacToeRepository;
import egovframework.ateli9r.tictactoe.typedef.dto.CreateGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.FindAccountDto;
import egovframework.ateli9r.tictactoe.typedef.dto.FindApplyDto;
import egovframework.ateli9r.tictactoe.typedef.dto.GameRoomDto;
import egovframework.ateli9r.tictactoe.typedef.dto.GameUpdateDto;
import egovframework.ateli9r.tictactoe.typedef.dto.JoinGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginRequestDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SendMailFormDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SignUpFormDto;
import egovframework.ateli9r.tictactoe.typedef.dto.StatusResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TicTacToeLocalTest implements TicTacToeTest {
    private TicTacToeModel model;

    /**
     * 테스트 객체 초기화
     */
    @BeforeEach
    public void setup() {
        TicTacToeRepository tttRepos = new TicTacToeLocalRepository();
        MessageRepository msgRepos = new MessageLocalRepository();
        model = new TicTacToeModel(tttRepos, msgRepos);
    }

    /**
     * 로그인 실패
     */
    @Test
    @Override
    public void testLoginFail() throws Exception {
        LoginRequestDto req = LoginRequestDto.builder()
            .userId("login_fail").userPw("login_fail").build();

        assertEquals(req.getUserId(), "login_fail");
        assertEquals(model.hash(req.getUserPw()), "62aedc27a715c727542d1c9aff0f24202149a9858c09bae7c71b7fcd9f5d03dd");

        StatusResponseDto resp = model.login(req);
        assertFalse(resp.isSuccess());
        assertEquals(resp.getMsg(), "로그인 실패");
    }

    /**
     * 로그인 성공
     */
    @Test
    @Override
    public void testLoginSuccess() throws Exception {
        LoginRequestDto req = LoginRequestDto.builder()
            .userId("login_ok").userPw("login_ok").build();
        
        assertEquals(req.getUserId(), "login_ok");
        assertEquals(model.hash(req.getUserPw()), "31e14f8175cb0980deb6ae418d94e9bcd25e3820a4a587ec1038f383faa525d4");

        StatusResponseDto resp = model.login(req);
        assertTrue(resp.isSuccess());
        assertEquals(resp.getMsg(), "");

        UserInfoDto user = model.getUserInfo(req.getUserId());
        assertTrue(user != null);
    }

    /**
     * 회원가입
     */
    @Test
    @Override
    public void testCreateUser() throws Exception {
        StatusResponseDto respDto1 = model.signUp(SignUpFormDto.builder()
            .userId("")
            .nickname("")
            .email("")
            .userPw("")
            .build());

        assertNotNull(respDto1);
        assertEquals(respDto1.isSuccess(), false);
        assertEquals(respDto1.getMsg(), "아이디를 입력해 주세요.");

        StatusResponseDto respDto2 = model.signUp(SignUpFormDto.builder()
            .userId("test")
            .nickname("")
            .email("")
            .userPw("")
            .build());

        assertNotNull(respDto2);
        assertEquals(respDto2.isSuccess(), false);
        assertEquals(respDto2.getMsg(), "닉네임을 입력해 주세요.");

        StatusResponseDto respDto3 = model.signUp(SignUpFormDto.builder()
            .userId("test")
            .nickname("test")
            .email("")
            .userPw("")
            .build());

        assertNotNull(respDto3);
        assertEquals(respDto3.isSuccess(), false);
        assertEquals(respDto3.getMsg(), "이메일을 입력해 주세요.");

        StatusResponseDto respDto4 = model.signUp(SignUpFormDto.builder()
            .userId("test")
            .nickname("test")
            .email("test@test.com")
            .userPw("")
            .build());

        assertNotNull(respDto4);
        assertEquals(respDto4.isSuccess(), false);
        assertEquals(respDto4.getMsg(), "패스워드를 입력해 주세요.");

        StatusResponseDto respDto5 = model.signUp(SignUpFormDto.builder()
            .userId("exists_user")
            .nickname("exists_user")
            .email("exists_user@exists_user.com")
            .userPw("exists_user@exists_user.com")
            .build());

        assertNotNull(respDto5);
        assertEquals(respDto5.isSuccess(), false);
        assertEquals(respDto5.getMsg(), "이미 가입되어 있는 아이디입니다.");

        model.setTestStatus("signUp1");

        StatusResponseDto respDto6 = model.signUp(SignUpFormDto.builder()
            .userId("test")
            .nickname("test")
            .email("test@test.com")
            .userPw("test@test.com")
            .token("accessToken")
            .build());

        assertNotNull(respDto6);
        assertTrue(respDto6.isSuccess());
        assertEquals(respDto6.getMsg(), "");
    }

    /**
     * 인증번호 생성
     */
    @Test
    @Override
    public void testCreateVerifyCode() throws Exception {
        String verifyCode = model.createVerifyCode();
        assertNotNull(verifyCode);
        assertTrue(verifyCode.length() == 6);
        assertTrue(verifyCode.chars().allMatch(Character::isDigit));
    }

    /**
     * 인증 이메일 발송
     */
    @Test
    @Override
    public void testSendVerifyEmail() throws Exception {
        SendMailFormDto reqDto = SendMailFormDto.builder()
            .mailTo("test@test.com")
            .build();

        StatusResponseDto respDto = model.sendVerifyEmail(reqDto);
        assertNotNull(respDto);
        assertTrue(respDto.isSuccess());
        assertEquals(respDto.getMsg(), "");
    }

    /**
     * 아이디 찾기
     */
    @Test
    @Override
    public void testFindUserId() throws Exception {
        FindAccountDto reqDto = FindAccountDto.builder()
            .findMode("findId")
            .email("test@test.com")
            .verifyCode("000000")
            .build();
        
        // 테스트 상태 설정
        model.setTestStatus("testVerifyCode");

        StatusResponseDto respDto1 = model.findAccount(reqDto);
        assertTrue(respDto1.isSuccess());
        assertTrue(respDto1.getMsg().length() > 0); // accessToken

        // 작업 토큰 가져오기
        String accessToken = (String) model.getTestStatus("testFindUserId > accessToken");

        FindApplyDto applyDto = FindApplyDto.builder()
            .findMode("findId")
            .email("test@test.com")
            .token(accessToken)
            .build();
        
        StatusResponseDto respDto2 = model.findApply(applyDto);
        assertTrue(respDto2.isSuccess());
        assertTrue(respDto2.getMsg().length() > 0); // userId
    }

    /**
     * 비밀번호 찾기
     */
    @Test
    @Override
    public void testFindUserPw() throws Exception {
        FindAccountDto reqDto = FindAccountDto.builder()
            .findMode("findPw")
            .email("test@test.com")
            .verifyCode("000000")
            .build();

        // 테스트 상태 설정
        model.setTestStatus("testVerifyCode");

        StatusResponseDto respDto1 = model.findAccount(reqDto);
        assertTrue(respDto1.isSuccess());
        assertTrue(respDto1.getMsg().length() > 0); // accessToken

        // 작업 토큰 가져오기
        String accessToken = (String) model.getTestStatus("testFindUserPw > accessToken");

        FindApplyDto applyDto = FindApplyDto.builder()
            .findMode("findPw")
            .email("test@test.com")
            .token(accessToken)
            .message("password=password")
            .build();

        StatusResponseDto respDto2 = model.findApply(applyDto);
        assertTrue(respDto2.isSuccess());
        assertEquals(respDto2.getMsg(), "");
    }

    /**
     * 게임 전적 목록
     */
    @Test
    @Override
    public void testListGameRank() throws Exception {
        List<UserInfoDto> listUser = model.listGameRank();
        assertTrue(listUser != null);
        assertTrue(listUser.size() > 0);

        UserInfoDto userDto = listUser.get(0);
        assertTrue(userDto.getUserId() != null && !userDto.getUserId().isEmpty());
        assertTrue(userDto.getNickname() != null && !userDto.getNickname().isEmpty());

        int expTotal = userDto.getWins() + userDto.getLosses() + userDto.getDraws();
        assertEquals(userDto.getTotal(), expTotal);
    }

    /**
     * 게임 생성
     */
    @Test
    @Override
    public void testCreateGameRoom() throws Exception {
        StatusResponseDto respDto1 = model.createGame(CreateGameDto.builder().build());
        assertFalse(respDto1.isSuccess());
        assertEquals(respDto1.getMsg(), "제목을 입력 하세요.");
        
        StatusResponseDto respDto2 = model.createGame(CreateGameDto.builder()
            .title("game title")
            .build());
        assertFalse(respDto2.isSuccess());
        assertEquals(respDto2.getMsg(), "방장 아이디가 없습니다.");

        StatusResponseDto respDto3 = model.createGame(CreateGameDto.builder()
            .title("game title")
            .ownerId("test")
            .build());
        assertTrue(respDto3.isSuccess());
        assertEquals(respDto3.getMsg(), "");
    }

    /**
     * 게임 참가
     */
    @Test
    @Override
    public void testJoinGameRoom() throws Exception {
        StatusResponseDto respDto1 = model.joinGame(JoinGameDto.builder().build());
        assertFalse(respDto1.isSuccess());
        assertEquals(respDto1.getMsg(), "게임방 참여중 오류가 발생했습니다.");
        
        StatusResponseDto respDto2 = model.joinGame(JoinGameDto.builder()
            .gameId(1).build());
        assertFalse(respDto2.isSuccess());
        assertEquals(respDto2.getMsg(), "게임방 참여중 오류가 발생했습니다.");

        StatusResponseDto respDto3 = model.joinGame(JoinGameDto.builder()
            .chngrId("test").build());
        assertFalse(respDto3.isSuccess());
        assertEquals(respDto3.getMsg(), "게임방 참여중 오류가 발생했습니다.");

        StatusResponseDto respDto4 = model.joinGame(JoinGameDto.builder()
            .gameId(1).chngrId("user1").build());
        assertTrue(respDto4.isSuccess());
        assertEquals(respDto4.getMsg(), "");
    }

    /**
     * 게임 진행
     */
    @Test
    @Override
    public void testUpdateGameRoom() throws Exception {
        int gameId = 2;

        StatusResponseDto respDto1 = model.updateGame(GameUpdateDto.builder().build());
        assertTrue(respDto1 != null);
        assertFalse(respDto1.isSuccess());
        assertEquals(respDto1.getMsg(), "게임방 아이디가 없습니다.");

        StatusResponseDto respDto2 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).msg("B4").build());
        assertTrue(respDto2 != null);
        assertFalse(respDto2.isSuccess());
        assertEquals(respDto2.getMsg(), "플레이어 아이디가 없습니다.");

        StatusResponseDto respDto3 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test2").msg("B4").build());
        assertTrue(respDto3 != null);
        assertFalse(respDto3.isSuccess());
        assertEquals(respDto3.getMsg(), "해당 플레이어 차례가 아닙니다.");

        StatusResponseDto respDto4 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test").msg("B4").build());
        assertTrue(respDto4 != null);
        assertFalse(respDto4.isSuccess());
        assertEquals(respDto4.getMsg(), "해당 위치에 놓을 수 없습니다.");

        StatusResponseDto respDto5 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test").msg("B123").build());
        assertTrue(respDto5 != null);
        assertFalse(respDto5.isSuccess());
        assertEquals(respDto5.getMsg(), "게임판 범위를 벗어납니다.");

        StatusResponseDto respDto6 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test").msg("dummy_message").build());
        assertTrue(respDto6 != null);
        assertFalse(respDto6.isSuccess());
        assertEquals(respDto6.getMsg(), "지원되지 않는 메시지 형식입니다.");

        StatusResponseDto respDto8 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test").msg("B1").build());
        assertTrue(respDto8 != null);
        assertTrue(respDto8.isSuccess());
        assertEquals(respDto8.getMsg(), "");        
    }

    /**
     * 게임 정보 조회
     */
    @Test
    @Override
    public void testViewGameRoom() throws Exception {
        int gameId = 2;
        GameRoomDto gameDto = model.getGameRoom(gameId);

        assertTrue(gameDto != null);
        assertTrue(gameDto.getOwnerId() != null && !gameDto.getOwnerId().isEmpty());
        assertTrue(gameDto.getChngrId() != null && !gameDto.getChngrId().isEmpty());
        assertTrue(gameDto.getStatus() != null && !gameDto.getStatus().isEmpty());
        assertTrue(gameDto.getBoard() != null && !gameDto.getBoard().isEmpty());
    }

    /**
     * 게임 리스트 조회
     */
    @Test
    @Override
    public void testListGameRoom() throws Exception {
        List<GameRoomDto> listGame = model.listGameRoom();
        assertTrue(listGame != null);
        assertTrue(listGame.size() > 0);

        GameRoomDto gameDto = listGame.get(0);
        assertTrue(gameDto.getOwnerId() != null && !gameDto.getOwnerId().isEmpty());
        assertTrue(gameDto.getChngrId() != null && !gameDto.getChngrId().isEmpty());
        assertTrue(gameDto.getStatus() != null && !gameDto.getStatus().isEmpty());
        assertTrue(gameDto.getBoard() != null && !gameDto.getBoard().isEmpty());
    }

    /**
     * 이메일로 사용자 정보 가져오기
     */
    @Test
    @Override
    public void testUserInfoByEmail() throws Exception {
        UserInfoDto userInfo1 = model.getUserInfoByEmail("dummy@email.com");
        assertNull(userInfo1);

        UserInfoDto userInfo2 = model.getUserInfoByEmail("test@test.com");
        assertNotNull(userInfo2);
        assertEquals(userInfo2.getUserId(), "test");
    }

    /**
     * 사용자 패스워드 변경
     */
    @Test
    @Override
    public void testChangePassword() throws Exception {
        boolean isOk1 = model.changePassword("dummy", "password");
        assertFalse(isOk1);

        boolean isOk2 = model.changePassword("test", "password");
        assertTrue(isOk2);
    }
}
