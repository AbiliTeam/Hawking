<template>
    <div id="test">
        <div v-if="connected">Connected to server.</div>
        <button id="connectButton" @click="connect()">connect</button>
        <div>{{message}}</div>
    </div>
</template>

<script>
import io from 'socket.io-client'

export default {
  name: "Test",
  components: {
      
  },
  data () {
      return {
          socket: io('http://localhost:8000'),
          connected: false,
          message: ""
      }
  },
  methods: {
      connect() {
          this.socket.emit('TEST', {
              user: "new user",
              message: "hi there!"
          })
      }
  },
  mounted() {
      this.socket.on('GOOD REPLY', (data) => {
          this.message = data
      })
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
