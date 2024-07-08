package egovframework.ateli9r.tictactoe.model;

import java.security.MessageDigest;
import java.util.Formatter;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.ateli9r.tictactoe.repos.TicTacToeRepository;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginRequestDto;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.SignUpFormDto;
import egovframework.ateli9r.tictactoe.typedef.dto.StatusResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;

/**
 * 틱택토 모델
 */
@Service("ticTacToeModel")
public class TicTacToeModel extends EgovAbstractServiceImpl {
    @Resource(name = "ticTacToeRepository")
    private final TicTacToeRepository ticTacToeRepository;

    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService egovIdGnrService;

    public TicTacToeModel(TicTacToeRepository ticTacToeRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
    }

    /**
     * 로그인
     * @param request 로그인 요청
     * @return 로그인 응답
     */
    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        boolean success = this.ticTacToeRepository.login(request.getUserId(), this.hash(request.getUserPw()));
        String msg = (!success) ? "로그인 실패" : "";
        return LoginResponseDto.builder().success(success).msg(msg).build();
    }

    /**
     * 사용자 정보 가져오기
     * @param userId 사용자 아이디
     * @return 사용자 정보
     */
    public UserInfoDto getUserInfo(String userId) throws Exception {
        return this.ticTacToeRepository.getUserInfo(userId).toDto();
    }

    /**
     * 회원가입
     * @param request 회원가입 요청
     * @return 회원가입 응답
     */
    public StatusResponseDto signUp(SignUpFormDto request) throws Exception {
        if (request.getUserId().length() == 0) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("아이디를 입력해 주세요.")
                .build();
        } else if (request.getNickname().length() == 0) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("닉네임을 입력해 주세요.")
                .build();
        } else if (request.getEmail().length() == 0) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("이메일을 입력해 주세요.")
                .build();
        } else if (request.getPassword().length() == 0) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("패스워드를 입력해 주세요.")
                .build();
        }
        if (this.ticTacToeRepository.signUp(request) > 0) {
            return StatusResponseDto.builder()
            .success(true)
            .msg("")
            .build();
        } else {
            return StatusResponseDto.builder()
            .success(false)
            .msg("데이터 저장중 오류가 발생했습니다.")
            .build();
        }
    }

    /**
     * SHA256 해시
     * @param input 원본 텍스트
     * @return 해시된 텍스트
     */
    public String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));

            // bytes를 hexadecimal 문자열로 변환
            Formatter formatter = new Formatter();
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            String hexHash = formatter.toString();
            formatter.close();

            return hexHash;
        } catch (Exception ex) { /* ignore */}
        return null;
    }

}
