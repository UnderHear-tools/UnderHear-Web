import path from 'node:path'
import tailwindcss from '@tailwindcss/vite'
import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'
// import { visualizer } from 'rollup-plugin-visualizer';

export default defineConfig({
  plugins: [
    vue(), 
    tailwindcss(), 
    // visualizer({
    //   open: false,        // 打包完成后，自动在浏览器中打开报告
    //   filename: 'packingStatistics.html', // 生成的分析报告文件名
    //   gzipSize: true,    // 显示文件经过Gzip压缩后的大小
    //   brotliSize: true,  // 显示文件经过Brotli压缩后的大小
    // }),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    allowedHosts: ['www.onlikee.cn', 'onlikee.cn'],
    host: '0.0.0.0',
    port: 5173,
  },
  build: {
    sourcemap: false,
    reportCompressedSize: false,
    minify: 'esbuild',
    target: 'es2018',
    rollupOptions: {
      output: {
        manualChunks: {
          monaco: ['monaco-editor'],
        },
      },
    },
  },
})
