package egovframework.portal.admin;

import egovframework.portal.admin.domain.AdminUserData;
import egovframework.portal.admin.domain.AdminUserList;
import egovframework.portal.admin.domain.AdminUserSearch;
import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

//@Mapper("adminUserStore")
@Mapper("adminUserStore")
public interface AdminUserStore {
    /**
     * 로그인 체크
     * @param userId 아이디
     * @param userPw 패스워드
     * @return 레코드 수
     */
    int loginCheck(@Param("userId") String userId, @Param("userPw") String userPw);

    /**
     * 사용자 등록
     * @param formData 회원 정보
     * @return 레코드 수
     */
    int adminUserSave(AdminUserData formData);

    /**
     * 사용자 목록 조회
     * @param srchData 검색 정보
     * @return 사용자 목록
     */
    List<AdminUserList> adminUserSearch(AdminUserSearch srchData);
}
