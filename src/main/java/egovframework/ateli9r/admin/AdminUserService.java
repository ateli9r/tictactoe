package egovframework.ateli9r.admin;

import egovframework.ateli9r.admin.dto.*;

/**
 * 관리자 회원 인터페이스
 */
public interface AdminUserService {
    /**
     * 로그인 체크
     * @param loginForm 로그인 요청 DTO
     * @return 로그인 응답 DTO
     */
    AdminUserLoginResponseDto loginCheck(AdminUserLoginRequestDto loginForm);

    /**
     * 사용자 등록
     * @param formData 회원 정보 DTO
     * @return 사용자 등록 응답 DTO
     */
    AdminUserFormResponseDto adminUserSave(AdminUserFormRequestDto formData);

    /**
     * 사용자 목록
     * @param searchData 검색 정보 DTO
     * @return 사용자 목록 응답 DTO
     */
    AdminUserSearchResponseDto adminUserSearch(AdminUserSearchRequestDto searchData);

}
