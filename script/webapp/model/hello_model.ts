export default class HelloModel {
    /**
     * 접두어
     */
    private prefix: string = 'Hello'

    /**
     * 연결어
     */
    private splitter: string = ', '

    /**
     * 인사하기
     * @param name 이름
     * @returns 인사말
     */
    hello(name: string = ''): string {
        if (name.length > 0)
            return `${this.prefix}${this.splitter}${name}`
        else
            return `${this.prefix}`
    }

    /**
     * 접두어 설정
     * @param prefix 접두어
     */
    setPrefix(prefix: string) {
        this.prefix = prefix
    }

    /**
     * 연결어 설정
     * @param splitter 연결어
     */
    setSplitter(splitter: string) {
        this.splitter = splitter
    }
}
