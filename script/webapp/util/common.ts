import { marked } from 'marked'

export default class CommonUtil {
    /**
     * 파일 읽기
     * @param path 파일 경로
     * @param debug 디버그 모드 여부
     * @returns 파일내용 텍스트
     */
    static async readData(path: string, debug: boolean = false): Promise<string | null> {
        let ret = null
        try {
            if (!debug) { // fetch를 통한 http 요청 (정상 요청)
                const resp = await fetch(path)
                if (!resp.ok) return null
                ret = await resp.text()
            } else { // node 파일 읽기 (디버그 모드)
                const fs = require('fs')
                const filePath = `../../docs/${path}`.replace(/\/\//, '/')
                if (fs.existsSync(filePath)) {
                    const resp = fs.readFileSync(filePath)
                    ret = resp.toString('utf-8')
                }
            }
        } catch (e) {
            // ignore errors
        }
        return ret
    }

    /**
     * 템플릿 HTML 가져오기
     * @param fileName 템플릿 파일명
     * @param debug 디버그 모드 여부
     * @returns 템플릿내용 텍스트
     */
    static async templateHTML(fileName: string, debug: boolean = false): Promise<string | undefined>  {
        return await CommonUtil.readData(`/html/${fileName}.html`, debug) ?? undefined
    }

    /**
     * markdown -> html 변환
     * @param text markdown 텍스트
     * @returns html 텍스트
     */
    static parseMarkdown(text: string): string {
        const resp = marked.parse(text) as string
        return resp.substring(0, resp.length-1)
    }

    /**
     * yaml -> json 변환
     * @param text yaml 텍스트
     * @returns json 객체
     */
    static parseYAML(text: string): string {
        const YAML = require('yamljs');
        return YAML.parse(text)
    }
}