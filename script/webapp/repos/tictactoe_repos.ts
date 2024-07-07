import { LoginRequestDto, LoginResponseDto } from "../typedef/login_dto";
import { UserInfoDto } from "../typedef/user_dto";

export default interface TicTacToeRepository {
    login(request: LoginRequestDto): Promise<LoginResponseDto | null>;
    logout(): Promise<boolean>;
    getUserInfo(): Promise<UserInfoDto | null>;
}
