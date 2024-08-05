import { describe, expect, test, beforeAll } from '@jest/globals'
import { LoginRequestDto } from '../typedef/login_dto'
import { SignUpFormDto, FindAccountDto, FindApplyDto, UserInfoDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { SendMailFormDto } from '../typedef/message_dto'
import { CreateGameDto, GameRoomDto, GameUpdateDto, JoinGameDto } from '../typedef/game_dto'
import TicTacToeLocalRepository from '../repos/tictactoe_local'
import TicTacToeProdRepository from '../repos/tictactoe_prod'
import MessageLocalRepository from '../repos/message_local'
import MessageProdRepository from '../repos/message_prod'
import TicTacToeModel from '../model/tictactoe_model'


describe('TicTacToeModel 테스트', () => {
    let model: TicTacToeModel | null = null

    beforeAll(() => {
        if (process.env.PROD_TEST == 'true') { // Production Test
            const tttRepos = new TicTacToeProdRepository()
            const msgRepos = new MessageProdRepository()
            model = new TicTacToeModel(tttRepos, msgRepos)
        } else { // Local Test
            const tttRepos = new TicTacToeLocalRepository()
            const msgRepos = new MessageLocalRepository()
            model = new TicTacToeModel(tttRepos, msgRepos)
        }
    })

    /**
     * 로그인 실패
     */
    test('signIn - fail', async () => {
        expect(await model?.logout()).toBe(true)
        expect(await model?.getUserInfo() != null).toBe(false)

        const resp = await model?.login({
            userId: 'dummy',
            userPw: 'dummy',
        } as LoginRequestDto)

        expect(resp != null).toBe(true)
        expect(resp!.success).toBe(false)
        expect(await model?.getUserInfo() != null).toBe(false)
    })

    /**
     * 로그인 성공
     */
    test('signIn - success', async () => {
        expect(await model?.logout()).toBe(true)
        expect(await model?.getUserInfo() != null).toBe(false)

        const resp = await model?.login({
            userId: 'test1',
            userPw: 'test1',
        } as LoginRequestDto)

        expect(resp != null).toBe(true)
        expect(resp?.success).toBe(true)

        const user = await model?.getUserInfo()
        expect(user != null).toBe(true)
        expect(user?.userId).toBe('test1')
        expect(user?.nickname).toBe('테스트')
        expect(user?.total).toBeGreaterThan(1)
        expect(user?.wins).toBeGreaterThan(1)
        expect(user?.losses).toBeGreaterThan(1)
        expect(user?.draws).toBeGreaterThan(1)
    })

    /**
     * 회원가입
     */
    test('signUp', async () => {
        const form = {
            userId: '',
            userPw: '',
            nickname: '',
            email: '',
        } as SignUpFormDto
        
        let resp = await model?.signUp(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(false)
        expect(resp.msg).toBe('아이디를 입력해 주세요.')

        form.userId = 'test'

        resp = await model?.signUp(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(false)
        expect(resp.msg).toBe('닉네임을 입력해 주세요.')

        form.nickname = 'test'

        resp = await model?.signUp(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(false)
        expect(resp.msg).toBe('이메일을 입력해 주세요.')

        form.email = 'test@test.com'

        resp = await model?.signUp(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(false)
        expect(resp.msg).toBe('패스워드를 입력해 주세요.')

        form.userPw = 'test@test.com'

        resp = await model?.signUp(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(true)
        expect(resp.msg).toBe('')
    })

    /**
     * 인증 이메일 발송
     */
    test('sendVerifyEmail', async () => {
        const form = {
            mailTo: 'test@test.com',
        } as SendMailFormDto

        const resp = await model?.sendVerifyEmail(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(true)
        expect(resp.msg).toBe('')
    })

    /**
     * 아이디 찾기 - 토큰 발급
     */
    test('findUserId', async () => {
        const form = {
            findMode: 'findId',
            email: 'test@test.com',
            verifyCode: '000000',
        } as FindAccountDto

        const resp = await model?.findAccount(form)
        expect(resp != null).toBe(true)
        expect(resp!.success).toBe(true)
        expect(resp!.msg.length > 0).toBe(true) // accessToken
    })

    /**
     * 아이디 찾기 - 아이디 출력
     */
    test('findUserIdApply', async () => {
        const form = {
            findMode: 'findId',
            email: 'test@test.com',
            token: 'accessToken',
        } as FindApplyDto

        const resp = await model?.findApply(form)
        expect(resp != null).toBe(true)
        expect(resp!.success).toBe(true)
        expect(resp!.msg.length > 0).toBe(true) // userId
    })

    /**
     * 비밀번호 찾기 - 토큰 발급
     */
    test('findUserPw', async () => {
        const form = {
            findMode: 'findPw',
            email: 'test@test.com',
            verifyCode: '000000',
        } as FindAccountDto

        const resp = await model?.findAccount(form)
        expect(resp != null).toBe(true)
        expect(resp!.success).toBe(true)
        expect(resp!.msg.length > 0).toBe(true) // accessToken
    })

    /**
     * 비밀번호 찾기 - 비밀번호 재설정
     */
    test('findUserPwApply', async () => {
        const form = {
            findMode: 'findPw',
            email: 'test@test.com',
            token: 'accessToken',
            message: 'password=password',
        } as FindApplyDto

        const resp = await model?.findApply(form)
        expect(resp != null).toBe(true)
        expect(resp!.success).toBe(true)
        expect(resp!.msg).toBe('')
    })

    /**
     * 게임 전적 목록
     */
    test('listGameRank', async () => {
        const listUser = await model?.listGameRank() as UserInfoDto[]
        expect(listUser != null).toBe(true)
        expect(listUser.length > 0).toBe(true)

        const userInfo = listUser[0]
        expect(userInfo.userId != null && userInfo.userId.length > 0).toBe(true)
        expect(userInfo.nickname != null && userInfo.nickname.length > 0).toBe(true)
        
        const expTotal = userInfo.wins + userInfo.losses + userInfo.draws
        expect(userInfo.total).toBe(expTotal)
    })

    /**
     * 게임 생성
     */
    test('createGameRoom', async () => {
        const resp1 = await model?.createGame({} as CreateGameDto)
        expect(resp1 != null).toBe(true)
        expect(resp1!.success).toBe(false)
        expect(resp1!.msg).toBe('제목을 입력 하세요.')

        const resp2 = await model?.createGame(
            {title: 'game title'} as CreateGameDto
        )
        expect(resp2 != null).toBe(true)
        expect(resp2!.success).toBe(false)
        expect(resp2!.msg).toBe('방장 아이디가 없습니다.')

        const resp3 = await model?.createGame(
            {title: 'game title', ownerId: 'test'} as CreateGameDto
        )
        expect(resp3 != null).toBe(true)
        expect(resp3!.success).toBe(true)
        // expect(resp3!.msg).toBe('')
        expect(resp3!.msg.indexOf('gameId=') == 0).toBe(true)
    })

    /**
     * 게임 참가
     */
    test('joinGameRoom', async () => {
        const resp = await model?.joinGame(
            {gameId: 1, chngrId: 'user1'} as JoinGameDto
        )
        expect(resp != null).toBe(true)
        expect(resp!.success).toBe(true)
        expect(resp!.msg).toBe('')
    })

    /**
     * 게임 진행
     */
    test('updateGameRoom', async () => {
        const gameId = 2
        const resp1 = await model?.updateGame({} as GameUpdateDto)
        expect(resp1 != null).toBe(true)
        expect(resp1!.success).toBe(false)
        expect(resp1!.msg).toBe('게임방 아이디가 없습니다.')

        const resp2 = await model?.updateGame(
            {gameId: gameId, msg: 'B4'} as GameUpdateDto
        )
        expect(resp2 != null).toBe(true)
        expect(resp2!.success).toBe(false)
        expect(resp2!.msg).toBe('플레이어 아이디가 없습니다.')

        const resp3 = await model?.updateGame(
            {gameId: gameId, playerId: 'test1', msg: 'B123'} as GameUpdateDto
        )
        expect(resp3 != null).toBe(true)
        expect(resp3!.success).toBe(false)
        expect(resp3!.msg).toBe('게임판 범위를 벗어납니다.')

        const resp4 = await model?.updateGame(
            {gameId: gameId, playerId: 'test1', msg: 'dummy_message'} as GameUpdateDto
        )
        expect(resp4 != null).toBe(true)
        expect(resp4!.success).toBe(false)
        expect(resp4!.msg).toBe('지원되지 않는 메시지 형식입니다.')

        const resp5 = await model?.updateGame(
            {gameId: gameId, playerId: 'test1', msg: 'B1'} as GameUpdateDto
        )
        expect(resp5 != null).toBe(true)
        expect(resp5!.success).toBe(true)
        expect(resp5!.msg).toBe('')
    })

    /**
     * 게임 정보 조회
     */
    test('viewGameRoom', async () => {
        const gameId = 2
        const resp = await model?.getGameRoom(gameId) as GameRoomDto
        expect(resp != null).toBe(true)
        expect(resp.ownerId != null && resp.ownerId.length > 0).toBe(true)
        expect(resp.chngrId != null && resp.chngrId.length > 0).toBe(true)
        expect(resp.status != null && resp.status.length > 0).toBe(true)
        expect(resp.board != null && resp.board.length > 0).toBe(true)
    })

    /**
     * 게임 리스트 조회
     */
    test('listGameRoom', async () => {
        const listGame = await model?.listGameRoom() as GameRoomDto[]
        expect(listGame != null).toBe(true)
        expect(listGame.length > 0)

        for (let i = 0; i < listGame.length; i++) {
            const game = listGame[i] as GameRoomDto
            expect(game != null).toBe(true)
            expect(game.ownerId != null && game.ownerId.length > 0).toBe(true)
            expect(game.status != null && game.status.length > 0).toBe(true)
            expect(game.board != null && game.board.length > 0).toBe(true)

            if (game.status != 'W')
                expect(game.chngrId != null && game.chngrId.length > 0).toBe(true)
        }
    })

    /**
     * 이메일 형식 검사
     */
    test('validateEmail', () => {
        const listCases = [
            {email: 'test@example.com', isEmail: true},
            {email: 'user@sub.domain.com', isEmail: true},
            {email: 'user+name@example.co.uk', isEmail: true},
            {email: 'user.name@domain.com', isEmail: true},
            {email: 'user.name+tag+sorting@example.com', isEmail: true},
            {email: 'user_name@domain.com', isEmail: true},
            {email: 'username@domain.co.in', isEmail: true},
            {email: 'username@domain', isEmail: false},  // No top-level domain
            {email: '@domain.com', isEmail: false},  // No local part
            {email: 'username@.com', isEmail: false},  // No domain
            {email: 'username@domain..com', isEmail: false},  // Double dot in domain
            {email: 'username@domain.com.', isEmail: false},  // Trailing dot
            {email: 'user name@domain.com', isEmail: false},  // Space in local part
            {email: 'username@domain,com', isEmail: false},  // Comma instead of dot
            {email: 'username@-domain.com', isEmail: false},  // Leading dash in domain
            {email: 'username@domain.c', isEmail: false},  // One character top-level domain
            {email: 'username@domain.corporate', isEmail: true},  // Long top-level domain
            {email: '', isEmail: false},  // Empty string
        ]

        let chk = 0
        for (let i = 0; i < listCases.length; i++) {
            const email = listCases[i].email
            const isEmail = model?.validateEmail(email)

            if (isEmail == listCases[i].isEmail) {
                chk += 1
            }
        }

        expect(chk).toBe(listCases.length)
    })

    /**
     * 인증번호 형식 검사
     */
    test('validateVerifyNo', () => {
        const listCases = [
            {verifyNo: '123456', isOk: true},
            {verifyNo: '000000', isOk: true},
            {verifyNo: '999999', isOk: true},
            {verifyNo: '12345', isOk: false},      // Less than 6 digits
            {verifyNo: '1234567', isOk: false},    // More than 6 digits
            {verifyNo: '12345a', isOk: false},     // Contains a letter
            {verifyNo: '123 456', isOk: false},    // Contains a space
            {verifyNo: '12-3456', isOk: false},    // Contains a special character
            {verifyNo: 'abcdef', isOk: false},     // All letters
            {verifyNo: '', isOk: false},           // Empty string
        ];
    
        let chk = 0;
        for (let i = 0; i < listCases.length; i++) {
            const verifyNo = listCases[i].verifyNo
            const isOk = model?.validateVerifyNo(verifyNo)
    
            if (isOk === listCases[i].isOk) {
                chk += 1;
            }
        }
    
        expect(chk).toBe(listCases.length)
    })
})
