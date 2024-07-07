import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import TicTactoeModel from '../model/tictactoe_model'

/**
 * 틱택토 앱
 */
export default class TicTacToeApp {
    /**
     * 틱택토 모델
     */
    private model: TicTactoeModel

    // private isLoggedIn = ref(false)
    private isLoggedIn = ref(true)
    private isShowGame = ref(false)
    private isShowMenu = ref(true)

    constructor() {
        this.model = new TicTactoeModel()
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
        const onClickLogin = () => {
            console.log('onClickLogin')
        }

        const app = createApp({
            setup() {
                return {
                    // 
                    onClickLogin,
                }
            }
        })
        app.mount(selector)
    }

    async renderSignUp(selector: string) {
        const onClickSubmit = () => {
            console.log('onClickSubmit')
        }

        const app = createApp({
            setup() {
                return {
                    // 
                    onClickSubmit,
                }
            }
        })
        app.mount(selector)
    }

    async renderUserLost(selector: string) {
        const findMode = ref('find_id')
        const status = ref('email')

        const onClickSendEmail = () => {
            console.log('onClickSendEmail')
        }

        const onClickVerifyNo = () => {
            console.log('onClickVerifyNo')
        }

        const onClickChangeUserPw = () => {
            console.log('onClickChangeUserPw')
        }

        const app = createApp({
            setup() {
                return {
                    status,
                    findMode,
                    onClickSendEmail,
                    onClickVerifyNo,
                    onClickChangeUserPw,
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
        const onClickChangeName = () => {
            console.log('onClickChangeName')
        }

        const onClickChangeUserPw = () => {
            console.log('onClickChangeUserPw')
        }

        const app = createApp({
            setup() {
                return {
                    onClickChangeName,
                    onClickChangeUserPw,
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
