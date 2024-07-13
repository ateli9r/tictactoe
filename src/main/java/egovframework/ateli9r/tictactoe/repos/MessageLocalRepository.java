package egovframework.ateli9r.tictactoe.repos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.ateli9r.tictactoe.typedef.dto.SendMailFormDto;
import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

public class MessageLocalRepository implements MessageRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

    @Override
    public boolean sendVerifyEmail(SendMailFormDto request) {
        int chk = 0;
        if (!request.getMailTo().isEmpty()) chk++;
        if (!request.getTitle().isEmpty()) chk++;
        if (!request.getContent().isEmpty()) chk++;

        if (chk == 3) {
            LOGGER.info(request.getContent());
            return true;
        }
        return false;
    }
    
}
