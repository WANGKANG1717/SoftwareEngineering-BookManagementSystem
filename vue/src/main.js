import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import '@/assets/css/global.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import zhCn from 'element-plus/es/locale/lang/zh-cn'

import * as echarts from 'echarts'
//---------------
//v-md-editor
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// highlightjs
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
    Hljs: hljs,
});
//----------------

const app=createApp(App)
    .use(store)
    .use(router)
    .use(VMdPreview)
    .use(ElementPlus, {locale: zhCn, size: "small"})
    .mount('#app')
app.echarts = echarts
//v-md-editor

//动态切换标题
router.beforeEach((to, from, next) => {
    window.document.title = (to.meta.title == undefined)?'书城后台管理系统':to.meta.title;
    next();
})