import { createApp, onMounted, ref } from 'vue'
import CommonUtil from '../util/common'
import TicTactoeModel from '../model/tictactoe_model'
import TicTacToeProdRepository from '../repos/tictactoe_prod'
import { LoginRequestDto } from '../typedef/login_dto'
import MessageProdRepository from '../repos/message_prod'
import { FindAccountDto, FindApplyDto, SignUpFormDto, UserInfoDto } from '../typedef/user_dto'
import { StatusResponseDto } from '../typedef/cmmn_dto'
import { SendMailFormDto } from '../typedef/message_dto'
import TicTacToeLocalRepository from '../repos/tictactoe_local'
import MessageLocalRepository from '../repos/message_local'
import { CreateGameDto, GameRoomDto, JoinGameDto } from '../typedef/game_dto'

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

    // private myGameId: number = 0
    // private refGameBoard = ref('.........')

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

    async checkLoggedIn() {
        const userInfo = await this.model.getUserInfo()
        if (userInfo == null) {
            this.closeModal()
            return false
        }
        return true
    }

    async switchGameBoard(gameId: number) {
        this.closeModal()
        this.isShowMenu.value = false

        const game = jQuery('#game')
        game.fadeOut()

        setTimeout(() => {
            this.isShowGame.value = true
            game.fadeIn(150)

            setTimeout(() => {
                game.find('.onShowClick').data('game_id', gameId)
                game.find('.onShowClick').get(0)?.click()
            }, 150)
        }, 350)
    }

    /**
     * VueApp - Header
     */
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

    /**
     * VueApp - 로그인
     */
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

    /**
     * VueApp - 회원가입
     */
    async renderSignUp(selector: string) {
        const isVerified = ref(false)
        const isVerifySent = ref(false)
        const props = {
            userId: '',
            userPw: '',
            userPwRe: '',
            nickname: '',
            email: '',
            verifyNo: '',
            token: '',
        }

        const clearForm = () => {
            isVerified.value = false
            isVerifySent.value = false

            props.userId = ''
            props.userPw = ''
            props.userPwRe = ''
            props.nickname = ''
            props.email = ''
            props.verifyNo = ''
            props.token = ''

            jQuery('#sign_up').find('form')[0].reset()
        }

        const onClickSubmit = async () => {
            const request = {
                userId: props.userId,
                userPw: props.userPw,
                nickname: props.nickname,
                email: props.email,
                token: props.token,
            } as SignUpFormDto

            if (props.userPw.length > 0) {
                if (props.userPw != props.userPwRe) {
                    alert('패스워드가 다릅니다.')
                    return
                }
            }

            const resp = await this.model.signUp(request) as StatusResponseDto
            if (!resp.success) {
                alert(resp.msg)
                return
            }

            clearForm()
            this.closeModal()

            const msg = '회원가입 완료'
            alert(msg)
        }

        const onClickSendEmail = async () => {
            const form = {
                mailTo: props.email,
            } as SendMailFormDto

            const resp = await this.model.sendVerifyEmail(form) as StatusResponseDto

            if (!resp.success) {
                alert(resp.msg)
                return
            }
            isVerifySent.value = true
        }

        const onClickCheckVerify = async () => {
            const form = {
                findMode: 'signUp',
                email: props.email,
                verifyCode: props.verifyNo,
            } as FindAccountDto

            const resp = await this.model.findAccount(form) as StatusResponseDto

            if (!resp.success) {
                const msg = '인증 오류가 발생했습니다.'
                alert(msg)
                return
            }
            if (resp.msg.length > 0 && resp.msg.indexOf('accessToken=') == 0) {
                props.token = resp.msg.substring(12)
                isVerified.value = true
            }
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
                    isVerified,
                    isVerifySent,
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

    /**
     * VueApp - 계정찾기
     */
    async renderUserLost(selector: string) {
        const status = ref('email')
        const isVerifySent = ref(false)
        const props = {
            findMode: '',
            email: '',
            verifyNo: '',
            token: '',
            userId: '',
            userPw: '',
            userPwRe: '',
        }

        const onClickSendEmail = async () => {
            if (props.findMode.length == 0) {
                const msg = '계정찾기 구분이 지정되지 않았습니다.'
                alert(msg)
                return
            }

            const form = {
                mailTo: props.email,
            } as SendMailFormDto

            const resp = await this.model.sendVerifyEmail(form) as StatusResponseDto

            if (!resp.success) {
                const msg = resp.msg
                alert(msg)
                return
            }
            isVerifySent.value = true
        }

        const onClickVerifyNo = async () => {
            const form = {
                findMode: props.findMode,
                email: props.email,
                verifyCode: props.verifyNo,
            } as FindAccountDto

            const resp = await this.model.findAccount(form) as StatusResponseDto

            if (!resp.success) {
                const msg = '인증 오류가 발생했습니다.'
                alert(msg)
                return
            }

            if (resp.msg.length > 0 && resp.msg.indexOf('accessToken=') == 0) {
                props.token = resp.msg.substring(12)

                if (props.findMode == 'findId') {
                    printUserId()
                } else if (props.findMode == 'findPw') {
                    status.value = 'changeUserPw'
                }
            }
        }

        const onClickOkUserId = () => {
            this.closeModal()
        }

        const printUserId = async () => {
            const form = {
                findMode: 'findId',
                email: props.email,
                token: props.token,
            } as FindApplyDto
    
            const resp = await this.model.findApply(form) as StatusResponseDto

            if (!resp.success) {
                const msg = '인증 오류가 발생했습니다.'
                alert(msg)
                return
            }

            if (resp.msg.length > 0 && resp.msg.indexOf('userId=') == 0) {
                props.userId = resp.msg.substring(7)
                status.value = 'printUserId'
            }
        }

        const onClickChangeUserPw = async () => {
            if (props.userPw.length == 0) {
                const msg = '변경할 패스워드를 입력해주세요.'
                alert(msg)
                return
            }
            if (props.userPw != props.userPwRe) {
                const msg = '패스워드가 다릅니다.'
                alert(msg)
                return
            }

            const form = {
                findMode: 'findPw',
                email: props.email,
                token: props.token,
                message: `password=${props.userPw}`,
            } as FindApplyDto

            const resp = await this.model.findApply(form) as StatusResponseDto

            if (!resp.success) {
                const msg = resp.msg
                alert(msg)
                return
            }

            this.closeModal()

            const msg = '패스워드 변경 완료'
            alert(msg)
        }

        const onRefresh = async () => {
            clearForm()
        }

        const clearForm = () => {
            jQuery('#user_lost').find('form')[0].reset()

            status.value = 'email'
            isVerifySent.value = false

            props.findMode = ''
            props.email = ''
            props.verifyNo = ''
            props.token = ''
            props.userId = ''
            props.userPw = ''
            props.userPwRe = ''
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
                    isVerifySent,
                    onClickSendEmail,
                    onClickVerifyNo,
                    onClickOkUserId,
                    onClickChangeUserPw,
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.userLost = app
    }

    /**
     * VueApp - 새 게임
     */
    async renderNewGame(selector: string) {
        const props = {
            title: '',
        }

        const onClickCreateGame = async () => {
            if (props.title.length == 0) {
                const msg = '제목을 입력해주세요.'
                alert(msg)
                return
            }

            const userInfo = await this.model.getUserInfo()
            if (userInfo == null) return

            const form = {
                title: props.title,
                ownerId: userInfo.userId,
            } as CreateGameDto

            const resp = await this.model.createGame(form) as StatusResponseDto

            if (!resp.success) {
                const msg = resp.msg
                alert(msg)
                return
            }

            if (resp.msg.length > 0 && resp.msg.indexOf('gameId=') == 0) {
                const gameId = parseInt(resp.msg.substring(7))
                this.switchGameBoard(gameId)
            }
        }

        const onRefresh = async () => {
            if (!await this.checkLoggedIn()) return
        }

        const app = createApp({
            data() {
                return props
            },
            setup() {
                return {
                    onClickCreateGame,
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.newGame = app
    }

    /**
     * VueApp - 게임 참가
     */
    async renderJoinGame(selector: string) {
        const refList = ref<GameRoomDto[]>([])

        const onClickJoin = async (game: GameRoomDto) => {
            const userInfo = await this.model.getUserInfo()
            if (userInfo == null) return

            const form = {
                gameId: game.gameId,
                chngrId: userInfo.userId,
            } as JoinGameDto

            const resp = await this.model.joinGame(form) as StatusResponseDto

            if (!resp.success) {
                const msg = resp.msg
                alert(msg)
                return
            }
            this.switchGameBoard(game.gameId)
        }

        const onRefresh = async () => {
            if (!await this.checkLoggedIn()) return

            refList.value.length = 0
            const listGame = await this.model.listGameRoom() as GameRoomDto[]

            for (let i = 0; i < listGame.length; i++) {
                const item = CommonUtil.copyObject(listGame[i]) as GameRoomDto
                refList.value.push(item)
            }
        }

        const app = createApp({
            setup() {
                return {
                    refList,
                    onClickJoin,
                    onRefresh,
                }
            }
        })
        app.mount(selector)

        this.vueApp.joinGame = app
    }

    /**
     * VueApp - 마이 페이지
     */
    async renderMyPage(selector: string) {
        const refUserInfo = ref<UserInfoDto | null>(null)

        const onRefresh = async () => {
            if (!await this.checkLoggedIn()) return

            const userInfo = await this.model.getUserInfo()
            if (userInfo == null) return

            refUserInfo.value = userInfo
        }

        const onClickLogout = async () => {
            await this.model.logout()
            this.isLoggedIn.value = false;
            this.closeModal()
        }

        const app = createApp({
            setup() {
                return {
                    refUserInfo,
                    onClickLogout,
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.myPage = app
    }

    /**
     * VueApp - 랭킹
     */
    async renderRanking(selector: string) {
        const refList = ref<UserInfoDto[]>([])

        const onRefresh = async () => {
            if (!await this.checkLoggedIn()) return

            refList.value.length = 0
            const listUser = await this.model.listGameRank() as UserInfoDto[]

            for (let i = 0; i < listUser.length; i++) {
                const item = CommonUtil.copyObject(listUser[i]) as UserInfoDto
                refList.value.push(item)
            }
        }

        const app = createApp({
            setup() {
                return {
                    refList,
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.ranking = app
    }

    /**
     * VueApp - 틱택토 게임
     */
    async renderGame(selector: string) {
        const gameId = ref(0)
        const gameBoard = ref('.........')
        const isShowGame = this.isShowGame

        const onClickCell = (pos: number) => {
            // TODO: onClickCell
            console.log('onClickCell', pos)
        }

        const onClickQuit = () => {
            if (!confirm('quit?')) return

            const game = jQuery('#game')
            game.fadeIn()

            this.isShowGame.value = false
            this.isShowMenu.value = true
        }

        const onRefresh = async () => {
            const dataGameId = jQuery('#game').find('.onShowClick').data('game_id')
            gameId.value = dataGameId

            tickGameStatus()
        }

        /**
         * 게임판 업데이트
         */
        const tickGameStatus = () => {
            const isShowGame = this.isShowGame
            if (!isShowGame.value) return

            setTimeout(async () => {
                const resp = await this.model.getGameRoom(gameId.value) as GameRoomDto
                console.log(resp)

                gameBoard.value = resp.board

                tickGameStatus()
            }, 1000)
        }

        const app = createApp({
            setup() {
                return {
                    isShowGame,
                    gameBoard,
                    onClickCell,
                    onClickQuit,
                    onRefresh,
                }
            }
        })
        app.mount(selector)
        this.vueApp.game = app
    }

}
