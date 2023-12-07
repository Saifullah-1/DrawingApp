import { createApp } from 'vue'
import mitt from 'mitt'
import App from './App.vue'
import VueKonva from 'vue-konva';

const emitter = mitt()
let Paint=createApp(App)

Paint.config.globalProperties.emitter = emitter
Paint.mount('#app')
Paint.use(VueKonva)
