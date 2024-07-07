import { LoginRequestDto, LoginResponseDto } from "../typedef/login_dto";
import { UserInfoDto, RankInfoDto } from "../typedef/user_dto";
import TicTacToeRepository from "./tictactoe_repos";

export default class TicTacToeLocalRepository implements TicTacToeRepository {
    private isLoggedIn: boolean = false

    async login(request: LoginRequestDto): Promise<LoginResponseDto | null> {
        if (request.userId == 'test1' && request.userPw == 'test1') {
            this.isLoggedIn = true
            return {success: true} as LoginResponseDto
        }
        return {success: false} as LoginResponseDto
    }

    async logout(): Promise<boolean> {
        this.isLoggedIn = false
        return true
    }

    async getUserInfo(): Promise<UserInfoDto | null> {
        if (this.isLoggedIn) {
            return {
                userId: 'test1',
                userNm: '테스트',
                rank: {
                    rankNo: 123,
                    total: 123,
                    win: 50,
                    lose: 50,
                    draw: 23,
                } as RankInfoDto
            } as UserInfoDto
        }
        return null
    }
}