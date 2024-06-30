import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import HomeModel from '../model/home_model'

/**
 * 홈페이지 앱
 */
export default class HomeApp {
    /**
     * 홈페이지 모델
     */
    private model: HomeModel

    constructor() {
        this.model = new HomeModel()
    }

    /**
     * 홈페이지 섹션 랜더링
     * @param selector 기준 셀렉터
     * @param isData 첨부 데이터 포함 여부
     */
    async render(selector: string, isData: boolean = false) {
        // contentId = '#selector' -> 'selector'
        const contentId = selector.replace(/\#/, '')

        // 컨텐츠 불러오기
        const content = await this.model.content(contentId)

        // 첨부 데이터 불러오기
        let attachData: any = null
        if (isData) {
            // contentID와 같은 이름의 YAML 파일을 읽어서 전달
            const path = `/contents/home/${contentId}.yml`
            const data = await CommonUtil.readData(path)
            if (data != null) {
                const doc = CommonUtil.parseYAML(data)
                attachData = doc
            }
        }

        const app = createApp({
            setup() {
                onMounted(() => {
                    // 숨겨져 있는 섹션을 보여주기
                    const el = document.querySelector(selector)
                    el?.classList.remove('hidden')
                })

                return {
                    content,
                    attachData,
                }
            }
        })
        app.mount(selector)
    }
}