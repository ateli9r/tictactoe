import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import TicTactoeModel from '../model/tictactoe_model'
import TicTacToeProdRepository from '../repos/tictactoe_prod'
import { LoginRequestDto } from '../typedef/login_dto'
import MessageProdRepository from '../repos/message_prod'
import { FindAccountDto, SignUpFormDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { SendMailFormDto } from '../typedef/message_dto'
import TicTacToeLocalRepository from '../repos/tictactoe_local'
import MessageLocalRepository from '../repos/message_local'

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
    private isProd = false

    private vueApp: any = {}

    constructor() {
        if (this.isProd) {
            const tttRepos = new TicTacToeProdRepository()
            const msgRepos = new MessageProdRepository()
            this.model = new TicTactoeModel(tttRepos, msgRepos)
        } else {
            const tttRepos = new TicTacToeLocalRepository()
            const msgRepos = new MessageLocalRepository()
            this.model = new TicTactoeModel(tttRepos, msgRepos)
        }
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

        const onRefresh = async () => {
            clearForm()
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
                    onRefresh,
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
            // TODO: onClickSendEmail

            const form = {
                mailTo: props.email,
            } as SendMailFormDto

            const resp = await this.model.sendVerifyEmail(form) as StatusResponseDto

            if (!resp.success && resp.msg.length > 0) {
                alert(resp.msg)
            } else {
                console.log('ok')
            }
        }

        const onClickCheckVerify = async () => {
            // TODO: onClickCheckVerify
            console.log('onClickSendEmail')
        }

        const onRefresh = async () => {
            clearForm()
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
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.signUp = app
    }

    async renderUserLost(selector: string) {
        // const findMode = ref('findId')
        const status = ref('email')
        const props = {
            findMode: '',
            email: '',
            verifyNo: '',
        }

        const onClickSendEmail = async () => {
            // TODO: onClickSendEmail

            if (props.findMode.length == 0) {
                const msg = '계정찾기 구분이 지정되지 않았습니다.'
                alert(msg)
                return
            }

            const form = {
                mailTo: props.email,
            } as SendMailFormDto

            const resp = await this.model.sendVerifyEmail(form) as StatusResponseDto

            if (!resp.success && resp.msg.length > 0) {
                alert(resp.msg)
            } else {
                console.log('ok')
            }
        }

        const onClickVerifyNo = async () => {
            // TODO: onClickVerifyNo

            const form = {
                findMode: props.findMode,
                email: props.email,
                verifyCode: props.verifyNo,
            } as FindAccountDto

            console.log(form)
    
            // const resp = await this.model.findAccount(form) as StatusResponseDto

            // if (!resp.success && resp.msg.length > 0) {
            //     alert(resp.msg)
            // } else {
            //     console.log('ok')
            // }
            if (true) {

                if (props.findMode == 'findId') {
                    status.value = 'printUserId'

                } else if (props.findMode == 'findPw') {
                    // props.status = 'changeUserPw'
                    status.value = 'changeUserPw'
                }
            }
        }

        const onClickChangeUserPw = () => {
            // TODO: onClickChangeUserPw
            console.log('onClickChangeUserPw')
        }

        const onRefresh = async () => {
            clearForm()
        }

        const clearForm = () => {
            jQuery('#user_lost').find('form')[0].reset()

            status.value = 'email'
            props.findMode = ''
            props.email = ''
            props.verifyNo = ''
        }

        const app = createApp({
            data() {
                return props
            },
            setup() {
                onMounted(() => {
                    setTimeout(async () => {
                        jQuery('#user_lost').find('input')[2].focus() // 이메일
                    }, 300)
                })

                return {
                    status,
                    onClickSendEmail,
                    onClickVerifyNo,
                    onClickChangeUserPw,
                    onRefresh,
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
            // TODO: onRefresh - renderJoinGame
        }

        const app = createApp({
            setup() {
                return {
                    onRefresh,
                }
            }
        })
        app.mount(selector)

        this.vueApp.joinGame = app
    }

    async renderMyPage(selector: string) {
        const onRefresh = async () => {
            // TODO: onRefresh - renderMyPage
        }

        const onClickChangeName = () => {
            // TODO: onClickChangeName
        }

        const onClickChangeUserPw = () => {
            // TODO: onClickChangeUserPw
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
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.myPage = app
    }

    async renderRanking(selector: string) {
        const onRefresh = async () => {
            // TODO: onRefresh - renderRanking
        }

        const app = createApp({
            setup() {
                return {
                    onRefresh,
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
            // TODO: onClickCell
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
