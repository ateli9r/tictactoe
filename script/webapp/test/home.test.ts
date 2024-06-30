import { describe, expect, test } from '@jest/globals'
import HomeModel from '../model/home_model'

describe('HomeModel 테스트', () => {
    const model = new HomeModel()
    
    test('content', async () => {
        model.setDebugMode(true)

        const resp1 = await model.content('dummy')
        expect(resp1).toBe(null)

        const resp2 = await model.content('_test')
        expect(resp2 != null).toBe(true)
        expect(resp2!.length > 0).toBe(true)
        expect(resp2).toBe('<p><strong>markdown</strong></p>')
    })
})
