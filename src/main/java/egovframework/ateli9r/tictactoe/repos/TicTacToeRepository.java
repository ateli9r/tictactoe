package egovframework.ateli9r.tictactoe.repos;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.ateli9r.tictactoe.typedef.domain.GameRoomRecord;
import egovframework.ateli9r.tictactoe.typedef.domain.UserInfoRecord;
import egovframework.ateli9r.tictactoe.typedef.dto.CreateGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.JoinGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SignUpFormDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper("ticTacToeRepository")
public interface TicTacToeRepository {
    /**
     * 로그인
     * @param userId 사용자 아이디
     * @param userPw 사용자 패스워드
     * @return 인증 성공 여부
     */
    boolean login(@Param("userId") String userId, @Param("userPw") String userPw);

    /**
     * 사용자 정보 가져오기
     * @param userId 사용자 아이디
     * @return 사용자 정보
     */
    UserInfoRecord getUserInfo(@Param("userId") String userId);

    /**
     * 회원가입
     * @param request 회원가입 요청
     * @return 영향 레코드 수
     */
    int signUp(SignUpFormDto request);

    /**
     * 게임방 만들기
     * @param request 게임방 생성 요청
     * @return 영향 레코드 수
     */
    int createGame(CreateGameDto request);

    /**
     * 게임방 참여하기
     * @param request 게임방 참여 요청
     * @return 영향 레코드 수
     */
    int joinGame(JoinGameDto request);

    /**
     * 게임방 목록
     * @return 게임방 목록
     */
    List<GameRoomRecord> listGameRoom();

    /**
     * 게임방 정보
     * @param gameId 게임방 아이디
     * @return 게임방 정보
     */
    GameRoomRecord getGameRoom(int gameId);

    /**
     * 
     * @return
     */
    List<UserInfoRecord> listGameRank();

    /**
     * 게임 진행
     * @param updateGameRoom 게임방 정보
     * @return 영향 레코드 수
     */
    int updateGame(GameRoomRecord updateGameRoom);


    /**
     * 테스트 데이터 삭제
     * @param key 쿼리 구분
     * @return 영향 레코드 수
     */
    int deleteTestData(String key);

    /**
     * 테스트 데이터 수정
     * @param key 쿼리 구분
     * @return 영향 레코드 수
     */
    int updateTestData(String key);

    /**
     * 테스트 데이터 등록
     * @param key 쿼리 구분
     * @return 영향 레코드 수
     */
    int insertTestData(String key);

    /**
     * 이메일로 사용자 정보 가져오기
     * @param email 이메일
     * @return 사용자 정보
     */
    UserInfoRecord getUserInfoRecordByEmail(String email);

    /**
     * 사용자 패스워드 변경
     * @param userId 사용자 아이디
     * @param userPw 사용자 패스워드
     * @return 영향 레코드 수
     */
    int changePassword(@Param("userId") String userId, @Param("userPw") String userPw);
}
