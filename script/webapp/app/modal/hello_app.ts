import { createApp, onMounted, ref, computed, h } from 'vue'
import CommonUtil from '../../util/common'
import HelloModel from '../../model/hello_model'

/**
 * 인사말 앱
 */
export default class HelloApp {
    public app: any

    /**
     * 앱 생성
     * @param selector 기준 셀렉터
     */
    async create(selector: string) {
        /**
         * 인사말 모델
         */
        const model = new HelloModel()

        this.app = createApp({
            data() {
                return {
                    // 이름
                    name: '',
                }
            },
            setup () {
                // 이름 입력칸
                const nameInput = ref(null)

                onMounted(() => {
                    // 앱을 불러왔을때, 이름 입력칸에 포커스
                    if (nameInput.value) {
                        (nameInput.value as any).focus()
                    }
                })

                return {
                    nameInput,
                }
            },
            computed: {
                // 인사말 출력
                message() {
                    return model.hello(this.name)
                }
            },
            template: await CommonUtil.templateHTML('hello'),
        })

        this.app.mount(selector)
    }
}