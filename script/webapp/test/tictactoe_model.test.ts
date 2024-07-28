import { describe, expect, test, beforeAll } from '@jest/globals'
import { LoginRequestDto } from '../typedef/login_dto'
import { SignUpFormDto, FindAccountDto, FindApplyDto, UserInfoDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { SendMailFormDto } from '../typedef/message_dto'
import { CreateGameDto } from '../typedef/game_dto'
import TicTacToeLocalRepository from '../repos/tictactoe_local'
import MessageLocalRepository from '../repos/message_local'
import TicTacToeModel from '../model/tictactoe_model'


describe('TicTacToeModel 테스트', () => {
    let model: TicTacToeModel | null = null

    beforeAll(() => {
        const tttRepos = new TicTacToeLocalRepository()
        const msgRepos = new MessageLocalRepository()
        model = new TicTacToeModel(tttRepos, msgRepos)
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
        expect(resp3!.msg).toBe('')
    })

    /**
     * 게임 참가
     */
    test('joinGameRoom', async () => {
        // TODO: 테스트 구현 - joinGameRoom

        // StatusResponseDto respDto1 = model.joinGame(JoinGameDto.builder().build());
        // assertFalse(respDto1.isSuccess());
        // assertEquals(respDto1.getMsg(), "게임방 참여중 오류가 발생했습니다.");
        
        // StatusResponseDto respDto2 = model.joinGame(JoinGameDto.builder()
        //     .gameId(1).build());
        // assertFalse(respDto2.isSuccess());
        // assertEquals(respDto2.getMsg(), "게임방 참여중 오류가 발생했습니다.");

        // StatusResponseDto respDto3 = model.joinGame(JoinGameDto.builder()
        //     .chngrId("test").build());
        // assertFalse(respDto3.isSuccess());
        // assertEquals(respDto3.getMsg(), "게임방 참여중 오류가 발생했습니다.");

        // StatusResponseDto respDto4 = model.joinGame(JoinGameDto.builder()
        //     .gameId(1).chngrId("user1").build());
        // assertTrue(respDto4.isSuccess());
        // assertEquals(respDto4.getMsg(), "");

    })

    /**
     * 게임 진행
     */
    test('updateGameRoom', async () => {
        // TODO: 테스트 구현 - updateGameRoom

        // int gameId = 2;

        // StatusResponseDto respDto1 = model.updateGame(GameUpdateDto.builder()
        //     .gameId(gameId).playerId("test2").msg("B4").build());
        // assertTrue(respDto1 != null);
        // assertFalse(respDto1.isSuccess());
        // assertEquals(respDto1.getMsg(), "해당 플레이어 차례가 아닙니다.");

        // StatusResponseDto respDto2 = model.updateGame(GameUpdateDto.builder()
        //     .gameId(gameId).playerId("test1").msg("B4").build());
        // assertTrue(respDto2 != null);
        // assertFalse(respDto2.isSuccess());
        // assertEquals(respDto2.getMsg(), "해당 위치에 놓을 수 없습니다.");

        // StatusResponseDto respDto3 = model.updateGame(GameUpdateDto.builder().build());
        // assertTrue(respDto3 != null);
        // assertFalse(respDto3.isSuccess());
        // assertEquals(respDto3.getMsg(), "게임방 아이디가 없습니다.");

        // StatusResponseDto respDto4 = model.updateGame(GameUpdateDto.builder()
        //     .gameId(gameId).msg("B4").build());
        // assertTrue(respDto4 != null);
        // assertFalse(respDto4.isSuccess());
        // assertEquals(respDto4.getMsg(), "플레이어 아이디가 없습니다.");

        // StatusResponseDto respDto5 = model.updateGame(GameUpdateDto.builder()
        //     .gameId(gameId).playerId("test1").msg("B123").build());
        // assertTrue(respDto5 != null);
        // assertFalse(respDto5.isSuccess());
        // assertEquals(respDto5.getMsg(), "게임판 범위를 벗어납니다.");

        // StatusResponseDto respDto6 = model.updateGame(GameUpdateDto.builder()
        //     .gameId(gameId).playerId("test1").msg("dummy_message").build());
        // assertTrue(respDto6 != null);
        // assertFalse(respDto6.isSuccess());
        // assertEquals(respDto6.getMsg(), "지원되지 않는 메시지 형식입니다.");

        // StatusResponseDto respDto8 = model.updateGame(GameUpdateDto.builder()
        //     .gameId(gameId).playerId("test1").msg("B1").build());
        // assertTrue(respDto8 != null);
        // assertTrue(respDto8.isSuccess());
        // assertEquals(respDto8.getMsg(), "");

    })

    /**
     * 게임 정보 조회
     */
    test('viewGameRoom', async () => {
        // TODO: 테스트 구현 - viewGameRoom

        // int gameId = 2;
        // GameRoomDto gameDto = model.getGameRoom(gameId);

        // assertTrue(gameDto != null);
        // assertTrue(gameDto.getOwnerId() != null && !gameDto.getOwnerId().isEmpty());
        // assertTrue(gameDto.getChngrId() != null && !gameDto.getChngrId().isEmpty());
        // assertTrue(gameDto.getStatus() != null && !gameDto.getStatus().isEmpty());
        // assertTrue(gameDto.getBoard() != null && !gameDto.getBoard().isEmpty());

    })

    /**
     * 게임 리스트 조회
     */
    test('listGameRoom', async () => {
        // TODO: 테스트 구현 - listGameRoom

        // List<GameRoomDto> listGame = model.listGameRoom();
        // assertTrue(listGame != null);
        // assertTrue(listGame.size() > 0);

        // GameRoomDto gameDto = listGame.get(0);
        // assertTrue(gameDto.getOwnerId() != null && !gameDto.getOwnerId().isEmpty());
        // assertTrue(gameDto.getChngrId() != null && !gameDto.getChngrId().isEmpty());
        // assertTrue(gameDto.getStatus() != null && !gameDto.getStatus().isEmpty());
        // assertTrue(gameDto.getBoard() != null && !gameDto.getBoard().isEmpty());

    })

    /**
     * 사용자 패스워드 변경
     */
    test('changePassword', async () => {
        // TODO: 테스트 구현 - changePassword

        // boolean isOk1 = model.changePassword("dummy", "password");
        // assertFalse(isOk1);

        // boolean isOk2 = model.changePassword("test", "password");
        // assertTrue(isOk2);

    })

})
