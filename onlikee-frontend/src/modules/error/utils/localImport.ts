import { parse, compileScript } from '@vue/compiler-sfc'
import * as ts from 'typescript'

export interface CompiledModule {
  code: string
  styles: string[]
  dependencies: string[]
}

export interface ModuleCache {
  [id: string]: CompiledModule
}

export function resolveLocalImport(
  importPath: string,
  fromPath: string,
  aliasBase = '@/',
  srcRoot = '/src/'
): string {
  // 1. 处理 @/ 别名
  if (importPath.startsWith(aliasBase)) {
    return srcRoot + importPath.slice(aliasBase.length)
  }
  // 2. 处理相对路径
  if (importPath.startsWith('.')) {
    return normalizePath(fromPath, importPath)
  }
  // 3. 其他情况直接返回
  return importPath
}

function normalizePath(from: string, rel: string): string {
  // 简单实现，假设 from 已为 /src/xxx/yyy.vue
  const base = from.replace(/\/[^/]+$/, '/')
  const full = base + rel
  // 处理 ../
  return full.replace(/\/[^/]+\/\.\./g, '')
}

export function createModuleCache(): ModuleCache {
  return Object.create(null)
}

// 递归编译主入口（占位，后续补全）
export async function compileModule(
  absPath: string,
  cache: ModuleCache,
  styleSet: Set<string>
): Promise<CompiledModule> {
  // TODO: 递归读取、编译 .vue/.ts，收集依赖和样式
  return { code: '', styles: [], dependencies: [] }
}

// SFC 解析（占位）
export function extractSFCParts(code: string) {
  const { descriptor } = parse(code)
  return {
    script: descriptor.script?.content || '',
    template: descriptor.template?.content || '',
    style: descriptor.styles.map(s => s.content).join('\n')
  }
}

// 样式注入（占位）
export function injectStylesToIframe(styles: Set<string>, iframe: HTMLIFrameElement) {
  // TODO: 合并样式并注入
}

export function clearModuleCache(cache: ModuleCache) {
  Object.keys(cache).forEach(k => delete cache[k])
}
