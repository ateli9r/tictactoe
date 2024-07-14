package tictactoe;

import org.junit.jupiter.api.Test;

import egovframework.ateli9r.tictactoe.model.TicTacToeModel;
import egovframework.ateli9r.tictactoe.repos.MessageLocalRepository;
import egovframework.ateli9r.tictactoe.repos.MessageRepository;
import egovframework.ateli9r.tictactoe.repos.TicTacToeLocalRepository;
import egovframework.ateli9r.tictactoe.repos.TicTacToeRepository;
import egovframework.ateli9r.tictactoe.typedef.dto.CreateGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.FindAccountDto;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginRequestDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SendMailFormDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SignUpFormDto;
import egovframework.ateli9r.tictactoe.typedef.dto.StatusResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TicTacToeModelTest {
    private final TicTacToeModel model;

    /**
     * 생성자
     */
    public TicTacToeModelTest() {
        TicTacToeRepository tttRepos = new TicTacToeLocalRepository();
        MessageRepository msgRepos = new MessageLocalRepository();
        model = new TicTacToeModel(tttRepos, msgRepos);
    }

    /**
     * 로그인
     */
    @Test
    public void testLoginFail() throws Exception {
        /*
         * # 전제조건
         * - 아이디와 비밀번호를 알고 있다
         * 
         * # 종료조건
         * - 세션이 생성되어야 한다
         */

        //  로그인	T-01-0001	로그인 성공	아이디와 비밀번호 란에 올바른 값이 입력 된 뒤 로그인 버튼을 누르면 메인 페이지로 접속 된다.
        //  로그인	T-01-0002	로그인 실패	아이디와 비밀번호 란에 올바르지 않은 값, 혹은 비어 있는 값이 입력 된 뒤에 로그인 버튼을 누르면 오류사항 안내를 출력한다.
        // 메인	T-05-0001	페이지 접속	로그인이 성공하면 메인 페이지로 이동한다.         


        LoginRequestDto req = LoginRequestDto.builder()
            .userId("login_fail").userPw("login_fail").build();

        assertEquals(req.getUserId(), "login_fail");
        assertEquals(model.hash(req.getUserPw()), "62aedc27a715c727542d1c9aff0f24202149a9858c09bae7c71b7fcd9f5d03dd");

        StatusResponseDto resp = model.login(req);
        assertFalse(resp.isSuccess());
        assertEquals(resp.getMsg(), "로그인 실패");
    }

    @Test
    public void testLoginSuccess() throws Exception {
        /*
         * # 전제조건
         * - 아이디와 비밀번호를 알고 있다
         * 
         * # 종료조건
         * - 세션이 생성되어야 한다
         */

        //  로그인	T-01-0001	로그인 성공	아이디와 비밀번호 란에 올바른 값이 입력 된 뒤 로그인 버튼을 누르면 메인 페이지로 접속 된다.
        //  로그인	T-01-0002	로그인 실패	아이디와 비밀번호 란에 올바르지 않은 값, 혹은 비어 있는 값이 입력 된 뒤에 로그인 버튼을 누르면 오류사항 안내를 출력한다.
        // 메인	T-05-0001	페이지 접속	로그인이 성공하면 메인 페이지로 이동한다.         


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
     * 로그아웃
     */
    @Test
    public void testLogout() throws Exception {
        /*
         * # 전제조건
         * - 세션이 생성되어 있다
         * 
         * # 종료조건
         * - 세션이 삭제되어야 한다
         */

        //  메인 - 설정	T-05-0010	로그아웃	메인 설정 창에서 로그아웃 클릭 시, 로그아웃 되어 게임 입장 페이지로 이동한다.         
        
    }


    /**
     * 회원가입
     * - 사용자의 계정을 생성한다
     */
    @Test
    public void testCreateUser() throws Exception {
        /*
         * # 전제조건
         * - 생성하고자 하는 아이디로 기생성된 계정이 없어야 한다
         * 
         * # 종료조건
         * - 생성하고자 하는 아이디로 기생성된 계정이 없어야 한다.
         * - 회원가입 완료 메시지가 노출 되어야 한다.
         * - 로그인 페이지에서 회원가입한 아이디와 패스워드로 로그인 되어야 한다.
         */

        //  회원 가입	T-02-0001	필수항목 확인	가입하기 버튼 클릭 시, 필수 항목이 비어 있는 경우 오류사항 안내를 출력한다.
        //  회원 가입	T-02-0002	중복항목 확인	가입하기 버튼 클릭 시, 기존 유저와 겹쳐지는 항목(비밀번호 제외)이 있을 경우 오류사항 안내를 출력한다.
        //  회원 가입	T-02-0003	회원 가입 성공	회원가입이 완료 되면 안내창과 함께 로그인 페이지로 이동한다.
         
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
            .userId("test")
            .nickname("test")
            .email("test@test.com")
            .userPw("test@test.com")
            .build());

        assertNotNull(respDto5);
        assertEquals(respDto5.isSuccess(), true);
        assertEquals(respDto5.getMsg(), "");
    }

    @Test
    public void testCreateVerifyCode() throws Exception {
        String verifyCode = model.createVerifyCode("test@test.com");
        assertNotNull(verifyCode);
        assertEquals(verifyCode.length() == 6, true);
        assertEquals(verifyCode.chars().allMatch(Character::isDigit), true);
    }

    @Test
    public void testSendVerifyEmail() throws Exception {
        SendMailFormDto reqDto = SendMailFormDto.builder()
            .mailTo("test@test.com")
            .build();

        StatusResponseDto respDto = model.sendVerifyEmail(reqDto);
        assertNotNull(respDto);
        assertEquals(respDto.isSuccess(), true);
        assertEquals(respDto.getMsg(), "");
    }

    /**
     * 아이디 찾기
     * - 사용자의 아이디를 조회한다
     */
    @Test
    public void testFindUserId() throws Exception {
        /*
         * # 전제조건
         * - 회원가입시 입력한 이메일을 알고 있어야 한다
         * 
         * # 종료조건
         * - 인증코드 안내 메시지가 노출 되어야 한다
         * - 인증코드로 아이디 조회가 가능하여야 한다
         */

        // 아이디 찾기	T-03-0001	코드 호출	인증코드 전송 클릭 시 임의의 코드가 alert창으로 출력 됨.	
        // 아이디 찾기	T-03-0002	아이디 찾기 실패	임의의 코드와 입력 된 코드가 동일하지 않다면 이를 안내하는 alert창이 출력 됨.	
        // 아이디 찾기	T-03-0003	아이디 찾기 성공	임의의 코드와 입력 된 코드가 동일하다면 아이디를 알려주는 페이지로 이동.	

        FindAccountDto reqDto = FindAccountDto.builder()
            .findMode("findId")
            .email("test@test.com")
            .verifyCode("000000")
            .build();

        StatusResponseDto respDto = model.findAccount(reqDto);
        assertTrue(respDto.isSuccess());
        assertTrue(respDto.getMsg().length() > 0);

        // TOOD: 아이디 조회 요청

    }


    /**
     * 비밀번호 찾기
     * - 사용자의 비밀번호를 조회한다
     */
    @Test
    public void testFindUserPw() throws Exception {
        /*
         * # 전제조건
         * - 회원가입시 입력한 아이디와 이메일을 알고 있어야 한다
         * 
         * # 종료조건
         * - 인증코드 안내 메시지가 노출 되어야 한다
         * - 인증코드로 비밀번호를 변경할 수 있어야 한다
         */

        // 비밀번호 찾기	T-04-0001	코드 호출	인증코드 전송 클릭 시 임의의 코드가 alert창으로 출력 됨.	
        // 비밀번호 찾기	T-04-0002	비밀번호 찾기 실패	임의의 코드와 입력 된 코드가 동일하지 않다면 이를 안내하는 alert창이 출력 됨.	
        // 비밀번호 찾기	T-04-0003	비밀번호 찾기 성공	임의의 코드와 입력 된 코드가 동일하다면 비밀번호를 변경하는 페이지로 이동.	
        // 비밀번호 변경	T-04-0004	비밀번호 변경 실패	입력 된 비밀번호와 비밀번호 확인이 동일하지 않다면 이를 안내하는 alert창이 출력 됨.
        // 비밀번호 변경	T-04-0005	비밀번호 변경 성공	입력 된 비밀번호와 비밀번호 확인이 동일하다면 비밀번호 변경 완료 안내와 함께 로그인 페이지로 이동한다.
        // 메인 - 설정	T-05-0009	비밀번호 변경 이동	메인 설정 창에서 비밀번호 변경 클릭 시, 비밀번호 변경페이지(T-04-0004)로 이동.	        
        

        FindAccountDto reqDto = FindAccountDto.builder()
            .findMode("findPw")
            .userId("test")
            .email("test@test.com")
            .verifyCode("000000")
            .build();

        StatusResponseDto respDto = model.findAccount(reqDto);
        assertTrue(respDto.isSuccess());
        assertTrue(respDto.getMsg().length() > 0);

        // TODO: 비밀번호 재설정 요청

    }

    /**
     * 게임 전적 조회
     * - 사용자의 게임 전적을 조회한다
     */
    @Test
    public void testViewGameRank() throws Exception {
        /*
         * # 전제조건
         * - 조회하고자 하는 아이디를 알고 있거나 랭킹 5위 내의 정보(닉네임, 프로필, 전적)를 조회할 수 있어야 한다
         * 
         * # 종료조건
         * - 게임 전적이 조회된다
         */

        //  메인	T-05-0002	유저 정보 출력	T-01-0001, 로그인에 성공하면 메인 페이지에 접속 되면서 해당 유저의 정보가 메인 상단에 출력 된다. (유저 닉네임, 승률, 프로필 이미지)        
        //  메인	T-05-0003	랭킹 정보 출력	유저들의 승률을 비교하여 상위 1~5위의 유저가 메인 우측 하단에 출력 된다.       
    }

    /**
     * 게임 전적 변경
     * - 사용자의 게임 전적을 변경한다
     */
    @Test
    public void testChangeGameRank() throws Exception {
        /*
         * # 전제조건
         * - 아이디에 매핑된 전적을 변경할 수 있어야 한다
         * 
         * # 종료조건
         * - 게임 전적이 변경되야 한다
         */

        // 메인 - 설정	T-05-0008	전적 리셋 하기	메인 설정 창에서 전적 리셋 클릭 시, 유저의 전적이 0승 0패 0무로 초기화 된다.        
    }

    /**
     * 유저 정보 변경
     * - 사용자의 정보를 변경한다
     */
    @Test
    public void testChangeUserInfo() throws Exception {
        /*
         * # 전제조건
         * - 세션에 저장된 아이디에 매핑된 계정이 있어야 한다
         * 
         * # 종료조건
         * - 계정 정보가 변경되어야 한다
         */

        // 메인	T-05-0006	유저 프로필 사진 변경	사진 작성 버튼을 클릭하고, 이미지 파일을 업로드하면 유저의 프로필 사진이 변경된다.
        // 메인	T-05-0007	유저 닉네임 변경	유저 닉네임 옆의 작성버튼을 클릭하면 닉네임을 변경 할 수 있는 창이 출력. 바뀌는 닉네임과 중복되는 닉네임이 있다면alert 창으로 알림.
        
    }

    /**
     * 유저 정보 삭제
     * - 사용자의 정보를 삭제한다
     */
    @Test
    public void testDeleteUserInfo() throws Exception {
        /*
         * # 전제조건
         * - 세션에 저장된 아이디에 매핑된 계정이 있어야 한다
         * 
         * # 종료조건
         * - 계정 정보가 데이터베이스에서 삭제되어야 한다
         */

        //  메인 - 설정	T-05-0011	회원탈퇴	메인 설정 창에서 회원탈퇴 클릭 시, 회원 정보가 삭제 되며 게임 입장 페이지로 이동한다.	
        
    }


    /**
     * 게임 생성
     * - 게임 룸을 생성한다
     */
    @Test
    public void testCreateGameRoom() throws Exception {
        /*
         * # 전제조건
         * - 로그인이 되어 있다
         * 
         * # 종료조건
         * - 게임 룸이 생성 되어야 한다
         */

        //  메인 – 게임 방 접속	T-05-0013	게임 방 생성	게임 방 생성 버튼 클릭 시 게임 방 이름을 입력 할 수 있는 창이 출력 되며, 이름 입력 후 생성 버튼을 클릭하면 게임방이 생성 된다.	

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
     * - 게임 룸에 참가한다
     */
    @Test
    public void testJoinGameRoom() throws Exception {
        /*
         * # 전제조건
         * - 참가할 게임 룸이 생성되어 있다
         * 
         * # 종료조건
         * - 게임 룸에 세션의 아이디가 등록되고 참가하고자 하는
         * 게임룸의 정보가 매핑된 게임 진행 페이지로 이동되어야 한다
         */


        //  메인 – 게임 방 접속	T-05-0014	기존게임 접속	기존 게임 중 게임대기 상태인 방의 JOIN GAME 버튼을 클릭하면 해당 게임 방의 후공(X)으로 접속 된다.
        // 게임진행	T-06-0001	게임 대기	게임 방 생성 후, 상대방 PC의 참여 전까지 게임 방에서 대기상태로 접속된다.        
        // 게임진행	T-06-0002	게임 접속	기존 게임에 입장 하면 해당 게임 방의 후공(X)으로 접속되며, 게임이 시작 된다.        
    }

    /**
     * 게임 진행
     * - 게임 룸의 게임 정보를 업데이트
     */
    @Test
    public void testUpdateGameRoom() throws Exception {
        /*
         * # 전제조건
         * - 진행중인 게임 룸 정보가 매핑된 게임 진행 페이지에
         * 접속되어 있으며 세션에 로그인된 사용자의 차례이다
         * 
         * # 종료조건
         * - 상대방 사용자의 차례가 되거나 게임의 결과가 확정된다
         */

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

    }

    /**
     * 게임 정보 조회
     * - 게임 정보를 조회한다
     */
    @Test
    public void testViewGameRoom() throws Exception {
        /*
         * # 전제조건
         * - 진행중인 게임 룸 정보가 매핑된 게임 진행 페이지에 접속되어 있다
         * 
         * # 종료조건
         * - 게임 정보를 반영한다
         */

        // 게임진행	T-06-0008	퇴장 후 유저, 게임 방 정보 갱신	유저가 게임을 끝마치고 게임방에서 퇴장하면 유저의 승률정보, 랭킹정보가 갱신 된다.	        
    }

    /**
     * 게임 리스트 조회
     * - 게임 룸 리스트를 조회한다
     */
    @Test
    public void testListGameRoom() throws Exception {
        /*
         * # 전제조건
         * - 게임 리스트 페이지에 접속해 있다
         * 
         * # 종료조건
         * - 게임 리스트를 반영한다
         */

        //  메인	T-05-0004	게임 방 리스트 출력	가장 최근에 만들어진 방부터 순서대로 게임방의 리스트가 메인 좌측 하단에 출력 된다.	
        //  메인	T-05-0005	게임 방 리스트 페이징	최근에 만들어진 방을 3개씩 묶어서 출력하며, 보이지 않는 방들은 페이징 처리한다.	
        // 메인 – 게임 방 접속	T-05-0012	게임 방 리스트 새로고침	게임 방 리스트에서 새로고침 클릭 시, 게임 방 리스트 정보가 갱신 된다.         

        // List<GameRoomDto> listGame = null;
        
        
    }
    


}
