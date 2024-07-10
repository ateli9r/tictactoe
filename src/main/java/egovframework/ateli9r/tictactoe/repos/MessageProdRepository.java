package egovframework.ateli9r.tictactoe.repos;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import egovframework.ateli9r.tictactoe.typedef.dto.SendMailFormDto;
// import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

@Component("messageRepository")
public class MessageProdRepository implements MessageRepository {

	// private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

    @Override
    public boolean sendVerifyEmail(SendMailFormDto request) {
        int chk = 0;
        if (!request.getMailTo().isEmpty()) chk++;
        if (!request.getTitle().isEmpty()) chk++;
        if (!request.getContent().isEmpty()) chk++;
        if (chk == 3) return false;

        // TODO: send email

        return false;
    }
    
}
