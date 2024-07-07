import { describe, expect, test, beforeAll } from '@jest/globals'
import { LoginRequestDto } from '../typedef/login_dto'
import TicTacToeLocalRepository from '../repos/tictactoe_local'
import TicTacToeModel from '../model/tictactoe_model'


describe('TicTacToeModel 테스트', () => {
    let model: TicTacToeModel | null = null

    beforeAll(() => {
        const repos = new TicTacToeLocalRepository()
        model = new TicTacToeModel(repos)
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

    // test('signUp', () => {
    //     //
    //     expect(false).toBe(true)
    // })

    // test('sendVerifyEmail', () => {
    //     //
    //     expect(false).toBe(true)
    // })

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
