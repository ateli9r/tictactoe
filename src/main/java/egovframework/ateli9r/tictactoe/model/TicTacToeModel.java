package egovframework.ateli9r.tictactoe.model;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.ateli9r.tictactoe.repos.MessageRepository;
import egovframework.ateli9r.tictactoe.repos.TicTacToeRepository;
import egovframework.ateli9r.tictactoe.typedef.domain.GameRoomRecord;
import egovframework.ateli9r.tictactoe.typedef.domain.UserInfoRecord;
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
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

/**
 * 틱택토 모델
 */
@Service("ticTacToeModel")
public class TicTacToeModel extends EgovAbstractServiceImpl {
    @Resource(name = "ticTacToeRepository")
    private final TicTacToeRepository ticTacToeRepository;

    @Resource(name = "messageRepository")
    private final MessageRepository messageRepository;

    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService egovIdGnrService;


    /**
     * 아이디 찾기 토큰 맵
     */
    private Map<String, String> findIdTokenMap;

    /**
     * 패스워드 찾기 토큰 맵
     */
    private Map<String, String> findPwTokenMap;



	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

    public TicTacToeModel(TicTacToeRepository ticTacToeRepository, MessageRepository messageRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
        this.messageRepository = messageRepository;

        this.findIdTokenMap = new HashMap<>();
        this.findPwTokenMap = new HashMap<>();
    }

