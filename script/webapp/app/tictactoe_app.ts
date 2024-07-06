import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import HomeModel from '../model/home_model'

/**
 * 틱택토 앱
 */
export default class TicTacToeApp {
    /**
     * 틱택토 모델
     */
    // private model: HomeModel

    private isLoggedIn = ref(false)
    private isShowGame = ref(false)
    private isShowMenu = ref(true)

    constructor() {
        // this.model = new HomeModel()
    }

    closeModal() {
        location.hash = ''
    }

    async renderHeader(selector: string) {
        const isLoggedIn = this.isLoggedIn
        const isShowMenu = this.isShowMenu

        const app = createApp({
            setup() {
                return {
                    isLoggedIn,
                    isShowMenu,
                }
            }
        })
        app.mount(selector)
    }

    async renderSignIn(selector: string) {
        const app = createApp({
            setup() {
                return {
                    // 
                }
            }
        })
        app.mount(selector)
    }

    async renderSignUp(selector: string) {
        const app = createApp({
            setup() {
                return {
                    // 
                }
            }
        })
        app.mount(selector)
    }

    async renderNewGame(selector: string) {
        const clickTest = () => {
            this.closeModal()
            this.isShowMenu.value = false

            const game = jQuery('#game')
            game.fadeOut()

            setTimeout(() => {
                this.isShowGame.value = true
                game.fadeIn(150)
            }, 350)
        }

        const app = createApp({
            setup() {
                return {
                    // 
                    clickTest,
                }
            }
        })
        app.mount(selector)
    }

    async renderJoinGame(selector: string) {
        const app = createApp({
            setup() {
                return {
                    // 
                }
            }
        })
        app.mount(selector)
    }

    async renderMyPage(selector: string) {
        const app = createApp({
            setup() {
                return {
                    // 
                }
            }
        })
        app.mount(selector)
    }

    async renderRanking(selector: string) {
        const app = createApp({
            setup() {
                return {
                    // 
                }
            }
        })
        app.mount(selector)
    }

    async renderGame(selector: string) {
        const isShowGame = this.isShowGame
        const board = ref([['O','X','O'],['O','X','O'],['O','X','O']])

        const onClickCell = () => {
            console.log('onClickCell')
        }

        const onClickQuit = () => {
            if (!confirm('quit?')) return

            const game = jQuery('#game')
            game.fadeIn()

            this.isShowGame.value = false
            this.isShowMenu.value = true
        }

        const app = createApp({
            setup() {
                return {
                    isShowGame,
                    board,
                    onClickCell,
                    onClickQuit,
                }
            }
        })
        app.mount(selector)
    }

}
