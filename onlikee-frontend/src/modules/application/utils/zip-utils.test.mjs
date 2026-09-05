import assert from 'node:assert/strict'
import test from 'node:test'

import { strFromU8, unzipSync } from 'fflate'

import { createHtmlZip } from './zip-utils.ts'

test('HTML source is preserved in a root index.html entry', async () => {
  const htmlSource = '  <!doctype html>\r\n<html lang="zh-CN">中文内容</html>\n'

  const archive = await createHtmlZip(htmlSource)
  const entries = unzipSync(new Uint8Array(await archive.arrayBuffer()))

  assert.equal(archive.name, 'dist.zip')
  assert.equal(archive.type, 'application/zip')
  assert.deepEqual(Object.keys(entries), ['index.html'])
  assert.equal(strFromU8(entries['index.html']), htmlSource)
})
