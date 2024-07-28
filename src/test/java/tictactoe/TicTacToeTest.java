package tictactoe;

public interface TicTacToeTest {
    /**
     * 로그인 실패
     */
    public void testLoginFail() throws Exception;

    /**
     * 로그인 성공
     */
    public void testLoginSuccess() throws Exception;

    /**
     * 회원가입
     */
    public void testCreateUser() throws Exception;

    /**
     * 인증번호 생성
     */
    public void testCreateVerifyCode() throws Exception;

    /**
     * 인증 이메일 발송
     */
    public void testSendVerifyEmail() throws Exception;

    /**
     * 아이디 찾기
     */
    public void testFindUserId() throws Exception;

    /**
     * 비밀번호 찾기
     */
    public void testFindUserPw() throws Exception;

    /**
     * 게임 전적 목록
     */
    public void testListGameRank() throws Exception;

    /**
     * 게임 생성
     */
    public void testCreateGameRoom() throws Exception;

    /**
     * 게임 참가
     */
    public void testJoinGameRoom() throws Exception;

    /**
     * 게임 진행
     */
    public void testUpdateGameRoom() throws Exception;

    /**
     * 게임 정보 조회
     */
    public void testViewGameRoom() throws Exception;

    /**
     * 게임 리스트 조회
     */
    public void testListGameRoom() throws Exception;

    /**
     * 이메일로 사용자 정보 가져오기
     */
    public void testUserInfoByEmail() throws Exception;

    /**
     * 사용자 패스워드 변경
     */
    public void testChangePassword() throws Exception;
}
