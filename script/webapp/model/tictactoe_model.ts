import TicTacToeRepository from '../repos/tictactoe_repos'
import MessageRepository from '../repos/message_repos'
import { LoginRequestDto } from '../typedef/login_dto'
import { FindAccountDto, FindApplyDto, SignUpFormDto, UserInfoDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { SendMailFormDto } from '../typedef/message_dto'
import CommonUtil from '../util/common'
import { CreateGameDto, GameRoomDto, GameUpdateDto, JoinGameDto } from '../typedef/game_dto'

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
    async login(request: LoginRequestDto): Promise<StatusResponseDto | null> {
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

    /**
     * 아이디 찾기
     * @param request 아이디 찾기 요청
     * @return 아이디 찾기 응답
     */
    async findAccount(request: FindAccountDto): Promise<StatusResponseDto | null> {
        return await this.tttRepos?.findAccount(request) ?? null
    }

    /**
     * 계정정보 찾기
     * @param request 계정정보 찾기 요청
     * @returns 계정정보 찾기 응답
     */
    async findApply(request: FindApplyDto): Promise<StatusResponseDto | null> {
        return await this.tttRepos?.findApply(request) ?? null
    }

    /**
     * 게임 전적 목록
     * @returns 게임 전적 목록
     */
    async listGameRank(): Promise<UserInfoDto[] | null> {
        return await this.tttRepos?.listGameRank() ?? null
    }

    /**
     * 게임 생성
     * @param request 게임 생성 요청
     * @returns 게임 생성 응답
     */
    async createGame(request: CreateGameDto): Promise<StatusResponseDto | null> {
        return await this.tttRepos?.createGame(request) ?? null
    }

    /**
     * 게임 참가
     * @param request 게임 참가 요청
     * @returns 게임 참가 응답
     */
    async joinGame(request: JoinGameDto): Promise<StatusResponseDto | null> {
        return await this.tttRepos?.joinGame(request) ?? null
    }

    /**
     * 게임 진행
     * @param request 게임 진행 요청
     * @returns 게임 진행 응답
     */
    async updateGame(request: GameUpdateDto): Promise<StatusResponseDto | null> {
        return await this.tttRepos?.updateGame(request) ?? null
    }

    /**
     * 게임 정보 조회
     * @param gameId 게임방 아이디
     * @returns 게임방 정보
     */
    async getGameRoom(gameId: number): Promise<GameRoomDto | null> {
        return await this.tttRepos?.getGameRoom(gameId) ?? null
    }

    /**
     * 게임 리스트 조회
     * @returns 게임 리스트
     */
    async listGameRoom(): Promise<GameRoomDto[] | null> {
        return await this.tttRepos?.listGameRoom() ?? null
    }
}
