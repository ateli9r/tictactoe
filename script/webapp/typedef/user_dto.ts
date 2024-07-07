interface RankInfoDto {
    total: number;
    wins: number;
    losses: number;
    draws: number;
}

interface UserInfoDto {
    userId: string;
    nickname: string;
    rank: RankInfoDto;
}

export { UserInfoDto, RankInfoDto }