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
     * 게임 전적 조회
     */
    public void testViewGameRank() throws Exception;

    /**
     * 게임 전적 변경
     */
    public void testChangeGameRank() throws Exception;

    /**
     * 유저 정보 변경
     */
    public void testChangeUserInfo() throws Exception;

    /**
     * 유저 정보 삭제
     */
    public void testDeleteUserInfo() throws Exception;

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
}
