<template>
  <div class="playground">
    <VueMonacoEditor
      v-model:value="source"
      language="html"
      :path="PLAYGROUND_MODEL_PATH"
      class="pane"
      :options="editorOptions"
    />
    <iframe
      ref="previewFrame"
      class="pane preview"
      :srcdoc="previewShell"
      sandbox="allow-scripts allow-same-origin"
      title="Vue playground preview"
      @load="handlePreviewLoad"
    />
  </div>
</template>

<script setup lang="ts">
import * as VueRuntime from 'vue'
import { onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { loader, VueMonacoEditor } from '@guolao/vue-monaco-editor'
import { compileScript, parse } from '@vue/compiler-sfc'
import * as ts from 'typescript'
import tooltipSfcSource from '@/components/z-ui/Tooltip/Tooltip.vue?raw'
import dividerSfcSource from '@/components/z-ui/Divider/Divider.vue?raw'
import { Tooltip } from '@/components/z-ui/Tooltip'
import { Divider } from '@/components/z-ui/Divider'
import { Admonition } from '@/components/z-ui/Admonition'
import { BoldIcon, ItalicIcon, CodeIcon, LinkIcon, FileAddedIcon, SearchIcon } from '@/components/octicons-vue3'

const PREVIEW_UPDATE_DEBOUNCE = 240
const PLAYGROUND_MODEL_PATH = 'file:///src/modules/error/views/PlaygroundInput.vue'
const PLAYGROUND_TS_EXTRA_LIB_PATH = 'file:///src/modules/error/views/playground.monaco.d.ts'
const PLAYGROUND_JS_EXTRA_LIB_PATH = 'file:///src/modules/error/views/playground.monaco.js.d.ts'
const PLAYGROUND_IMPORTER_KEY = '__onlikeePlaygroundImport' as const
const PLAYGROUND_MODULE_DECLARATIONS = `
declare module '@/components/z-ui/Tooltip' {
  export const Tooltip: any
}

declare module '@/components/z-ui/Divider' {
  export const Divider: any
}

declare module '@/components/z-ui/Admonition' {
  export const Admonition: any
}

declare module '@/components/octicons-vue3' {
  export const BoldIcon: any
  export const ItalicIcon: any
  export const CodeIcon: any
  export const LinkIcon: any
  export const FileAddedIcon: any
  export const SearchIcon: any
}
`.trim()

type PlaygroundRuntimeModule = {
  exports: Record<string, unknown>
  styles: string
}

type PlaygroundHostWindow = Window & {
  __onlikeePlaygroundImport?: (specifier: string) => Promise<Record<string, unknown>>
}

const PLAYGROUND_RUNTIME_MODULES: Record<string, PlaygroundRuntimeModule> = {
  vue: {
    exports: VueRuntime,
    styles: '',
  },
  '@/components/z-ui/Tooltip': {
    exports: { Tooltip },
    styles: extractSfcStyles(tooltipSfcSource),
  },
  '@/components/z-ui/Divider': {
    exports: { Divider },
    styles: extractSfcStyles(dividerSfcSource),
  },
  '@/components/octicons-vue3': {
    exports: {
      BoldIcon,
      ItalicIcon,
      CodeIcon,
      LinkIcon,
      FileAddedIcon,
      SearchIcon,
    },
    styles: '',
  },
}

const PLAYGROUND_RUNTIME_IMPORT_SPECIFIERS = new Set(Object.keys(PLAYGROUND_RUNTIME_MODULES))

type PreviewMessage = {
  type: 'playground:update'
  moduleCode: string
  styles: string
  error: string | null
}

type MonacoDefaults = {
  addExtraLib: (content: string, filePath?: string) => { dispose: () => void }
  setCompilerOptions: (options: Record<string, unknown>) => void
}

type MonacoEditorApi = {
  languages: {
    typescript: {
      javascriptDefaults: MonacoDefaults
      typescriptDefaults: MonacoDefaults
      ModuleKind: {
        ESNext: number
      }
      ModuleResolutionKind: {
        NodeJs: number
      }
      ScriptTarget: {
        ES2020: number
      }
    }
  }
}

const previewFrame = ref<HTMLIFrameElement | null>(null)
const previewLoaded = ref(false)
const previewShell = createPreviewShell()
let updateTimer: number | undefined
let playgroundTypeDisposables: Array<{ dispose: () => void }> = []

const source = ref(`<template>

</template>

<script setup lang="ts">
123
<\/script>

<style>

</style>`)

const editorOptions = {
  automaticLayout: true,
  fontSize: 14,
  minimap: { enabled: false },
  scrollBeyondLastLine: false,
  tabSize: 2,
}

const previewMessage = ref(buildPreviewMessage(source.value))

onMounted(async () => {
  installPreviewImportBridge()
  const monaco = await loader.init()
  configurePlaygroundTypeSupport(monaco)
})

watch(source, (nextSource) => {
  if (updateTimer !== undefined) {
    window.clearTimeout(updateTimer)
  }

  updateTimer = window.setTimeout(() => {
    previewMessage.value = buildPreviewMessage(nextSource)
    postPreviewMessage()
  }, PREVIEW_UPDATE_DEBOUNCE)
})

onBeforeUnmount(() => {
  if (updateTimer !== undefined) {
    window.clearTimeout(updateTimer)
  }

  disposePlaygroundTypeSupport()
  uninstallPreviewImportBridge()
})

function installPreviewImportBridge() {
  const hostWindow = window as PlaygroundHostWindow
  hostWindow[PLAYGROUND_IMPORTER_KEY] = async (specifier) => {
    const runtimeModule = PLAYGROUND_RUNTIME_MODULES[specifier]

    if (!runtimeModule) {
      throw new Error(`playground 暂不支持导入模块：${specifier}`)
    }

    return runtimeModule.exports
  }
}

function uninstallPreviewImportBridge() {
  delete (window as PlaygroundHostWindow)[PLAYGROUND_IMPORTER_KEY]
}

function configurePlaygroundTypeSupport(monaco: MonacoEditorApi) {
  const compilerOptions = {
    allowNonTsExtensions: true,
    baseUrl: 'file:///',
    module: monaco.languages.typescript.ModuleKind.ESNext,
    moduleResolution: monaco.languages.typescript.ModuleResolutionKind.NodeJs,
    paths: {
      '@/*': ['src/*'],
    },
    strict: true,
    target: monaco.languages.typescript.ScriptTarget.ES2020,
  }

  monaco.languages.typescript.typescriptDefaults.setCompilerOptions(compilerOptions)
  monaco.languages.typescript.javascriptDefaults.setCompilerOptions(compilerOptions)

  disposePlaygroundTypeSupport()
  playgroundTypeDisposables = [
    monaco.languages.typescript.typescriptDefaults.addExtraLib(
      PLAYGROUND_MODULE_DECLARATIONS,
      PLAYGROUND_TS_EXTRA_LIB_PATH,
    ),
    monaco.languages.typescript.javascriptDefaults.addExtraLib(
      PLAYGROUND_MODULE_DECLARATIONS,
      PLAYGROUND_JS_EXTRA_LIB_PATH,
    ),
  ]
}

function disposePlaygroundTypeSupport() {
  for (const disposable of playgroundTypeDisposables) {
    disposable.dispose()
  }

  playgroundTypeDisposables = []
}

function handlePreviewLoad() {
  previewLoaded.value = true
  postPreviewMessage()
}

function postPreviewMessage() {
  if (!previewLoaded.value) {
    return
  }

  previewFrame.value?.contentWindow?.postMessage({ ...previewMessage.value }, '*')
}

function buildPreviewMessage(source: string): PreviewMessage {
  try {
    const normalizedSource = normalizeSource(source)
    const { descriptor } = parse(normalizedSource, { filename: 'Playground.vue' })
    const compiledScript = compileScript(descriptor, {
      id: 'playground',
      inlineTemplate: true,
      genDefaultAs: '__PlaygroundComponent__',
    })
    const runtimeModule = createModuleCode(compiledScript.content)

    return {
      type: 'playground:update',
      moduleCode: runtimeModule.code,
      styles: [
        descriptor.styles.map((block) => block.content).join('\n\n'),
        collectRuntimeModuleStyles(runtimeModule.usedSpecifiers),
      ].filter(Boolean).join('\n\n'),
      error: null,
    }
  } catch (error) {
    return {
      type: 'playground:update',
      moduleCode: '',
      styles: '',
      error: error instanceof Error ? error.message : String(error),
    }
  }
}

function createModuleCode(script: string) {
  const rewrittenImports = rewriteRuntimeImports(script)

  return {
    code: ts.transpileModule(
    [
      rewrittenImports.code,
      'export default __PlaygroundComponent__',
    ].join('\n\n'),
    {
      compilerOptions: {
        module: ts.ModuleKind.ESNext,
        target: ts.ScriptTarget.ES2022,
      },
    },
    ).outputText,
    usedSpecifiers: rewrittenImports.usedSpecifiers,
  }
}

function normalizeSource(source: string) {
  const trimmedSource = source.trim()
  const hasTemplateBlock = trimmedSource.includes('<template')
  const hasScriptBlock = trimmedSource.includes('<script')
  const hasStyleBlock = trimmedSource.includes('<style')

  if (!hasTemplateBlock && !hasScriptBlock && !hasStyleBlock) {
    return `<template>\n${trimmedSource}\n</template>\n\n<script setup lang="ts">\n<\\/script>`
  }

  if (hasScriptBlock) {
    return trimmedSource
  }

  return `${trimmedSource}\n\n<script setup lang="ts">\n<\\/script>`
}

function rewriteRuntimeImports(script: string) {
  const sourceFile = ts.createSourceFile('playground-runtime.ts', script, ts.ScriptTarget.ESNext, true, ts.ScriptKind.TS)
  const printer = ts.createPrinter()
  const usedSpecifiers = new Set<string>()
  const nextStatements: ts.Statement[] = []

  for (const statement of sourceFile.statements) {
    if (!ts.isImportDeclaration(statement) || !ts.isStringLiteral(statement.moduleSpecifier)) {
      nextStatements.push(statement)
      continue
    }

    const specifier = statement.moduleSpecifier.text

    if (!PLAYGROUND_RUNTIME_IMPORT_SPECIFIERS.has(specifier)) {
      if (specifier.startsWith('@/')) {
        throw new Error(`playground 暂不支持导入模块：${specifier}`)
      }

      nextStatements.push(statement)
      continue
    }

    usedSpecifiers.add(specifier)
    nextStatements.push(...createRuntimeImportStatements(statement.importClause, specifier))
  }

  return {
    code: printer.printFile(ts.factory.updateSourceFile(sourceFile, nextStatements)),
    usedSpecifiers: Array.from(usedSpecifiers),
  }
}

function createRuntimeImportStatements(importClause: ts.ImportClause | undefined, specifier: string) {
  if (!importClause) {
    return [ts.factory.createExpressionStatement(createRuntimeImportCall(specifier))]
  }

  const statements: ts.Statement[] = []
  const needsModuleBinding = Boolean(importClause.name && importClause.namedBindings)
  const moduleBinding = needsModuleBinding ? ts.factory.createUniqueName('__playgroundModule') : null
  const moduleExpression = moduleBinding ?? createRuntimeImportCall(specifier)

  if (moduleBinding) {
    statements.push(
      ts.factory.createVariableStatement(
        undefined,
        ts.factory.createVariableDeclarationList(
          [ts.factory.createVariableDeclaration(moduleBinding, undefined, undefined, createRuntimeImportCall(specifier))],
          ts.NodeFlags.Const,
        ),
      ),
    )
  }

  if (importClause.name) {
    statements.push(
      ts.factory.createVariableStatement(
        undefined,
        ts.factory.createVariableDeclarationList(
          [
            ts.factory.createVariableDeclaration(
              ts.factory.createObjectBindingPattern([
                ts.factory.createBindingElement(undefined, ts.factory.createIdentifier('default'), importClause.name, undefined),
              ]),
              undefined,
              undefined,
              moduleExpression,
            ),
          ],
          ts.NodeFlags.Const,
        ),
      ),
    )
  }

  if (!importClause.namedBindings) {
    return statements
  }

  if (ts.isNamespaceImport(importClause.namedBindings)) {
    statements.push(
      ts.factory.createVariableStatement(
        undefined,
        ts.factory.createVariableDeclarationList(
          [ts.factory.createVariableDeclaration(importClause.namedBindings.name, undefined, undefined, moduleExpression)],
          ts.NodeFlags.Const,
        ),
      ),
    )

    return statements
  }

  statements.push(
    ts.factory.createVariableStatement(
      undefined,
      ts.factory.createVariableDeclarationList(
        [
          ts.factory.createVariableDeclaration(
            ts.factory.createObjectBindingPattern(
              importClause.namedBindings.elements.map((element) => ts.factory.createBindingElement(
                undefined,
                element.propertyName ?? undefined,
                element.name,
                undefined,
              )),
            ),
            undefined,
            undefined,
            moduleExpression,
          ),
        ],
        ts.NodeFlags.Const,
      ),
    ),
  )

  return statements
}

function createRuntimeImportCall(specifier: string) {
  return ts.factory.createAwaitExpression(
    ts.factory.createCallExpression(
      ts.factory.createPropertyAccessExpression(ts.factory.createIdentifier('globalThis'), '__playgroundImport'),
      undefined,
      [ts.factory.createStringLiteral(specifier)],
    ),
  )
}

function collectRuntimeModuleStyles(specifiers: string[]) {
  return specifiers
    .map((specifier) => PLAYGROUND_RUNTIME_MODULES[specifier]?.styles ?? '')
    .filter(Boolean)
    .join('\n\n')
}

function extractSfcStyles(source: string) {
  return parse(source).descriptor.styles.map((block) => block.content).join('\n\n')
}

function createPreviewShell() {
  return `<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <style>
    :root {
      font-family: ui-sans-serif, system-ui, sans-serif;
      color: #1f2328;
      background: #ffffff;
    }

    * {
      box-sizing: border-box;
    }

    body {
      margin: 0;
      padding: 16px;
    }

    #error {
      margin: 0;
      padding: 16px;
      border-radius: 6px;
      font: 14px/1.5 ui-monospace, SFMono-Regular, Consolas, monospace;
      color: #cf222e;
      background: #fff8f8;
      white-space: pre-wrap;
    }
  <\/style>
  <style id="playground-styles"><\/style>
</head>
<body>
  <div id="app"></div>
  <pre id="error" hidden></pre>
  <script type="module">
    globalThis.__playgroundImport = (specifier) => window.parent.${PLAYGROUND_IMPORTER_KEY}(specifier)

    const { createApp } = await globalThis.__playgroundImport('vue')

    const appRoot = document.getElementById('app')
    const errorRoot = document.getElementById('error')
    const styleRoot = document.getElementById('playground-styles')
    let currentApp = null
    let currentModuleUrl = null
    let renderToken = 0

    function destroyPreview() {
      if (currentApp) {
        currentApp.unmount()
        currentApp = null
      }

      appRoot.innerHTML = ''

      if (currentModuleUrl) {
        URL.revokeObjectURL(currentModuleUrl)
        currentModuleUrl = null
      }
    }

    function showError(message) {
      errorRoot.hidden = false
      errorRoot.textContent = message
    }

    function clearError() {
      errorRoot.hidden = true
      errorRoot.textContent = ''
    }

    window.addEventListener('message', async (event) => {
      const payload = event.data

      if (!payload || payload.type !== 'playground:update') {
        return
      }

      const nextToken = ++renderToken
      destroyPreview()
      styleRoot.textContent = payload.styles ?? ''

      if (payload.error) {
        showError(payload.error)
        return
      }

      clearError()

      try {
        const moduleUrl = URL.createObjectURL(new Blob([payload.moduleCode], { type: 'text/javascript' }))
        currentModuleUrl = moduleUrl
        const module = await import(moduleUrl)

        if (nextToken !== renderToken) {
          URL.revokeObjectURL(moduleUrl)
          if (currentModuleUrl === moduleUrl) {
            currentModuleUrl = null
          }
          return
        }

        currentApp = createApp(module.default)
        currentApp.mount(appRoot)
      } catch (error) {
        if (currentModuleUrl) {
          URL.revokeObjectURL(currentModuleUrl)
          currentModuleUrl = null
        }

        if (nextToken !== renderToken) {
          return
        }

        showError(error instanceof Error ? error.message : String(error))
      }
    })
  <\/script>
</body>
</html>`
}

</script>

<style scoped>
.playground {
  display: flex;
  height: calc(100vh - var(--header-height));
  gap: 8px;
  padding: 8px;
  box-sizing: border-box;
}

.pane {
  flex: 1;
  min-width: 0;
  height: 100%;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  overflow: hidden;
}

.preview {
  background: #ffffff;
}
</style>

