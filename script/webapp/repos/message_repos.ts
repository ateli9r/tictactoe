import { StatusResponseDto } from "../typedef/cmmn_dto";
import { SendMailFormDto } from "../typedef/message_dto";

export default interface MessageRepository {
    sendVerifyEmail(request: SendMailFormDto): Promise<StatusResponseDto | null>;
}
