import { LoginRequestDto } from '../typedef/login_dto'
import { SignUpFormDto, UserInfoDto, FindAccountDto, FindApplyDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { CreateGameDto } from '../typedef/game_dto'

export default interface TicTacToeRepository {
    /**
     * 로그인
     * @param request 로그인 요청
     */
    login(request: LoginRequestDto): Promise<StatusResponseDto | null>

    /**
     * 로그아웃
     */
    logout(): Promise<boolean>

    /**
     * 사용자 정보 가져오기
     */
    getUserInfo(): Promise<UserInfoDto | null>

    /**
     * 회원가입
     * @param request 회원가입 요청
     */
    signUp(request: SignUpFormDto): Promise<StatusResponseDto | null>

    /**
     * 계정정보 찾기
     * @param request 계정정보 찾기 요청
     */
    findAccount(request: FindAccountDto): Promise<StatusResponseDto | null>

    /**
     * 계정찾기 적용
     * @param request 계정찾기 적용 요청
     */
    findApply(request: FindApplyDto): Promise<StatusResponseDto | null>

    /**
     * 게임 전적 목록
     */
    listGameRank(): Promise<UserInfoDto[] | null>

    /**
     * 게임 생성
     * @param request 게임 생성 요청
     */
    createGame(request: CreateGameDto): Promise<StatusResponseDto | null>

}
