package egovframework.ateli9r.tictactoe.app;


import egovframework.ateli9r.tictactoe.model.TicTacToeModel;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginRequestDto;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;


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


	@RequestMapping(value = "app.do")
	public String app() throws Exception {
		return "tictactoe/app";
    }

	@ResponseBody
	@RequestMapping(value = "/api/login.do", method = RequestMethod.POST)
	public LoginResponseDto login(LoginRequestDto reqDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginResponseDto respDto = ticTacToeModel.login(reqDto);

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
		return respDto;
	}

	@ResponseBody
	@RequestMapping(value = "/api/logout.do", method = RequestMethod.POST)
	public boolean logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

	@ResponseBody
	@RequestMapping(value = "/api/userInfo.do", method = RequestMethod.POST)
	public UserInfoDto userInfo(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null) {
			String userId = (String) session.getAttribute("userId");
			UserInfoDto respDto = ticTacToeModel.getUserInfo(userId);
			return respDto;
		}
		return null;
	}

	// sendVerifyEmail
	// checkVerifyNo
	// signUp

	// findIdRequest
	// findPwRequest


}