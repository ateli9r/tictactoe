import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../../util/common'
import CounterModel from '../../model/counter_model'

/**
 * 카운터 앱
 */
export default class CounterApp {
    public app: any

    /**
     * 앱 생성
     * @param selector 기준 셀렉터
     */
    async create(selector: string) {
        /**
         * 카운터 모델
         */
        const model = new CounterModel()

        this.app = createApp({
            data() {
                return {
                    // 카운트
                    count: 0,
                }
            },
            methods: {
                // 카운터 증가
                increase() {
                    model.increase()
                    this.count = model.getCount()
                },
                // 카운터 초기화
                reset() {
                    model.resetCount()
                    this.count = model.getCount()
                },
            },
            setup() {
                // 증가 버튼
                const incButton = ref(null)

                onMounted(() => {
                    // 앱을 불러왔을때, 증가 버튼에 포커스
                    if (incButton.value) {
                        (incButton.value as any).focus()
                    }
                })

                return {
                    incButton,
                }
            },
            template: await CommonUtil.templateHTML('counter'),
        })

        this.app.mount(selector)
    }
}