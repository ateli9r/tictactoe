package egovframework.ateli9r.tictactoe.app;


import egovframework.ateli9r.tictactoe.model.TicTacToeModel;
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

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;


@Controller
@RequestMapping(value = "tictactoe")
public class TicTacToeApp {
	@Resource(name = "ticTacToeModel")
	private TicTacToeModel ticTacToeModel;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);


	/**
	 * 웹페이지
	 * @return
	 */
	@RequestMapping(value = "app.do")
	public String app() {
		return "tictactoe/app";
    }


	/**
	 * 로그인
	 * @param reqDto 로그인 요청
	 * @return 로그인 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/login.do", method = RequestMethod.POST)
	public StatusResponseDto login(LoginRequestDto reqDto, HttpServletRequest request, HttpServletResponse response) {
		StatusResponseDto respDto = null;
		try {
			respDto = ticTacToeModel.login(reqDto);
			if (respDto.isSuccess()) {
				// 세션 속성 설정
				HttpSession session = request.getSession(true);
				session.setAttribute("userId", reqDto.getUserId());
	
				// 세션 쿠키 설정
				Cookie cookie = new Cookie("JSESSIONID", session.getId());
				cookie.setHttpOnly(true);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		} catch (Exception e) {
			respDto = StatusResponseDto.builder()
				.msg("요청중 오류가 발생했습니다.")
				.build();
			LOGGER.debug(e.getMessage());
		}
		return respDto;
	}

	/**
	 * 로그아웃
	 */
	@ResponseBody
	@RequestMapping(value = "/api/logout.do", method = RequestMethod.POST)
	public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 제거
        }

        // 세션 쿠키 삭제
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("JSESSIONID", null);
        cookie.setPath(request.getContextPath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 삭제
        response.addCookie(cookie);

		return true;
	}

	/**
	 * 사용자 정보
	 * @return 사용자 정보
	 */
	@ResponseBody
	@RequestMapping(value = "/api/userInfo.do", method = RequestMethod.POST)
	public UserInfoDto userInfo(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute("userId") != null) {
				String userId = (String) session.getAttribute("userId");
				UserInfoDto respDto = ticTacToeModel.getUserInfo(userId);
				return respDto;
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 인증메일 발송 요청
	 * @return 인증메일 발송 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/sendVerifyEmail.do", method = RequestMethod.POST)
	public StatusResponseDto sendVerifyEmail(SendMailFormDto request) {
		try {
			return this.ticTacToeModel.sendVerifyEmail(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 회원가입
	 * @param request 회원가입 요청
	 * @return 회원가입 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/signUp.do", method = RequestMethod.POST)
	public StatusResponseDto signUp(SignUpFormDto request) {
		try {
			return this.ticTacToeModel.signUp(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 계정찾기
	 * @param request 계정찾기 요청
	 * @return 계정창기 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/findAccount.do", method = RequestMethod.POST)
	public StatusResponseDto findAccount(FindAccountDto request) {
		try {
			return this.ticTacToeModel.findAccount(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 계정찾기 적용
	 * @param request 계정찾기 적용 요청
	 * @return 계정찾기 적용 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/findApply.do", method = RequestMethod.POST)
	public StatusResponseDto findApply(FindApplyDto request) {
		try {
			return this.ticTacToeModel.findApply(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
     * 게임 전적 목록
	 * @return 게임 전적 목록
	 */
	@ResponseBody
	@RequestMapping(value = "/api/listGameRank.do", method = RequestMethod.POST)
	public List<UserInfoDto> listGameRank() {
		try {
			return this.ticTacToeModel.listGameRank();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
     * 게임 생성
     * @param request 게임 생성 요청
     * @returns 게임 생성 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/createGame.do", method = RequestMethod.POST)
	public StatusResponseDto createGame(CreateGameDto request) {
		try {
			return this.ticTacToeModel.createGame(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
     * 게임 참가
     * @param request 게임 참가 요청
     * @returns 게임 참가 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/joinGame.do", method = RequestMethod.POST)
	public StatusResponseDto joinGame(JoinGameDto request) {
		try {
			return this.ticTacToeModel.joinGame(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
     * 게임 진행
     * @param request 게임 진행 요청
     * @returns 게임 진행 응답
	 */
	@ResponseBody
	@RequestMapping(value = "/api/updateGame.do", method = RequestMethod.POST)
	public StatusResponseDto updateGame(GameUpdateDto request) {
		try {
			return this.ticTacToeModel.updateGame(request);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
     * 게임 정보 조회
     * @param gameId 게임방 아이디
     * @returns 게임방 정보
	 */
	@ResponseBody
	@RequestMapping(value = "/api/getGameRoom.do", method = RequestMethod.POST)
	public GameRoomDto getGameRoom(int gameId) {
		try {
			return this.ticTacToeModel.getGameRoom(gameId);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
     * 게임 리스트 조회
     * @returns 게임 리스트
	 */
	@ResponseBody
	@RequestMapping(value = "/api/listGameRoom.do", method = RequestMethod.POST)
	public List<GameRoomDto> listGameRoom() {
		try {
			return this.ticTacToeModel.listGameRoom();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 테스트 상태 설정
	 * @param key 구분 키
	 */
	@ResponseBody
	@RequestMapping(value = "/api/testStatus.do", method = RequestMethod.POST)
	public StatusResponseDto testStatus(String key) {
		try {
			// 
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		return null;
	}

}