const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../main/resources/static', // 빌드경로
  devServer: {
    allowedHosts: 'all',
    port: 8081,  // 프론트엔드 포트 설정
    proxy: {
      '/api': {
        target: 'http://localhost:8080',  // 백엔드 서버 URL
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
        secure: false
      }
    }
  }
})
