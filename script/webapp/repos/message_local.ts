import { StatusResponseDto } from "../typedef/cmmn_dto";
import { SendMailFormDto } from "../typedef/message_dto";
import MessageRepository from "./message_repos";

export default class MessageLocalRepository implements MessageRepository {
    async sendVerifyEmail(request: SendMailFormDto): Promise<StatusResponseDto | null> {
        if (request.mailTo.length == 0) {
            return { msg: '수신자를 입력해 주세요.' } as StatusResponseDto
        } else if (request.title.length == 0) {
            return { msg: '제목을 입력해 주세요.' } as StatusResponseDto
        } else if (request.content.length == 0) {
            return { msg: '내용을 입력해 주세요.' } as StatusResponseDto
        }
        return { success: true, msg: '' } as StatusResponseDto
    }
}
