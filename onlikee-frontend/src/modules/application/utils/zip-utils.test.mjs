import assert from 'node:assert/strict'
import test from 'node:test'

import { strFromU8, unzipSync } from 'fflate'

import { createFolderZip, createHtmlZip } from './zip-utils.ts'

test('HTML source is preserved in a root index.html entry', async () => {
  const htmlSource = '  <!doctype html>\r\n<html lang="zh-CN">中文内容</html>\n'

  const archive = await createHtmlZip(htmlSource)
  const entries = unzipSync(new Uint8Array(await archive.arrayBuffer()))

  assert.equal(archive.name, 'dist.zip')
  assert.equal(archive.type, 'application/zip')
  assert.deepEqual(Object.keys(entries), ['index.html'])
  assert.equal(strFromU8(entries['index.html']), htmlSource)
})

test('folder archive preserves nested paths, same-name files and binary contents', async () => {
  const files = [
    { file: new File(['<html>中文内容</html>'], 'index.html'), relativePath: '构建目录/index.html' },
    { file: new File([new Uint8Array([0, 255, 128, 13, 10])], 'image.bin'), relativePath: '构建目录/assets/image.bin' },
    { file: new File(['first'], 'same.txt'), relativePath: '构建目录/a/same.txt' },
    { file: new File(['second'], 'same.txt'), relativePath: '构建目录/b/same.txt' },
    { file: new File([], 'empty.txt'), relativePath: '构建目录/assets/empty.txt' }
  ]

  const archive = await createFolderZip(files)
  const entries = unzipSync(new Uint8Array(await archive.arrayBuffer()))

  assert.equal(archive.name, 'dist.zip')
  assert.equal(archive.type, 'application/zip')
  assert.deepEqual(Object.keys(entries).sort(), files.map(item => item.relativePath).sort())
  for (const { file, relativePath } of files) {
    assert.deepEqual(entries[relativePath], new Uint8Array(await file.arrayBuffer()))
  }
})

test('folder archive rejects file read failures instead of returning a partial ZIP', async () => {
  const error = new Error('File read failed')
  const unreadableFile = new File(['unreadable'], 'broken.txt')
  Object.defineProperty(unreadableFile, 'arrayBuffer', {
    value: async () => { throw error }
  })

  await assert.rejects(createFolderZip([
    { file: new File(['ok'], 'index.html'), relativePath: 'dist/index.html' },
    { file: unreadableFile, relativePath: 'dist/broken.txt' }
  ]), error)
})
