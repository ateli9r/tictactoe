import { describe, expect, test } from '@jest/globals'
import CommonUtil from '../util/common'

describe('CommonUtil 테스트', () => {
    test('templateHTML', async () => {
        const html = await CommonUtil.templateHTML('_test', true)
        expect(html != null).toBe(true)
        expect(html!.length > 0).toBe(true)
        expect(html!.indexOf('</div>') > 0).toBe(true)
        expect(html!.indexOf('_test.html') > 0).toBe(true)
    })

    test('readData', async () => {
        const resp1 = await CommonUtil.readData('/contents/home/dummy.md', true)
        expect(resp1).toBe(null)

        const resp2 = await CommonUtil.readData('/contents/home/story.md', true)
        expect(resp2 != null).toBe(true)
        expect(resp2!.length > 0).toBe(true)
    })

    test('parseMarkdown', () => {
        // https://marked.js.org
        expect(CommonUtil.parseMarkdown('# title')).toBe('<h1>title</h1>')
        expect(CommonUtil.parseMarkdown('*bold text*')).toBe('<p><em>bold text</em></p>')
        expect(CommonUtil.parseMarkdown('**bold text**')).toBe('<p><strong>bold text</strong></p>')
        expect(CommonUtil.parseMarkdown('- item')).toBe('<ul>\n<li>item</li>\n</ul>')
    })

    test('parseYAML', async () => {
        const path = '/contents/home/_test.yml'
        const data = await CommonUtil.readData(path, true)
        expect(data != null).toBe(true)

        const doc = CommonUtil.parseYAML(data!) as any
        expect(doc.text).toBe('text')
        expect(doc.number).toBe(1234)
        expect(doc.items.length > 0).toBe(true)
    })
})
