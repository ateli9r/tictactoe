import { describe, expect, test } from '@jest/globals'
import CounterModel from '../model/counter_model'

describe('CounterModel 테스트', () => {
    const model = new CounterModel()
    
    test('increase', () => {
        expect(model.getCount()).toBe(0)

        model.increase()
        expect(model.getCount()).toBe(1)

        for (let i = 0; i < 100; i++) {
            model.increase()
        }
        expect(model.getCount()).toBe(101)

        model.resetCount()
        expect(model.getCount()).toBe(0)
    })
})