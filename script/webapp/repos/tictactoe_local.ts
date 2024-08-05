import { StatusResponseDto } from "../typedef/cmmn_dto";
import { CreateGameDto, GameRoomDto, GameUpdateDto, JoinGameDto } from "../typedef/game_dto";
import { LoginRequestDto } from "../typedef/login_dto";
import { UserInfoDto, SignUpFormDto, FindAccountDto, FindApplyDto } from "../typedef/user_dto";
import TicTacToeRepository from "./tictactoe_repos";

export default class TicTacToeLocalRepository implements TicTacToeRepository {
    private isLoggedIn: boolean = false
    private dataGameRoom: Array<GameRoomDto> = []
    private dataUserInfo: Array<UserInfoDto> = []


    /**
     * 생성자
     */
    constructor() {
        // 게임방 정보
        this.dataGameRoom.push(...[
            {
                gameId: 1,
                ownerId: 'test',
                status: 'W',
                board: '.........',
            } as GameRoomDto,
            {
                gameId: 2,
                ownerId: 'test1',
                chngrId: 'test2',
                status: 'P1',
                board: 'O...O...X',
            },
        ])

        // 사용자 정보
        this.dataUserInfo.push(...[
            {
                userId: 'test1',
                nickname: '테스트',
                email: "test@test.com",
                total: 123,
                wins: 100,
                losses: 20,
                draws: 3,
            },
            {
                userId: 'user1',
                nickname: 'nickname1',
                email: "user1@user1.com",
                total: 78,
                wins: 41,
                losses: 24,
                draws: 13,
            },
        ])
    }

    /**
     * 로그인
     * @param request 로그인 요청
     * @returns 로그인 응답
     */
    async login(request: LoginRequestDto): Promise<StatusResponseDto | null> {
        if (request.userId == 'test1' && request.userPw == 'test1') {
            this.isLoggedIn = true
            return {success: true} as StatusResponseDto
        }
        return {success: false, msg: '로그인 실패'} as StatusResponseDto
    }

    /**
     * 로그아웃
     * @returns 처리완료 여부
     */
    async logout(): Promise<boolean> {
        this.isLoggedIn = false
        return true
    }

    /**
     * 사용자 정보 가져오기
     * @returns 사용자 정보
     */
    async getUserInfo(): Promise<UserInfoDto | null> {
        if (this.isLoggedIn) {
            const findUserId = 'test1'
            return this.dataUserInfo.find(user => user.userId == findUserId) || null
        }
        return null
    }

    /**
     * 회원가입
     * @param request 회원가입 요청
     * @returns 회원가입 응답
     */
    async signUp(request: SignUpFormDto): Promise<StatusResponseDto | null> {
        return { success: true, msg: '' } as StatusResponseDto
    }

    /**
     * 계정정보 찾기
     * @param request 계정정보 찾기 요청
     * @returns 계정정보 찾기 응답
     */
    async findAccount(request: FindAccountDto): Promise<StatusResponseDto | null> {
        return { success: true, msg: 'accessToken=accessToken' } as StatusResponseDto
    }

    /**
     * 계정찾기 적용
     * @param request 계정찾기 적용 요청
     * @returns 계정찾기 적용 응답
     */
    async findApply(request: FindApplyDto): Promise<StatusResponseDto | null> {
        if (request.findMode == 'findId') {
            return { success: true, msg: 'userId=userId' } as StatusResponseDto
        } else if (request.findMode == 'findPw') {
            return { success: true, msg: '' } as StatusResponseDto
        }
        return null
    }

    /**
     * 게임 전적 목록
     * @returns 게임 전적 목록
     */
    async listGameRank(): Promise<UserInfoDto[] | null> {
        return this.dataUserInfo
    }

    /**
     * 게임 생성
     * @param request 게임 생성 요청
     * @returns 게임 생성 응답
     */
    async createGame(request: CreateGameDto): Promise<StatusResponseDto | null> {
        console.log('createGame', request)

        const gameId = 1
        return { success: true, msg: `gameId=${gameId}` } as StatusResponseDto
    }

    /**
     * 게임 참가
     * @param request 게임 참가 요청
     * @returns 게임 참가 응답
     */
    async joinGame(request: JoinGameDto): Promise<StatusResponseDto | null> {
        let chk = 0
        if (request.gameId > 0) chk += 1
        if (request.chngrId != null && request.chngrId.length > 0) chk += 1

        if (chk == 2) {
            const game = await this.getGameRoom(request.gameId)
            if (game?.status == 'W') {
                game.chngrId = request.chngrId
                game.status = 'P1'
            }
            return { success: true, msg: '' } as StatusResponseDto
        }
        return null
    }

    /**
     * 게임 진행
     * @param request 게임 진행 요청
     * @returns 게임 진행 응답
     */
    async updateGame(request: GameUpdateDto): Promise<StatusResponseDto | null> {
        console.log('updateGame', request)

        return { success: true, msg: '' } as StatusResponseDto
    }

    /**
     * 게임 정보 조회
     * @param gameId 게임방 아이디
     * @returns 게임방 정보
     */
    async getGameRoom(gameId: number): Promise<GameRoomDto | null> {
        return this.dataGameRoom.find(room => room.gameId === gameId) || null
    }

    /**
     * 게임 리스트 조회
     * @returns 게임 리스트
     */
    async listGameRoom(): Promise<GameRoomDto[] | null> {
        return this.dataGameRoom
    }
}