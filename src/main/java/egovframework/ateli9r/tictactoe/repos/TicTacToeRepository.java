package egovframework.ateli9r.tictactoe.repos;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.ateli9r.tictactoe.typedef.domain.UserInfoRecord;

import org.apache.ibatis.annotations.Param;

@Mapper("ticTacToeRepository")
public interface TicTacToeRepository {
    boolean login(@Param("userId") String userId, @Param("userPw") String userPw);
    UserInfoRecord getUserInfo(@Param("userId") String userId);    
}
