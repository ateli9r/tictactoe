import { StatusResponseDto } from "../typedef/cmmn_dto";
import { LoginRequestDto, LoginResponseDto } from "../typedef/login_dto"
import { SignUpFormDto, UserInfoDto } from "../typedef/user_dto"
import TicTacToeRepository from "./tictactoe_repos"
import CommonUtil from "../util/common";

export default class TicTacToeProdRepository implements TicTacToeRepository {
    /**
     * 로그인
     * @param request 로그인 요청
     * @returns 로그인 응답
     */
    async login(request: LoginRequestDto): Promise<LoginResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/login.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as LoginResponseDto
    }

    /**
     * 로그아웃
     * @returns 동작완료 여부
     */
    async logout(): Promise<boolean> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/logout.do',
        })
        return await response?.text() == 'true'
    }

    /**
     * 사용자 정보 가져오기
     * @returns 사용자 정보
     */
    async getUserInfo(): Promise<UserInfoDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/userInfo.do',
        })
        return await response?.json() as UserInfoDto
    }

    /**
     * 회원가입
     * @param request 회원가입 요청
     * @returns 회원가입 응답
     */
    async signUp(request: SignUpFormDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/signUp.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }
}