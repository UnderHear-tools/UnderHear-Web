<template>
  <div ref="editorContainer" style="width: 100%; height: 500px;"></div>
</template>

<script>
import * as monaco from 'monaco-editor';

export default {
  name: 'MonacoEditor',
  mounted() {
    this.createEditor();
  },
  methods: {
    createEditor() {
      this.editor = monaco.editor.create(this.$refs.editorContainer, {
        value: "// 欢迎使用 Monaco 编辑器\nconsole.log('Hello, Monaco!');",
        language: 'javascript',
        theme: 'vs-light', // 你可以更改主题，例如 'vs-light' 或 'hc-black'
        automaticLayout: true, // 自动调整布局
      });
    }
  },
  beforeDestroy() {
    if (this.editor) {
      this.editor.dispose();
    }
  }
};
</script>

<style scoped>
/* 你可以根据需要调整 Monaco 编辑器的样式 */
</style>
