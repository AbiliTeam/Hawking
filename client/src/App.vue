<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'app',
    components: {

    }
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Montserrat&display=swap');
  #app {
    font-family: 'Montserrat', Helvetica, Arial, sans-serif;
    color: #343E3D;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    background-color: #4CBA5E;
  }
  body {
    background-color: #4CBA5E;
  }
</style>