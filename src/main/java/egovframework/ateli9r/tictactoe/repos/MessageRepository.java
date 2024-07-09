package egovframework.ateli9r.tictactoe.repos;

import egovframework.ateli9r.tictactoe.typedef.dto.SendMailFormDto;

public interface MessageRepository {

    boolean sendVerifyEmail(SendMailFormDto request);
    
}
