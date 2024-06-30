package egovframework.portal.admin;

// import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import egovframework.portal.admin.dto.*;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 관리자 회원 서비스
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends EgovAbstractServiceImpl implements AdminUserService {
    @Resource(name = "adminUserStore")
    private final AdminUserStore adminUserStore;

    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService egovIdGnrService;

    // private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);


    public AdminUserServiceImpl(AdminUserStore adminUserStore) {
        this.adminUserStore = adminUserStore;
    }

    @Override
    public AdminUserLoginResponseDto loginCheck(AdminUserLoginRequestDto loginForm) {
        if (adminUserStore == null) return null;

        boolean success = false;
        String msg = "";

        if (!loginForm.getUserId().isEmpty() && !loginForm.getUserPw().isEmpty()) {
            int check = adminUserStore.loginCheck(loginForm.getUserId(), loginForm.getUserPw());
            if (check == 1) {
                success = true;
            } else {
                msg = "로그인 실패";
            }
        } else {
            msg = "로그인 실패";
        }

        return AdminUserLoginResponseDto.builder()
                .success(success).msg(msg).build();
    }

    @Override
    public AdminUserFormResponseDto adminUserSave(AdminUserFormRequestDto formData) {
        return null;
    }

    @Override
    public AdminUserSearchResponseDto adminUserSearch(AdminUserSearchRequestDto searchData) {
        return null;
    }
}