    /**
     * 로그인
     * @param request 로그인 요청
     * @return 로그인 응답
     */
    public StatusResponseDto login(LoginRequestDto request) throws Exception {
        LOGGER.debug(String.format("id:%s / pw:%s", request.getUserId(), this.hash(request.getUserPw())));
        
        boolean success = this.ticTacToeRepository.login(request.getUserId(), this.hash(request.getUserPw()));
        String msg = (!success) ? "로그인 실패" : "";
        return StatusResponseDto.builder().success(success).msg(msg).build();
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
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("아이디를 입력해 주세요.")
                .build();
        } else if (request.getNickname() == null || request.getNickname().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("닉네임을 입력해 주세요.")
                .build();
        } else if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("이메일을 입력해 주세요.")
                .build();
        } else if (request.getUserPw() == null || request.getUserPw().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("패스워드를 입력해 주세요.")
                .build();
        }
        // TODO: 이미 가입되어 있는지 검사
        if (request.getUserId().equals("exists_user")) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("이미 가입되어 있는 아이디입니다.")
                .build();
        }

        // 패스워드를 SHA256 hash해서 dto 다시 생성
        SignUpFormDto formDto = SignUpFormDto.builder()
            .userId(request.getUserId())
            .userPw(this.hash(request.getUserPw()))
            .nickname(request.getNickname())
            .email(request.getEmail())
            .build();

        if (this.ticTacToeRepository.signUp(formDto) > 0) {
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

    /**
     * 인증코드 생성
     * @return
     */
    public String createVerifyCode(String mailTo) {
        // TODO: create verify code
        return "000000";
    }

    /**
     * 인증 이메일 발송
     * @param request 이메일 발송 요청
     * @return 이메일 발송 결과
     */
    public StatusResponseDto sendVerifyEmail(SendMailFormDto request) {
        if (request.getMailTo() == null || request.getMailTo().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("수신자를 입력해 주세요.")
                .build();
        }

        SendMailFormDto mailForm = SendMailFormDto.builder()
            .mailTo(request.getMailTo())
            .title("Verify Code")
            .content(String.format("code: %s", this.createVerifyCode(request.getMailTo())))
            .build();

        if (this.messageRepository.sendVerifyEmail(mailForm)) {
            return StatusResponseDto.builder()
            .success(true)
            .msg("")
            .build();
        } else {
            return StatusResponseDto.builder()
            .success(false)
            .msg("이메일 발송중 오류가 발생했습니다.")
            .build();
        }
    }

    /**
     * 계정정보 찾기
     * @param request
     * @return
     */
    public StatusResponseDto findAccount(FindAccountDto request) {
        if (request.getFindMode() == null || request.getFindMode().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("계정찾기 구분이 지정되지 않았습니다.")
                .build();
        } else if (request.getFindMode().equals("findPw")) {
            if (request.getUserId() == null || request.getUserId().isEmpty()) {
                return StatusResponseDto.builder()
                    .success(false)
                    .msg("아이디를 입력 해주세요.")
                    .build();
            }
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("이메일을 입력 해주세요.")
                .build();
        } else if (request.getVerifyCode() == null || request.getVerifyCode().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("인증번호를 입력 해주세요.")
                .build();
        }

        // TODO: 작업 토큰 생성하기 (UUID)
        String accessToken = "accessToken";

        if (request.getFindMode().equals("findId")) {
            findIdTokenMap.put(request.getEmail(), accessToken);
            return StatusResponseDto.builder()
                .success(true)
                .msg(String.format("token: %s", accessToken))
                .build();
        }
        if (request.getFindMode().equals("findPw")) {
            findPwTokenMap.put(request.getEmail(), accessToken);
            return StatusResponseDto.builder()
                .success(true)
                .msg(String.format("token: %s", accessToken))
                .build();
        }
        return StatusResponseDto.builder()
            .success(false)
            .msg("잘못된 요청 입니다.")
            .build();
    }

    /**
     * 게임방 만들기
     * @param request 게임방 생성 요청
     * @return 게인방 생성 응답
     */
    public StatusResponseDto createGame(CreateGameDto request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("제목을 입력 하세요.")
                .build();
        } else if (request.getOwnerId() == null || request.getOwnerId().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("방장 아이디가 없습니다.")
                .build();
        }
        if (this.ticTacToeRepository.createGame(request) > 0) {
            return StatusResponseDto.builder()
                .success(true)
                .msg("")
                .build();
        } else {
            return StatusResponseDto.builder()
                .success(false)
                .msg("게임방을 만드는 중 오류가 발생했습니다.")
                .build();
        }
    }

    /**
     * 게임방 참여
     * @param request 게임방 참여 요청
     * @return 게임방 참여 응답
     */
    public StatusResponseDto joinGame(JoinGameDto request) {
        if (this.ticTacToeRepository.joinGame(request) > 0) {
            return StatusResponseDto.builder()
                .success(true)
                .msg("")
                .build();
        } else {
            return StatusResponseDto.builder()
                .msg("게임방 참여중 오류가 발생했습니다.")
                .build();
        }
    }

    /**
     * 게임방 목록
     * @return 게임방 목록
     */
    public List<GameRoomDto> listGameRoom() throws Exception {
        List<GameRoomDto> ret = new ArrayList<GameRoomDto>();
        List<GameRoomRecord> listGame = this.ticTacToeRepository.listGameRoom();
        
        for (GameRoomRecord item : listGame) {
            ret.add(item.toDto());
        }
        return ret;
    }

    /**
     * 게임방 조회
     * @param gameId 게임방 아이디
     * @return
     */
    public GameRoomDto getGameRoom(int gameId) throws Exception {
        return this.ticTacToeRepository.getGameRoom(gameId).toDto();
    }

    /**
     * 게임 전적 목록
     * @return 게임 전적 목록
     */
    public List<UserInfoDto> listGameRank() throws Exception {
        List<UserInfoDto> ret = new ArrayList<>();
        for (UserInfoRecord userInfo : this.ticTacToeRepository.listGameRank()) {
            ret.add(userInfo.toDto());
        }
        return ret;
    }

    /**
     * 게임 진행
     * @param gameUpdateDto 게임 진행 요청
     * @return 게임 진행 응답
     */
    public StatusResponseDto updateGame(GameUpdateDto gameUpdateDto) {
        GameRoomRecord gameRoom = this.ticTacToeRepository.getGameRoom(gameUpdateDto.getGameId());

        /*
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
        */

        // int affected = this.ticTacToeRepository.updateGame()
        return null;
    }

    /**
     * 계정찾기 응답
     * @param applyDto
     * @return
     */
    public StatusResponseDto findApply(FindApplyDto applyDto) {
        if (applyDto.getFindMode().equals("findId")) {
            if (this.findIdTokenMap.containsKey(applyDto.getEmail())) {
                if (this.findIdTokenMap.get(applyDto.getEmail()).equals(applyDto.getToken())) {
                    return StatusResponseDto.builder()
                        .success(true)
                        .msg("successful_find_id")
                        .build();
                }
                this.findIdTokenMap.remove(applyDto.getEmail());
            }
        } else if (applyDto.getFindMode().equals("findPw")) {
            if (this.findPwTokenMap.containsKey(applyDto.getEmail())) {
                if (this.findPwTokenMap.get(applyDto.getEmail()).equals(applyDto.getToken())) {
                    // TODO: 사용자 패스워드 변경
                    if (true) {
                        return StatusResponseDto.builder()
                        .success(true)
                        .msg("")
                        .build();
                    }
                }
                this.findPwTokenMap.remove(applyDto.getEmail());
            }
        }
        return StatusResponseDto.builder()
            .success(false)
            .msg("잘못된 요청 입니다.")
            .build();
    }
}
