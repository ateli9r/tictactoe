interface LoginRequestDto {
    userId: string;
    userPw: string;
}

interface LoginResponseDto {
    success: boolean;
    msg: string;
}


export { LoginRequestDto, LoginResponseDto }