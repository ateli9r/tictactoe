import { LoginRequestDto } from '../typedef/login_dto';
import { SignUpFormDto, UserInfoDto } from '../typedef/user_dto';
import { StatusResponseDto } from '../typedef/cmmn_dto'

export default interface TicTacToeRepository {
    login(request: LoginRequestDto): Promise<StatusResponseDto | null>;
    logout(): Promise<boolean>;
    getUserInfo(): Promise<UserInfoDto | null>;
    signUp(request: SignUpFormDto): Promise<StatusResponseDto | null>;
}
