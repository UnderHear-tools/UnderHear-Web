<template>
  <div class="html-editor-wrapper">
    <div
      ref="editorRef"
      class="html-editor"
    />
  </div>
</template>

<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { TextDocument } from 'monaco-editor/esm/external/vscode-languageserver-textdocument/lib/esm/main.js'
import { getLanguageService } from 'monaco-editor/esm/external/vscode-html-languageservice/lib/esm/htmlLanguageService.js'
import { onBeforeUnmount, onMounted, ref, shallowRef } from 'vue'

type HtmlEditorWithTyping = monaco.editor.IStandaloneCodeEditor & {
  onDidType: (listener: (text: string) => void) => monaco.IDisposable
}

const props = defineProps<{
  modelValue: string
}>()

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const htmlLanguageService = getLanguageService()
const editorRef = ref<HTMLElement | null>(null)
const editor = shallowRef<monaco.editor.IStandaloneCodeEditor | null>(null)

function disposeEditor() {
  const model = editor.value?.getModel()

  editor.value?.dispose()
  model?.dispose()
  editor.value = null
}

function initEditor() {
  if (!editorRef.value || editor.value) {
    return
  }

  editor.value = monaco.editor.create(editorRef.value, {
    value: props.modelValue,
    language: 'html',
    automaticLayout: true,
    fontSize: 14,
    lineNumbersMinChars: 3,
    minimap: { enabled: false },
    scrollBeyondLastLine: false,
    tabSize: 2
  })

  editor.value.onDidChangeModelContent(() => {
    emit('update:modelValue', editor.value?.getValue() ?? '')
  })

  const editorWithTyping = editor.value as HtmlEditorWithTyping

  editorWithTyping.onDidType((text: string) => {
    if (text !== '>' && text !== '/') {
      return
    }

    const monacoEditor = editor.value
    const model = monacoEditor?.getModel()
    const position = monacoEditor?.getPosition()

    if (!monacoEditor || !model || !position) {
      return
    }

    const document = TextDocument.create(
      model.uri.toString(),
      model.getLanguageId(),
      model.getVersionId(),
      model.getValue()
    )
    const htmlDocument = htmlLanguageService.parseHTMLDocument(document)
    const snippet = htmlLanguageService.doTagComplete(document, {
      line: position.lineNumber - 1,
      character: position.column - 1
    }, htmlDocument)

    if (!snippet) {
      return
    }

    const snippetController = monacoEditor.getContribution('snippetController2') as {
      insert: (template: string) => void
    } | null

    snippetController?.insert(snippet)
  })
}

onMounted(() => {
  initEditor()
})

onBeforeUnmount(() => {
  disposeEditor()
})
</script>

<style scoped>
.html-editor-wrapper {
  overflow: hidden;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
}

.html-editor-wrapper:focus-within {
  outline: 2px solid var(--borderColor-accent-emphasis);
  outline-offset: -1px;
}

.html-editor {
  width: 100%;
  height: 360px;
}
</style>
