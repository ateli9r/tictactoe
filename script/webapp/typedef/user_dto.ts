/**
 * 사용자 정보 DTO
 */
export interface UserInfoDto {
    userId: string;
    nickname: string;
    email: string;
    total: number;
    wins: number;
    losses: number;
    draws: number;
}

/**
 * 회원가입 DTO
 */
export interface SignUpFormDto {
    userId: string;
    userPw: string;
    nickname: string;
    email: string;
}

/**
 * 계정찾기 DTO
 */
export interface FindAccountDto {
    findMode: string;
    email: string;
    verifyCode: string;
}

/**
 * 계정찾기 적용 DTO
 */
export interface FindApplyDto {
    findMode: string;
    email: string;
    token: string;
    message: string;
}
