<template>
  <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
  <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
  <ToolBar/>
  <Canvas/>
</template>

<script>
import Canvas from './components/Canvas.vue';
import HelloWorld from './components/HelloWorld.vue'
import ToolBar from './components/ToolBar.vue';

export default {
  name: 'App',
  components: {
    HelloWorld,
    ToolBar,
    Canvas
}
}
</script>

<style >
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  align-items: center;
  color: #2c3e50;
 
}
</style>
