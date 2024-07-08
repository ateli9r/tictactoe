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
    nickname: string;
    email: string;
    password: string;
}
