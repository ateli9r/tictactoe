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

        StatusResponseDto respDto6 = model.signUp(SignUpFormDto.builder()
            .userId("test")
            .nickname("test")
            .email("test@test.com")
            .userPw("test@test.com")
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
        String verifyCode = model.createVerifyCode("test@test.com");
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

        StatusResponseDto respDto1 = model.findAccount(reqDto);
        assertTrue(respDto1.isSuccess());
        assertTrue(respDto1.getMsg().length() > 0); // accessToken

        FindApplyDto applyDto = FindApplyDto.builder()
            .findMode("findId")
            .email("test@test.com")
            .token("token")
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
            .userId("test")
            .email("test@test.com")
            .verifyCode("000000")
            .build();

        StatusResponseDto respDto1 = model.findAccount(reqDto);
        assertTrue(respDto1.isSuccess());
        assertTrue(respDto1.getMsg().length() > 0); // accessToken

        FindApplyDto applyDto = FindApplyDto.builder()
            .findMode("findPw")
            .email("test@test.com")
            .token("token")
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
            .gameId(1).chngrId("test").build());
        assertTrue(respDto4.isSuccess());
        assertEquals(respDto4.getMsg(), "");
    }

    /**
     * 게임 진행
     */
    @Test
    @Override
    public void testUpdateGameRoom() throws Exception {
        //  게임진행	T-06-0003	차례 변경	차례가 변경 될 때 마다 차례인 유저를 표시하여 준다.
        // 게임진행	T-06-0004	실시간 게임 진행	틱택토 게임을 통신으로 진행한다.	
        // 게임진행	T-06-0005	게임 결과 출력	게임이 완료 되면 각 유저에게 결과 창이 출력 되며, 갱신된 승률 정보가 표시된다.	
        // 게임진행	T-06-0006	게임 다시시작	게임 완료 후, 다시시작 버튼을 누르면 게임대기 상태로 돌아간다. 상대역시 다시시작 버튼을 누르면 게임이 시작 된다.      
        // 게임진행	T-06-0007	게임 나가기	게임 완료 후, 나가기 버튼을 누르면 메인 페이지로 이동한다.                  
        // 게임진행 - 로직	T-07-0001	예외처리-1	매칭이 완료 된 게임 방에서는 다른 플레이어가 입장 할 수 없다. (JOIN GAME버튼을 없애기 / 입장 불가 안내 창 띄우기 등.)
        // 게임진행 - 로직	T-07-0002	예외처리-2	자신의 차례가 아닌 사용자는 게임 판에 영향을 줄 수 없다.
        // 게임진행 - 로직	T-07-0003	예외처리-3	이미 마크 된 셀에는 영향을 줄 수 없다.
        // 게임진행 - 로직	T-07-0004	(O)승리조건 – 수평	플레이어(O)가 수평, 수직, 대각선을 채울 때 플레이어(O)가 우승 처리된다.
        // 게임진행 - 로직	T-07-0005	(X)승리조건 – 수평	플레이어(X)가 수평, 수직, 대각선을 채울 때 플레이어(X)가 우승 처리된다.
        // 게임진행 - 로직	T-07-0006	승리/패배 처리	우승 처리가 진행 된 후, 우승 한 플레이어는 승리안내 팝업을, 우승하지 못한 플레이어에게는 패배안내 팝업이 출력된다.
        // 게임진행 - 로직	T-07-0007	무승부 조건	9개의 셀이 모두 채워 졌을 때, 플레이어(O), 플레이어(X) 모두가 승리조건을 채우지 못했을 경우, 무승부 처리된다.
        // 게임진행 - 로직	T-07-0008	무승부 처리	무승부가 된 게임은 플레이어 모두에게 무승부안내 팝업이 출력된다.
        // 게임진행 - 로직	T-07-0009	승률 갱신	승리, 무승부, 패배 처리에 맞게 승률 정보가 수정된다.

        int gameId = 1;
        // status: P1
        // board: O...O...X

        StatusResponseDto respDto1 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test2").msg("B4").build());
        assertTrue(respDto1 != null);
        assertFalse(respDto1.isSuccess());
        assertEquals(respDto1.getMsg(), "해당 플레이어 차례가 아닙니다.");

        StatusResponseDto respDto2 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test1").msg("B4").build());
        assertTrue(respDto2 != null);
        assertFalse(respDto2.isSuccess());
        assertEquals(respDto2.getMsg(), "해당 위치에 놓을 수 없습니다.");

        StatusResponseDto respDto3 = model.updateGame(GameUpdateDto.builder().build());
        assertTrue(respDto3 != null);
        assertFalse(respDto3.isSuccess());
        assertEquals(respDto3.getMsg(), "게임방 아이디가 없습니다.");

        StatusResponseDto respDto4 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).msg("B4").build());
        assertTrue(respDto4 != null);
        assertFalse(respDto4.isSuccess());
        assertEquals(respDto4.getMsg(), "플레이어 아이디가 없습니다.");

        StatusResponseDto respDto5 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test1").msg("B123").build());
        assertTrue(respDto5 != null);
        assertFalse(respDto5.isSuccess());
        assertEquals(respDto5.getMsg(), "게임판 범위를 벗어납니다.");

        StatusResponseDto respDto6 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test1").msg("dummy_message").build());
        assertTrue(respDto6 != null);
        assertFalse(respDto6.isSuccess());
        assertEquals(respDto6.getMsg(), "지원되지 않는 메시지 형식입니다.");

        StatusResponseDto respDto8 = model.updateGame(GameUpdateDto.builder()
            .gameId(gameId).playerId("test1").msg("B1").build());
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
        int gameId = 1;
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

}
