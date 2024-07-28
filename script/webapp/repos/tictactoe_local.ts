import { StatusResponseDto } from "../typedef/cmmn_dto";
import { LoginRequestDto } from "../typedef/login_dto";
import { UserInfoDto, RankInfoDto, SignUpFormDto, FindAccountDto, FindApplyDto } from "../typedef/user_dto";
import TicTacToeRepository from "./tictactoe_repos";

export default class TicTacToeLocalRepository implements TicTacToeRepository {
    private isLoggedIn: boolean = false

    async login(request: LoginRequestDto): Promise<StatusResponseDto | null> {
        if (request.userId == 'test1' && request.userPw == 'test1') {
            this.isLoggedIn = true
            return {success: true} as StatusResponseDto
        }
        return {success: false} as StatusResponseDto
    }

    async logout(): Promise<boolean> {
        this.isLoggedIn = false
        return true
    }

    async getUserInfo(): Promise<UserInfoDto | null> {
        if (this.isLoggedIn) {
            return {
                userId: 'test1',
                nickname: '테스트',
                rank: {
                    rankNo: 123,
                    total: 123,
                    wins: 50,
                    losses: 50,
                    draws: 23,
                } as RankInfoDto
            } as UserInfoDto
        }
        return null
    }

    async signUp(request: SignUpFormDto): Promise<StatusResponseDto | null> {
        if (request.userId.length == 0) {
            return { success: false, msg: '아이디를 입력해 주세요.' } as StatusResponseDto
        } else if (request.nickname.length == 0) {
            return { success: false, msg: '닉네임을 입력해 주세요.' } as StatusResponseDto
        } else if (request.email.length == 0) {
            return { success: false, msg: '이메일을 입력해 주세요.' } as StatusResponseDto
        } else if (request.userPw.length == 0) {
            return { success: false, msg: '패스워드를 입력해 주세요.' } as StatusResponseDto
        }
        return { success: true, msg: '' } as StatusResponseDto
    }

    async findAccount(request: FindAccountDto): Promise<StatusResponseDto | null> {
        return { success: true, msg: 'accessToken' } as StatusResponseDto
    }

    async findApply(request: FindApplyDto): Promise<StatusResponseDto | null> {
        if (request.findMode == 'findId') {
            return { success: true, msg: 'userId' } as StatusResponseDto
        } else if (request.findMode == 'findPw') {
            return { success: true, msg: '' } as StatusResponseDto
        }
        return null
    }



    // #pw

    // StatusResponseDto respDto1 = model.findAccount(reqDto);
    // assertTrue(respDto1.isSuccess());
    // assertTrue(respDto1.getMsg().length() > 0); // accessToken

    // StatusResponseDto respDto2 = model.findApply(applyDto);
    // assertTrue(respDto2.isSuccess());
    // assertEquals(respDto2.getMsg(), "");

    


}