import { StatusResponseDto } from "../typedef/cmmn_dto";
import { SendMailFormDto } from "../typedef/message_dto";
import MessageRepository from "./message_repos";

export default class MessageProdRepository implements MessageRepository {
    async sendVerifyEmail(request: SendMailFormDto): Promise<StatusResponseDto | null> {
        const formBody = new URLSearchParams();
        formBody.append('mailTo', request.mailTo)
        formBody.append('title', request.title)
        formBody.append('content', request.content)

        try {
            const host = 'http://localhost:8080/tictactoe'
            const response = await fetch(`${host}/api/sendVerifyEmail.do`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                credentials: 'include',
                body: formBody.toString()
            })
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json() as StatusResponseDto
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error)
        }
        return null
    }
}
