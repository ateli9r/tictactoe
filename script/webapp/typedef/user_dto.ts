interface RankInfoDto {
    rankNo: number;
    total: number;
    win: number;
    lose: number;
    draw: number;
}

interface UserInfoDto {
    userId: string;
    userNm: string;
    rank: RankInfoDto;
}

export { UserInfoDto, RankInfoDto }