export interface RankInfoDto {
    total: number;
    wins: number;
    losses: number;
    draws: number;
}

export interface UserInfoDto {
    userId: string;
    nickname: string;
    rank: RankInfoDto;
}

export interface SignUpFormDto {
    userId: string;
    userPw: string;
    nickname: string;
    email: string;
}

export interface FindAccountDto {
    findMode: string;
    email: string;
    verifyCode: string;
}

export interface FindApplyDto {
    findMode: string;
    email: string;
    token: string;
    message: string;
}
