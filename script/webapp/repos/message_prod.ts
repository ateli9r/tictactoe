import { StatusResponseDto } from "../typedef/cmmn_dto";
import { SendMailFormDto } from "../typedef/message_dto";
import MessageRepository from "./message_repos";
import CommonUtil from "../util/common";

export default class MessageProdRepository implements MessageRepository {
    async sendVerifyEmail(request: SendMailFormDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/sendVerifyEmail.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }
}
