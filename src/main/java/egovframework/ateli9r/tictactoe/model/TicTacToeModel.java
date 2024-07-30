package egovframework.ateli9r.tictactoe.model;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
     * 인증코드 맵
     */
    private Map<String, String> verifyCodeMap;

    /**
     * 아이디 찾기 토큰 맵
     */
    private Map<String, String> findIdTokenMap;

    /**
     * 패스워드 찾기 토큰 맵
     */
    private Map<String, String> findPwTokenMap;

    /**
     * 회원가입 찾기 토큰 맵
     */
    private Map<String, String> signUpTokenMap;


	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

    public TicTacToeModel(TicTacToeRepository ticTacToeRepository, MessageRepository messageRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
        this.messageRepository = messageRepository;

        this.verifyCodeMap = new HashMap<>();
        this.findIdTokenMap = new HashMap<>();
        this.findPwTokenMap = new HashMap<>();
        this.signUpTokenMap = new HashMap<>();
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
     * 숫자 랜덤코드 만들기
     * @param length 랜덤코드 길이
     * @return 랜덤코드
     */
    public String createRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 0부터 9까지의 랜덤 숫자 생성
            code.append(digit);
        }

        return code.toString();
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
        try {
            return this.ticTacToeRepository.getUserInfo(userId).toDto();
        } catch(Exception ex) { /** ignore */}
        return null;
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
        if (request.getUserId().equals("exists_user")) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("이미 가입되어 있는 아이디입니다.")
                .build();
        }
        if (request.getToken() == null || request.getToken().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("인증 정보가 없습니다.")
                .build();
        }

        // if (this.findPwTokenMap.containsKey(request.getEmail())) {

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
     * 인증코드 생성
     * @return
     */
    public String createVerifyCode() {
        return createRandomCode(6);
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

        // 인증코드 생성 및 등록
        String verifyCode = this.createVerifyCode();
        verifyCodeMap.put(request.getMailTo(), verifyCode);

        SendMailFormDto mailForm = SendMailFormDto.builder()
            .mailTo(request.getMailTo())
            .title("Verify Code")
            .content(String.format("code: %s", verifyCode))
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
     * @param request 계정정보 찾기 요청
     * @return 계정정보 찾기 응답
     */
    public StatusResponseDto findAccount(FindAccountDto request) {
        if (request.getFindMode() == null || request.getFindMode().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("계정찾기 구분이 지정되지 않았습니다.")
                .build();
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

        // 인증코드 검사
        if (!verifyCodeMap.containsKey(request.getEmail())) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("인증번호가 틀립니다.")
                .build();
        } else {
            String verifyCode = verifyCodeMap.get(request.getEmail());
            if (!verifyCode.equals(request.getVerifyCode())) {
                return StatusResponseDto.builder()
                    .success(false)
                    .msg("인증번호가 틀립니다.")
                    .build();
            }
        }
        this.verifyCodeMap.remove(request.getEmail());

        // 작업 토큰 생성
        String accessToken = UUID.randomUUID().toString();

        // 아이디 찾기
        if (request.getFindMode().equals("findId")) {
            findIdTokenMap.put(request.getEmail(), accessToken);
            return StatusResponseDto.builder()
                .success(true)
                .msg(String.format("accessToken=%s", accessToken))
                .build();
        }

        // 비밀번호 찾기
        if (request.getFindMode().equals("findPw")) {
            findPwTokenMap.put(request.getEmail(), accessToken);
            return StatusResponseDto.builder()
                .success(true)
                .msg(String.format("accessToken=%s", accessToken))
                .build();
        }

        // 회원가입
        if (request.getFindMode().equals("signUp")) {
            signUpTokenMap.put(request.getEmail(), accessToken);
            return StatusResponseDto.builder()
                .success(true)
                .msg(String.format("accessToken=%s", accessToken))
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
    public StatusResponseDto createGame(CreateGameDto request) throws Exception {
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
                .msg(String.format("gameId=%d", request.getGameId()))
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
    public StatusResponseDto joinGame(JoinGameDto request) throws Exception {
        if (request.getGameId() < 1) {
            return StatusResponseDto.builder()
                .msg("게임방 참여중 오류가 발생했습니다.")
                .build();
        }
        if (request.getChngrId() == null || request.getChngrId().isEmpty()) {
            return StatusResponseDto.builder()
                .msg("게임방 참여중 오류가 발생했습니다.")
                .build();
        }
        if (getUserInfo(request.getChngrId()) == null) {
            return StatusResponseDto.builder()
                .msg("게임방 참여중 오류가 발생했습니다.")
                .build();
        }

        GameRoomDto gameRoom = getGameRoom(request.getGameId());
        if (gameRoom == null || !gameRoom.getStatus().equals("W")) {
            return StatusResponseDto.builder()
                .msg("참여할 수 없는 게임입니다.")
                .build();
        }
        if (gameRoom.getOwnerId().equals(request.getChngrId())) {
            return StatusResponseDto.builder()
                .msg("참여자가 방장과 같은 아이디입니다.")
                .build();
        }

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
     * @return 게임방 정보
     */
    public GameRoomDto getGameRoom(int gameId) throws Exception {
        try {
            return this.ticTacToeRepository.getGameRoom(gameId).toDto();
        } catch(Exception ex) { /** ignore */}
        return null;
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
        // 게임방 아이디 검사
        if (gameUpdateDto.getGameId() == 0) {
            return StatusResponseDto.builder().msg("게임방 아이디가 없습니다.").build();
        }

        // 플레이어 아이디 검사
        if (gameUpdateDto.getPlayerId() == null || gameUpdateDto.getPlayerId().isEmpty()) {
            return StatusResponseDto.builder().msg("플레이어 아이디가 없습니다.").build();
        }

        // 메시지 검사
        if (gameUpdateDto.getMsg() == null || gameUpdateDto.getMsg().isEmpty()) {
            return StatusResponseDto.builder().msg("메시지가 없습니다.").build();
        }

        // 게임방 정보 가져오기
        GameRoomRecord gameRoom = this.ticTacToeRepository.getGameRoom(gameUpdateDto.getGameId());

        // 게임판 상태
        String rStatus = gameRoom.getStatus();
        String rBoard = gameRoom.getBoard();

        // 턴에 해당하는 플레이어 아이디
        boolean isCmdJoin = false;
        boolean isCmdSwitch = false;
        String turnPlayer = null;

        switch (rStatus) {
            case "IN": // JOIN
                turnPlayer = gameUpdateDto.getPlayerId();
                isCmdJoin = true;
                break;
            case "P1": // TURN-PLAYER1
                turnPlayer = gameRoom.getOwnerId();
                isCmdSwitch = true;
                break;
            case "P2": // TURN-PLAYER2
                turnPlayer = gameRoom.getChngrId();
                isCmdSwitch = true;
                break;
            case "W": // WAITING
                String msg1 = "게임이 시작되지 않았습니다.";
                return StatusResponseDto.builder().msg(msg1).build();
            default:
                String msg2 = "게임이 종료된 게임방 입니다.";
                return StatusResponseDto.builder().msg(msg2).build();
        }

        // 게임 진행중일때
        if (isCmdSwitch && turnPlayer != null) {
            if (!turnPlayer.equals(gameUpdateDto.getPlayerId())) {
                return StatusResponseDto.builder().msg("해당 플레이어 차례가 아닙니다.").build();
            }
        }

        // 메시지 형식 검사
        Optional<Integer> boardPos = Optional.empty();
        if (rStatus.equals("P1") || rStatus.equals("P2")) {
            // 게임 진행중일때 메시지의 첫글자는 'B'이고 나머지는 0~8사이의 숫자여야 함
            if (gameUpdateDto.getMsg().charAt(0) == 'B') {
                String boardPosStr = gameUpdateDto.getMsg().substring(1);
                try {
                    int pos = Integer.parseInt(boardPosStr);
                    boardPos = Optional.of(pos);
                } catch (Exception ex) { /* ignore */ }

                // boardPos가 제공되지 않으면 잘못된 메시지
                if (!boardPos.isPresent()) {
                    return StatusResponseDto.builder().msg("지원되지 않는 메시지 형식입니다.").build();
                }
            } else {
                return StatusResponseDto.builder().msg("지원되지 않는 메시지 형식입니다.").build();
            }
        } else {
            // 그 외에는 메시지는 공백이 아니면 지원하지 않음
            if (!gameUpdateDto.getMsg().equals("")) {
                return StatusResponseDto.builder().msg("지원되지 않는 메시지 형식입니다.").build();
            }
        }

        // 지정 위치 예외 검사
        if (boardPos.isPresent()) {
            if (boardPos.get() > 8) {
                return StatusResponseDto.builder().msg("게임판 범위를 벗어납니다.").build();
            }
            if (rBoard.charAt(boardPos.get()) != '.') {
                return StatusResponseDto.builder().msg("해당 위치에 놓을 수 없습니다.").build();
            }
        }

        // 게임방 업데이트 정보
        GameRoomRecord updateGameRoom = null;

        // 게임방 참여
        if (isCmdJoin) {
            updateGameRoom = GameRoomRecord.builder()
                .ownerId(gameRoom.getOwnerId())
                .chngrId(gameRoom.getChngrId())
                .status("P1")
                .board(gameRoom.getBoard())
                .build();
        }

        // 턴 전환
        if (isCmdSwitch && boardPos.isPresent()) {
            char mark = rStatus.equals("P2") ? 'X' : 'O';
            String status = rStatus.equals("P2") ? "P1" : "P2";
            StringBuffer sbBoard = new StringBuffer(rBoard);
            sbBoard.setCharAt(boardPos.get(), mark);

            updateGameRoom = GameRoomRecord.builder()
                .gameId(gameRoom.getGameId())
                .ownerId(gameRoom.getOwnerId())
                .chngrId(gameRoom.getChngrId())
                .status(status)
                .board(sbBoard.toString())
                .build();
        }

        // 게임판 업데이트
        if (updateGameRoom != null && this.ticTacToeRepository.updateGame(updateGameRoom) > 0) {
            return StatusResponseDto.builder()
                .success(true).msg("")
                .build();
        } else {
            return StatusResponseDto.builder().msg("게임판 업데이트 중 오류가 발생 했습니다.").build();
        }
    }

    /**
     * 계정찾기 적용
     * @param request 계정찾기 적용 요청
     * @return 계정찾기 적용 응답
     */
    public StatusResponseDto findApply(FindApplyDto request) throws Exception {
        if (request.getFindMode() == null || request.getFindMode().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("계정찾기 구분이 지정되지 않았습니다.")
                .build();
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("이메일을 입력 해주세요.")
                .build();
        }
        if (request.getToken() == null || request.getToken().isEmpty()) {
            return StatusResponseDto.builder()
                .success(false)
                .msg("인증 토큰이 없습니더.")
                .build();
        }

        if (request.getFindMode().equals("findId")) {
            if (this.findIdTokenMap.containsKey(request.getEmail())) {
                if (this.findIdTokenMap.get(request.getEmail()).equals(request.getToken())) {
                    // 토큰 제거
                    this.findIdTokenMap.remove(request.getEmail());

                    UserInfoDto userInfo = getUserInfoByEmail(request.getEmail());

                    return StatusResponseDto.builder()
                        .success(true)
                        .msg(String.format("userId=%s", userInfo.getUserId()))
                        .build();
                }
            }
        } else if (request.getFindMode().equals("findPw")) {
            if (this.findPwTokenMap.containsKey(request.getEmail())) {
                if (this.findPwTokenMap.get(request.getEmail()).equals(request.getToken())) {
                    // 토큰 제거
                    this.findPwTokenMap.remove(request.getEmail());

                    UserInfoDto userInfo = getUserInfoByEmail(request.getEmail());
                    if (request.getMessage() != null && request.getMessage().indexOf("password=") == 0) {
                        String userPw = request.getMessage().substring(9);
                        if (this.changePassword(userInfo.getUserId(), userPw)) {
                            return StatusResponseDto.builder()
                                .success(true)
                                .msg("")
                                .build();
                        }
                    }
                    return StatusResponseDto.builder()
                        .success(false)
                        .msg("패스워드 변경중 오류가 발생했습니다.")
                        .build();
                }
            }
        }
        return StatusResponseDto.builder()
            .success(false)
            .msg("잘못된 요청 입니다.")
            .build();
    }


    /**
     * 테스트 데이터 삭제
     * @param key 쿼리 구분
     */
    public void deleteTestData(String key) {
        this.ticTacToeRepository.deleteTestData(key);
    }

    /**
     * 테스트 데이터 수정
     * @param key 쿼리 구분
     */
    public void updateTestData(String key) {
        this.ticTacToeRepository.updateTestData(key);
    }

    /**
     * 테스트 데이터 등록
     * @param key 쿼리 구분
     */
    public void insertTestData(String key) {
        this.ticTacToeRepository.insertTestData(key);
    }

    /**
     * 테스트 상태 설정
     * @param key 기능 구분
     */
    public void setTestStatus(String key) {
        if (key.equals("testVerifyCode")) {
            verifyCodeMap.put("test@test.com", "000000");
        } else if (key.equals("signUp1")) {
            signUpTokenMap.put("test@test.com", "accessToken");
        } else if (key.equals("signUp2")) {
            signUpTokenMap.put("new_user@new_user.com", "accessToken");
        }
    }

    /**
     * 테스트 상태 가져오기
     * @param key 기능 구분
     * @return 상태 데이터
     */
    public Object getTestStatus(String key) {
        if (key.equals("testFindUserId > accessToken")) {
            return findIdTokenMap.get("test@test.com");
        } else if (key.equals("testFindUserPw > accessToken")) {
            return findPwTokenMap.get("test@test.com");
        }
        return null;
    }

    /**
     * 이메일로 사용자 정보 가져오기
     * @param email 이메일
     * @return 사용자 정보
     */
    public UserInfoDto getUserInfoByEmail(String email) throws Exception {
        try {
            return this.ticTacToeRepository.getUserInfoRecordByEmail(email).toDto();
        } catch(Exception ex) { /** ignore */}
        return null;
    }

    /**
     * 사용자 패스워드 변경
     * @param userId 사용자 아이디
     * @param password 사용자 패스워드
     * @return 성공 여부
     */
    public boolean changePassword(String userId, String userPw) {
        return this.ticTacToeRepository.changePassword(userId, this.hash(userPw)) == 1;
    }

}
