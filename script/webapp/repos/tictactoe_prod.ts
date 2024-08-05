import { StatusResponseDto } from "../typedef/cmmn_dto";
import { LoginRequestDto } from "../typedef/login_dto"
import { FindAccountDto, FindApplyDto, SignUpFormDto, UserInfoDto } from "../typedef/user_dto"
import TicTacToeRepository from "./tictactoe_repos"
import CommonUtil from "../util/common";
import { CreateGameDto, GameRoomDto, GameUpdateDto, JoinGameDto } from "../typedef/game_dto";

export default class TicTacToeProdRepository implements TicTacToeRepository {
    /**
     * 로그인
     * @param request 로그인 요청
     * @returns 로그인 응답
     */
    async login(request: LoginRequestDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/login.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
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

    /**
     * 계정정보 찾기
     * @param request 계정정보 찾기 요청
     * @returns 계정정보 찾기 응답
     */
    async findAccount(request: FindAccountDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/findAccount.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }

    /**
     * 계정찾기 적용
     * @param request 계정찾기 적용 요청
     * @returns 계정찾기 적용 응답
     */
    async findApply(request: FindApplyDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/findApply.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }

    /**
     * 게임 전적 목록
     * @returns 게임 전적 목록
     */
    async listGameRank(): Promise<UserInfoDto[] | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/listGameRank.do',
        })
        return await response?.json() as UserInfoDto[]
    }

    /**
     * 게임 생성
     * @param request 게임 생성 요청
     * @returns 게임 생성 응답
     */
    async createGame(request: CreateGameDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/createGame.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }

    /**
     * 게임 참가
     * @param request 게임 참가 요청
     * @returns 게임 참가 응답
     */
    async joinGame(request: JoinGameDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/joinGame.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }

    /**
     * 게임 진행
     * @param request 게임 진행 요청
     * @returns 게임 진행 응답
     */
    async updateGame(request: GameUpdateDto): Promise<StatusResponseDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/updateGame.do',
            body: CommonUtil.toForm(request),
        })
        return await response?.json() as StatusResponseDto
    }

    /**
     * 게임 정보 조회
     * @param gameId 게임방 아이디
     * @returns 게임방 정보
     */
    async getGameRoom(gameId: number): Promise<GameRoomDto | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/getGameRoom.do',
            body: CommonUtil.toForm({gameId: gameId}),
        })
        return await response?.json() as GameRoomDto
    }

    /**
     * 게임 리스트 조회
     * @returns 게임 리스트
     */
    async listGameRoom(): Promise<GameRoomDto[] | null> {
        const response = await CommonUtil.request({
            method: 'POST',
            url: '/api/listGameRoom.do',
        })
        return await response?.json() as GameRoomDto[]
    }
}