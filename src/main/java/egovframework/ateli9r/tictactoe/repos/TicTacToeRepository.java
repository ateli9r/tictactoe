package egovframework.ateli9r.tictactoe.repos;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.ateli9r.tictactoe.typedef.domain.UserInfoRecord;
import egovframework.ateli9r.tictactoe.typedef.dto.CreateGameDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SignUpFormDto;

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
}
