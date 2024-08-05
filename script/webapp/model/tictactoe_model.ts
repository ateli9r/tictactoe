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
        if (request.userId.length == 0) {
            return { success: false, msg: '아이디를 입력해 주세요.' } as StatusResponseDto
        } else if (request.nickname.length == 0) {
            return { success: false, msg: '닉네임을 입력해 주세요.' } as StatusResponseDto
        } else if (request.email.length == 0) {
            return { success: false, msg: '이메일을 입력해 주세요.' } as StatusResponseDto
        } else if (request.userPw.length == 0) {
            return { success: false, msg: '패스워드를 입력해 주세요.' } as StatusResponseDto
        }
        return await this.tttRepos?.signUp(request) ?? null
    }

    /**
     * 인증메일 보내기
     * @param request 인증메일 요청
     * @returns 인증메일 응답
     */
    async sendVerifyEmail(request: SendMailFormDto): Promise<StatusResponseDto | null> {
        if (request.mailTo == null || request.mailTo.length == 0) {
            return { success: false, msg: '수신자를 입력해 주세요.' } as StatusResponseDto
        }
        return await this.msgRepos?.sendVerifyEmail(request) ?? null
    }

    /**
     * 아이디 찾기
     * @param request 아이디 찾기 요청
     * @return 아이디 찾기 응답
     */
    async findAccount(request: FindAccountDto): Promise<StatusResponseDto | null> {
        if (request.findMode == null || request.findMode.length == 0) {
            return { success: false, msg: '계정찾기 구분이 지정되지 않았습니다.' } as StatusResponseDto
        }
        if (request.email == null || request.email.length == 0) {
            return { success: false, msg: '이메일을 입력 해주세요.' } as StatusResponseDto
        }
        if (request.verifyCode == null || request.verifyCode.length == 0) {
            return { success: false, msg: '인증번호를 입력 해주세요.' } as StatusResponseDto
        }
        return await this.tttRepos?.findAccount(request) ?? null
    }

    /**
     * 계정정보 찾기
     * @param request 계정정보 찾기 요청
     * @returns 계정정보 찾기 응답
     */
    async findApply(request: FindApplyDto): Promise<StatusResponseDto | null> {
        if (request.findMode == null || request.findMode.length == 0) {
            return { success: false, msg: '계정찾기 구분이 지정되지 않았습니다.' } as StatusResponseDto
        }
        if (request.email == null || request.email.length == 0) {
            return { success: false, msg: '이메일을 입력 해주세요.' } as StatusResponseDto
        }
        if (request.token == null || request.token.length == 0) {
            return { success: false, msg: '인증 토큰이 없습니더.' } as StatusResponseDto
        }
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
        if (request.title == null || request.title.length == 0) {
            return { success: false, msg: '제목을 입력 하세요.' } as StatusResponseDto
        }
        if (request.ownerId == null || request.ownerId.length == 0) {
            return { success: false, msg: '방장 아이디가 없습니다.' } as StatusResponseDto
        }
        return await this.tttRepos?.createGame(request) ?? null
    }

    /**
     * 게임 참가
     * @param request 게임 참가 요청
     * @returns 게임 참가 응답
     */
    async joinGame(request: JoinGameDto): Promise<StatusResponseDto | null> {
        if (request.gameId == null || request.gameId < 1) {
            return { success: false, msg: '게임방 아이디가 없습니다.' } as StatusResponseDto
        }
        if (request.chngrId == null || request.chngrId.length == 0) {
            return { success: false, msg: '참여자 아이디가 없습니다.' } as StatusResponseDto
        }
        return await this.tttRepos?.joinGame(request) ?? null
    }

    /**
     * 게임 진행
     * @param request 게임 진행 요청
     * @returns 게임 진행 응답
     */
    async updateGame(request: GameUpdateDto): Promise<StatusResponseDto | null> {
        if (request.gameId == null || request.gameId < 1) {
            return { success: false, msg: '게임방 아이디가 없습니다.' } as StatusResponseDto
        }
        if (request.playerId == null || request.playerId.length == 0) {
            return { success: false, msg: '플레이어 아이디가 없습니다.' } as StatusResponseDto
        }

        if (request.msg != null) {
            const cmdChar = request.msg[0]
            if (cmdChar == 'B') { // Board Update
                let posNum = -1
                try {
                    const pos = request.msg.substring(1)
                    posNum = parseInt(pos)
                } catch (error) { /** ignore */ }
    
                if (!(posNum >= 0 && posNum <= 8)) {
                    return { success: false, msg: '게임판 범위를 벗어납니다.' } as StatusResponseDto
                }
            }
        }

        if (request.msg == null || request.msg.length > 5) {
            return { success: false, msg: '지원되지 않는 메시지 형식입니다.' } as StatusResponseDto
        }

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


    /**
     * 이메일 형식 검사
     * @param email 이메일 주소
     * @returns 이메일 형식 여부
     */
    validateEmail(email: string): boolean {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/
        const domainPart = email.split('@')[1]
        
        if (!emailRegex.test(email)) {
            return false
        }
    
        if (domainPart) {
            const domainParts = domainPart.split('.')
            if (domainParts.some(part => part.length === 0 || /-/.test(part[0]) || /-/.test(part[part.length - 1]))) {
                return false
            }
        }
    
        return true
    }


    /**
     * 인증번호 형식 검사
     * @param verifyNo 인증번호
     * @returns 인증번호 형식 여부
     */
    validateVerifyNo(verifyNo: string): boolean {
        const verifyNoRegex = /^\d{6}$/
        return verifyNoRegex.test(verifyNo)
    }

}
