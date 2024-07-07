// import { marked } from 'marked'
import CommonUtil from '../util/common'

/**
 * 틱택토 모델
 */
export default class TicTactoeModel {
    private debug: boolean = false

    /**
     * 테스트 플래그 설정
     * @param debug 디버그 모드
     */
    setDebugMode(debug: boolean) {
        this.debug = debug
    }

    /**
     * 컨텐츠 가져오기
     * @param contentId 컨텐츠 ID
     * @returns html 변환된 텍스트
     */
    // async content(contentId: string): Promise<string | null> {
    //     const resp = await CommonUtil.readData(`/contents/home/${contentId}.md`, this.debug)
    //     if (resp == null) return null
    //     // return this.parse(resp)
    //     return CommonUtil.parseMarkdown(resp)
    // }

    /*
        async renderSignIn(selector: string) {
        async renderSignUp(selector: string) {
        async renderUserLost(selector: string) {
        async renderNewGame(selector: string) {
        async renderJoinGame(selector: string) {
        async renderMyPage(selector: string) {
        async renderRanking(selector: string) {
        async renderGame(selector: string) {
    */
}