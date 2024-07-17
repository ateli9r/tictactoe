import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import TicTactoeModel from '../model/tictactoe_model'
import TicTacToeProdRepository from '../repos/tictactoe_prod'
import { LoginRequestDto } from '../typedef/login_dto'
import MessageProdRepository from '../repos/message_prod'
import { SignUpFormDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'

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

    private vueApp: any = {}

    constructor() {
        const tttRepos = new TicTacToeProdRepository()
        const msgRepos = new MessageProdRepository()
        this.model = new TicTactoeModel(tttRepos, msgRepos)
    }

    getModel() {
        return this.model
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
        this.vueApp.header = app
    }

    async renderSignIn(selector: string) {
        const props = {
            userId: '',
            userPw: '',
        }

        const onClickLogin = async () => {
            if (props.userId.length == 0 || props.userPw.length == 0) {
                const msg = '아이디, 패스워드를 입력해주세요.'
                alert(msg)
                return
            }

            const resp = await this.model.login({
                userId: props.userId,
                userPw: props.userPw,
            } as LoginRequestDto) as StatusResponseDto

            if (!resp.success) {
                const msg = resp.msg
                alert(msg)
                return
            }
            callUserInfo()
        }

        const clearForm = () => {
            props.userId = ''
            props.userPw = ''

            jQuery('#sign_in').find('form')[0].reset()
        }

        const callUserInfo = async () => {
            setTimeout(async () => {
                const user = await this.model.getUserInfo()
                if (user != null) {
                    this.isLoggedIn.value = true
                }
                clearForm()
                this.closeModal()
            }, 300)
        }

        const app = createApp({
            data() {
                return props
            },
            setup() {
                onMounted(() => {
                    setTimeout(async () => {
                        jQuery('#sign_in').find('input')[0].focus()
                    }, 300)
                })

                return {
                    onClickLogin,
                }
            }
        })
        app.mount(selector)
        this.vueApp.signIn = app
    }

    async renderSignUp(selector: string) {
        const props = {
            userId: '',
            userPw: '',
            userPwRe: '',
            nickname: '',
            email: '',
            verifyNo: '',
        }

        const clearForm = () => {
            props.userId = ''
            props.userPw = ''
            props.userPwRe = ''
            props.nickname = ''
            props.email = ''
            props.verifyNo = ''

            jQuery('#sign_up').find('form')[0].reset()
        }

        const onClickSubmit = async () => {
            const request = {
                userId: props.userId,
                userPw: props.userPw,
                nickname: props.nickname,
                email: props.email,
            } as SignUpFormDto

            if (props.userPw.length > 0) {
                if (props.userPw != props.userPwRe) {
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
                clearForm()
                this.closeModal()
            }
        }

        const onClickSendEmail = async () => {
            console.log('onClickSendEmail')
        }

        const onClickCheckVerify = async () => {
            console.log('onClickCheckVerify')
        }

        const app = createApp({
            data() {
                return props
            },
            setup() {
                onMounted(() => {
                    setTimeout(async () => {
                        jQuery('#sign_up').find('input')[0].focus()
                    }, 300)
                })

                return {
                    onClickSubmit,
                    onClickSendEmail,
                    onClickCheckVerify,
                }
            }
        })
        app.mount(selector)
        this.vueApp.signUp = app
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
                onMounted(() => {
                    setTimeout(async () => {
                        jQuery('#user_lost').find('input')[2].focus() // 이메일
                    }, 300)
                })

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
        this.vueApp.userLost = app
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
        this.vueApp.newGame = app
    }

    async renderJoinGame(selector: string) {
        const onRefresh = async () => {
            console.log('onRefresh - renderJoinGame')
        }

        const app = createApp({
            setup() {
                return {
                    onRefresh,
                }
            }
        })
        app.mount(selector)

        setTimeout(() => {
            console.log('!!!', (app as any).onRefresh)
        }, 1000)

        this.vueApp.joinGame = app
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
        this.vueApp.myPage = app
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
        this.vueApp.ranking = app
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
        this.vueApp.game = app
    }

}
