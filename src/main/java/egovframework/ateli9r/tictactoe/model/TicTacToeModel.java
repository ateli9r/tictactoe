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
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;


@Service("ticTacToeModel")
public class TicTacToeModel extends EgovAbstractServiceImpl {
    @Resource(name = "ticTacToeRepository")
    private final TicTacToeRepository ticTacToeRepository;

    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService egovIdGnrService;

    public TicTacToeModel(TicTacToeRepository ticTacToeRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
    }

    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        boolean success = this.ticTacToeRepository.login(request.getUserId(), this.hash(request.getUserPw()));
        String msg = (!success) ? "로그인 실패" : "";
        return LoginResponseDto.builder().success(success).msg(msg).build();
    }

    public UserInfoDto getUserInfo(String userId) {
        return null;
    }

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
