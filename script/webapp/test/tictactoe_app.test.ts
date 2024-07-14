import { describe, expect, test, beforeAll } from '@jest/globals'
import TicTacToeApp from '../app/tictactoe_app'
import TicTactoeModel from '../model/tictactoe_model'

describe('TicTacToeApp 테스트', () => {
    let app: TicTacToeApp | null = null
    let model: TicTactoeModel | null = null

    beforeAll(() => {
        app = new TicTacToeApp()
        model = app.getModel()
    })

    if (process.env.PROD_TEST) {
        test('this is production test', async () => {
            expect(true).toBe(false)
        })
    } else {
        test('skip production test', async () => {
            expect(true).toBe(true)
        })
    }
})
