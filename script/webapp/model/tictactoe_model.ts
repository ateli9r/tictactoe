import TicTacToeRepository from '../repos/tictactoe_repos'
import { LoginRequestDto, LoginResponseDto } from '../typedef/login_dto'
import { UserInfoDto } from '../typedef/user_dto'
import CommonUtil from '../util/common'

/**
 * 틱택토 모델
 */
export default class TicTactoeModel {
    /**
     * 레파지토리
     */
    private repos: TicTacToeRepository | null = null

    /**
     * 생성자
     * @param repos 레파지토리
     */
    constructor(repos: TicTacToeRepository) {
        this.repos = repos
    }
    
    /**
     * 로그인
     * @param request 로그인 요청
     * @returns 로그인 응답
     */
    async login(request: LoginRequestDto): Promise<LoginResponseDto | null> {
        return await this.repos?.login(request) ?? null
    }

    /**
     * 로그아웃
     * @returns 정상 실행 여부
     */
    async logout() {
        return await this.repos?.logout()
    }

    /**
     * 사용자 정보 가져오기
     * @returns 사용자 정보
     */
    async getUserInfo(): Promise<UserInfoDto | null> {
        return await this.repos?.getUserInfo() ?? null
    }

}