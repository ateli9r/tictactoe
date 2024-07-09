import { describe, expect, test, beforeAll } from '@jest/globals'
import { LoginRequestDto } from '../typedef/login_dto'
import { SignUpFormDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { SendMailFormDto } from '../typedef/message_dto'
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

    test('signIn - fail', async () => {
        expect(await model?.logout()).toBe(true)
        expect(await model?.getUserInfo() != null).toBe(false)

        const resp = await model?.login({
            userId: 'dummy',
            userPw: 'dummy',
        } as LoginRequestDto)

        expect(resp != null).toBe(true)
        expect(resp?.success).toBe(false)
        expect(await model?.getUserInfo() != null).toBe(false)
    })

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
        expect(user?.rank.total).toBeGreaterThan(1)
        expect(user?.rank.wins).toBeGreaterThan(1)
        expect(user?.rank.losses).toBeGreaterThan(1)
        expect(user?.rank.draws).toBeGreaterThan(1)
    })

    test('signUp', async () => {
        const form = {
            userId: '',
            nickname: '',
            email: '',
            password: '',
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

        form.password = 'test@test.com'

        resp = await model?.signUp(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(true)
        expect(resp.msg).toBe('')
    })

    test('sendVerifyEmail', async () => {
        // TODO: create verify code
        // const verifyCode = await model?.createVerifyCode() as StstusResponseDto
        const verifyCode = '000000'

        const form = {
            mailTo: 'test@test.com',
            title: 'Verify Code',
            content: `code: ${verifyCode}`,
        } as SendMailFormDto

        const resp = await model?.sendVerifyEmail(form) as StatusResponseDto
        expect(resp != null).toBe(true)
        expect(resp.success).toBe(true)
        expect(resp.msg).toBe('')
    })

    // test('checkVerifyNo', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('changeUserName', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('changeUserPw', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('newGame', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('joinGame', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('listGame', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('getUserInfo', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('listRanking', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('getGameInfo', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('sendGameRequest', () => {
    //     //
    //     expect(false).toBe(true)
    // })
})
