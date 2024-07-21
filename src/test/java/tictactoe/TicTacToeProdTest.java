package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import egovframework.ateli9r.tictactoe.model.TicTacToeModel;
import egovframework.ateli9r.tictactoe.repos.MessageProdRepository;
import egovframework.ateli9r.tictactoe.repos.MessageRepository;
import egovframework.ateli9r.tictactoe.repos.TicTacToeRepository;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginRequestDto;
import egovframework.ateli9r.tictactoe.typedef.dto.StatusResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;


@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/resources/egovframework/spring/*.xml",
    "file:src/main/webapp/WEB-INF/config/egovframework/springmvc/dispatcher-servlet.xml"
})
public class TicTacToeProdTest implements TicTacToeTest {
    @Inject
    private WebApplicationContext wac;
   
    private TicTacToeModel model;


    /**
     * 테스트 객체 초기화
     */
    @BeforeEach
    public void setup() {
        TicTacToeRepository tttRepos = this.wac.getBean(TicTacToeRepository.class);
        MessageRepository msgRepos = new MessageProdRepository();
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
            .userId("user1").userPw("user1").build();
        
        assertEquals(req.getUserId(), "user1");
        assertEquals(model.hash(req.getUserPw()), "0a041b9462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90");

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
        // 
    }

    /**
     * 인증번호 생성
     */
    @Test
    @Override
    public void testCreateVerifyCode() throws Exception {
        // 
    }

    /**
     * 인증 이메일 발송
     */
    @Test
    @Override
    public void testSendVerifyEmail() throws Exception {
        // 
    }

    /**
     * 아이디 찾기
     */
    @Test
    @Override
    public void testFindUserId() throws Exception {
        // 
    }

    /**
     * 비밀번호 찾기
     */
    @Test
    @Override
    public void testFindUserPw() throws Exception {
        // 
    }

    /**
     * 게임 전적 목록
     */
    @Test
    @Override
    public void testListGameRank() throws Exception {
        // 
    }

    /**
     * 게임 생성
     */
    @Test
    @Override
    public void testCreateGameRoom() throws Exception {
        // 
    }

    /**
     * 게임 참가
     */
    @Test
    @Override
    public void testJoinGameRoom() throws Exception {
        // 
    }

    /**
     * 게임 진행
     */
    @Test
    @Override
    public void testUpdateGameRoom() throws Exception {
        // 
    }

    /**
     * 게임 정보 조회
     */
    @Test
    @Override
    public void testViewGameRoom() throws Exception {
        // 
    }

    /**
     * 게임 리스트 조회
     */
    @Test
    @Override
    public void testListGameRoom() throws Exception {
        // 
    }

}
