package egovframework.ateli9r.tictactoe.app;


import egovframework.ateli9r.tictactoe.model.TicTacToeModel;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginRequestDto;
import egovframework.ateli9r.tictactoe.typedef.dto.LoginResponseDto;
import egovframework.ateli9r.tictactoe.typedef.dto.UserInfoDto;
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;


@Controller
@CrossOrigin(originPatterns = "*")
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
	public LoginResponseDto login(LoginRequestDto reqDto, HttpServletRequest request) throws Exception {
		LoginResponseDto respDto = ticTacToeModel.login(reqDto);

		if (respDto.isSuccess()) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", reqDto.getUserId());
		}
		return respDto;
	}

	@ResponseBody
	@RequestMapping(value = "/api/userInfo.do", method = RequestMethod.POST)
	public UserInfoDto userInfo(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null) {
			String userId = (String) session.getAttribute("userId");
			UserInfoDto respDto = ticTacToeModel.getUserInfo(userId);
			return respDto;
		}
		return null;
	}

}