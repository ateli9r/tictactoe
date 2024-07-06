import HomeApp from './home_app'
import CounterApp from './modal/counter_app'
import HelloApp from './modal/hello_app'
import TicTacToeApp from './tictactoe_app'

export default class App {
    /**
     * 싱글턴 인스턴스
     */
    private static instance: App

    /**
     * 마지막 앱
     */
    private ctxApp: any

    /**
     * 라우팅 맵
     */
    private appRoute: any = {
        'counter': new CounterApp(),
        'hello': new HelloApp(),
    }

    /**
     * 인스턴스 가져오기
     */
    static getInstance() {
        if (!App.instance) {
            App.instance = new App()
            App.instance.initialize()
        }
        return App.instance
    }

    /**
     * 초기화
     */
    private initialize() {
        // 마우스 우클릭 금지
        // document.oncontextmenu = () => { return false }

        // 홈 컨텐츠 불러오기
        this.loadHome()
    }

    /**
     * 홈 컨텐츠 불러오기
     */
    private loadHome() {
        const ttt = new TicTacToeApp()
        ttt.renderHeader('#header')
        ttt.renderSignIn('#sign_in')
        ttt.renderSignUp('#sign_up')
        ttt.renderNewGame('#new_game')
        ttt.renderJoinGame('#join_game')
        ttt.renderMyPage('#my_page')
        ttt.renderRanking('#list_rank')
        ttt.renderGame('#game')
    }

    /**
     * modal에 앱 불러오기
     * @param appId appRoute에 지정된 key
     */
    public loadApp(appId: string) {
        if (this.ctxApp != null) {
            this.ctxApp.app.unmount()
            this.ctxApp = null
        }

        const ctxApp = this.appRoute[appId]
        if (ctxApp != null) {
            ctxApp.create('.modal-body')
            this.ctxApp = ctxApp
        }
    }
}