import { LoginRequestDto } from '../typedef/login_dto'
import { SignUpFormDto, UserInfoDto, FindAccountDto, FindApplyDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'

export default interface TicTacToeRepository {
    login(request: LoginRequestDto): Promise<StatusResponseDto | null>;
    logout(): Promise<boolean>;
    getUserInfo(): Promise<UserInfoDto | null>;
    signUp(request: SignUpFormDto): Promise<StatusResponseDto | null>;
    findAccount(request: FindAccountDto): Promise<StatusResponseDto | null>;
    findApply(request: FindApplyDto): Promise<StatusResponseDto | null>;
}
