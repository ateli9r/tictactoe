package egovframework.portal.admin;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Resource(name = "adminUserService")
	private AdminUserService adminUserService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);


	@RequestMapping(value = "/hello.do")
	@ResponseBody
	public String hello() throws Exception {
		return "Hello, World!";
	}

	@RequestMapping(value = "/home.do")
	public String home() throws Exception {
//		if (adminUserService != null) {
//			AdminUserLoginRequestDto reqDto = AdminUserLoginRequestDto
//					.builder().userId("test1").userPw("test1").build();
//			AdminUserLoginResponseDto respDto = adminUserService.loginCheck(reqDto);
//
//			if (respDto.isSuccess()) {
//
//			}
//		}
		LOGGER.info("home.do");
		return "admin/home";
	}


}
