declare module 'monaco-editor/esm/external/vscode-languageserver-textdocument/lib/esm/main.js' {
  export const TextDocument: {
    create(uri: string, languageId: string, version: number, content: string): unknown
  }
}

declare module 'monaco-editor/esm/external/vscode-html-languageservice/lib/esm/htmlLanguageService.js' {
  export function getLanguageService(): {
    parseHTMLDocument(document: unknown): unknown
    doTagComplete(
      document: unknown,
      position: { line: number, character: number },
      htmlDocument: unknown
    ): string | null
  }
}
