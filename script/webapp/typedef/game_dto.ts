/**
 * 게임방 생성 DTO
 */
export interface CreateGameDto {
    title: string
    ownerId: string
}

/**
 * 게임방 가입 DTO
 */
export interface JoinGameDto {
    gameId: number
    chngrId: string
}

/**
 * 게임 진행 DTO
 */
export interface GameUpdateDto {
    gameId: number
    playerId: string
    msg: string
}

/**
 * 게임방 DTO
 */
export interface GameRoomDto {
    gameId: number
    ownerId: string
    chngrId: string
    status: string
    board: string
}
