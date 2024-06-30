package egovframework.portal.admin;

import egovframework.portal.admin.domain.AdminUserData;
import egovframework.portal.admin.domain.AdminUserList;
import egovframework.portal.admin.domain.AdminUserSearch;

import java.util.List;

public class AdminUserStoreLocal implements AdminUserStore {
    @Override
    public int loginCheck(String userId, String userPw) {
        return 0;
    }

    @Override
    public int adminUserSave(AdminUserData formData) {
        return 0;
    }

    @Override
    public List<AdminUserList> adminUserSearch(AdminUserSearch srchData) {
//        return List.of();
        return null;
    }
}
