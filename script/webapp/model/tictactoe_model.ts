import TicTacToeRepository from '../repos/tictactoe_repos'
import MessageRepository from '../repos/message_repos'
import { LoginRequestDto, LoginResponseDto } from '../typedef/login_dto'
import { SignUpFormDto, UserInfoDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import CommonUtil from '../util/common'
import { SendMailFormDto } from '../typedef/message_dto'

/**
 * 틱택토 모델
 */
export default class TicTactoeModel {
    /**
     * 레파지토리
     */
    private tttRepos: TicTacToeRepository | null = null
    private msgRepos: MessageRepository | null = null

    /**
     * 생성자
     * @param repos 레파지토리
     */
    constructor(repos: TicTacToeRepository, msgRepos: MessageRepository) {
        this.tttRepos = repos
        this.msgRepos = msgRepos
    }
    
    /**
     * 로그인
     * @param request 로그인 요청
     * @returns 로그인 응답
     */
    async login(request: LoginRequestDto): Promise<LoginResponseDto | null> {
        return await this.tttRepos?.login(request) ?? null
    }

    /**
     * 로그아웃
     * @returns 정상 실행 여부
     */
    async logout() {
        return await this.tttRepos?.logout()
    }

    /**
     * 사용자 정보 가져오기
     * @returns 사용자 정보
     */
    async getUserInfo(): Promise<UserInfoDto | null> {
        return await this.tttRepos?.getUserInfo() ?? null
    }

    /**
     * 회원가입
     * @param request 회원가입 요청
     * @returns 회원가입 응답
     */
    async signUp(request: SignUpFormDto): Promise<StatusResponseDto | null> {
        return await this.tttRepos?.signUp(request) ?? null
    }


    /**
     * 인증메일 보내기
     * @param request 인증메일 요청
     * @returns 인증메일 응답
     */
    async sendVerifyEmail(request: SendMailFormDto): Promise<StatusResponseDto | null> {
        return await this.msgRepos?.sendVerifyEmail(request) ?? null
    }

}