export default class CounterModel {
    /**
     * 카운트 값
     */
    private count: number = 0

    /**
     * 카운트 가져오기
     * @returns 카운트 값
     */
    getCount() {
        return this.count
    }

    /**
     * 카운트 초기화
     */
    resetCount() {
        this.count = 0
    }

    /**
     * 카운트 증가 (+1)
     */
    increase() {
        this.count += 1
    }
}