package admin;

import egovframework.ateli9r.admin.AdminUserService;
import egovframework.ateli9r.admin.AdminUserServiceImpl;
import egovframework.ateli9r.admin.AdminUserStore;
import egovframework.ateli9r.admin.AdminUserStoreLocal;
import egovframework.ateli9r.admin.dto.AdminUserLoginRequestDto;
import egovframework.ateli9r.admin.dto.AdminUserLoginResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * 관리자 회원 테스트
 */
public class AdminUserTest {
    private final AdminUserService adminUserService;

    public AdminUserTest() {
        AdminUserStore store = new AdminUserStoreLocal();
        adminUserService = new AdminUserServiceImpl(store);
    }

    // 로그인 테스트
    @Test
    public void testLoginCheck() {
        AdminUserLoginRequestDto reqDto = AdminUserLoginRequestDto.builder()
                .userId("login_fail").userPw("login_fail").build();

        assertEquals(reqDto.getUserId(), "login_fail");
        assertEquals(reqDto.getUserPw(), "sha-256_hashed_password");

        AdminUserLoginResponseDto respDto = adminUserService.loginCheck(reqDto);
        assertFalse(respDto.isSuccess());
        assertEquals(respDto.getMsg(), "로그인 실패");
    }

    // 회원가입 테스트

    // 검색 테스트

    // 상세 조회 테스트


    // 로그인 금지
    // 접속 금지
    // 접속 기록
    // 활동 기록
}