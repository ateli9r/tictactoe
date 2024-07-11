import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import TicTactoeModel from '../model/tictactoe_model'
import TicTacToeProdRepository from '../repos/tictactoe_prod'
import { LoginRequestDto, LoginResponseDto } from '../typedef/login_dto'
import MessageProdRepository from '../repos/message_prod'
import { SignUpFormDto } from '../typedef/user_dto'

/**
 * 틱택토 앱
 */
export default class TicTacToeApp {
    /**
     * 틱택토 모델
     */
    private model: TicTactoeModel

    private isLoggedIn = ref(false)
    private isShowGame = ref(false)
    private isShowMenu = ref(true)

    constructor() {
        const tttRepos = new TicTacToeProdRepository()
        const msgRepos = new MessageProdRepository()
        this.model = new TicTactoeModel(tttRepos, msgRepos)
    }

    closeModal() {
        location.hash = ''
    }

    move(key: string) {
        location.hash = key
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
        const dataUserId = ref('')
        const dataUserPw = ref('')

        const onClickLogin = async () => {
            if (dataUserId.value.length == 0 || dataUserPw.value.length == 0) {
                const msg = '아이디, 패스워드를 입력해주세요.'
                alert(msg)
                return
            }

            const resp = await this.model.login({
                userId: dataUserId.value,
                userPw: dataUserPw.value,
            } as LoginRequestDto) as LoginResponseDto

            if (!resp.success) {
                const msg = resp.msg
                alert(msg)
                return
            }
            callUserInfo()
        }

        const callUserInfo = async () => {
            setTimeout(async () => {
                const user = await this.model.getUserInfo()
                if (user != null) {
                    this.isLoggedIn.value = true
                }
                this.closeModal()
            }, 300)
        }

        const app = createApp({
            setup() {
                return {
                    dataUserId,
                    dataUserPw,
                    onClickLogin,
                }
            }
        })
        app.mount(selector)
    }

    async renderSignUp(selector: string) {
        const propsData = {
            userId: '',
            userPw: '',
            userPwRe: '',
            nickname: '',
            email: '',
            verifyNo: '',
        }

        const onClickSubmit = async () => {
            const request = {
                userId: propsData.userId,
                userPw: propsData.userPw,
                nickname: propsData.nickname,
                email: propsData.email,
            } as SignUpFormDto

            if (propsData.userPw.length > 0) {
                if (propsData.userPw != propsData.userPwRe) {
                    alert('패스워드가 다릅니다.')
                    return
                }
            }

            const resp = await this.model.signUp(request)
            if (resp == null) {
                alert('error')
                return
            }
            if (!resp.success) {
                alert(resp.msg)
                return
            } else {
                const msg = '회원가입 완료'
                alert(msg)

                this.closeModal()
            }
        }

        const app = createApp({
            data() {
                return propsData
            },
            setup() {
                return {
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

        const onClickLogout = async () => {
            await this.model.logout()
            this.isLoggedIn.value = false;
            this.closeModal()
        }

        const app = createApp({
            setup() {
                return {
                    onClickChangeName,
                    onClickChangeUserPw,
                    onClickLogout,
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
