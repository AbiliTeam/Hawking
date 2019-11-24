<template>
    <div id="remote-view">
        <h4>Tap and hold the screen to record a gesture. Release to stop</h4>
        <span>touches: {{ touches }}</span>
        <br/>
        <span>active: {{ active }}</span>
        <div>props: {{ this.pin }}</div>
        <div>interval: {{ this.timeInterval }}</div>
        <div v-if="this.connectedToHost">Remote connected!</div>
        <div>data: {{ this.output }}</div>
        <canvas id="canvas" width="600" height="600">
            Your browser does not support canvas element.
        </canvas>
    </div>
</template>

<script>
import io from 'socket.io-client'
import axios from 'axios'

export default {
  name: "Remote",
  components: {
      
  },
  mounted: function () {
      this.initializeSocket();
      this.initializeListeners();
      /* eslint-disable no-console */
      console.log(this.$props.hostIP)
      console.log(this.$props.pin)
      /* eslint-enable no-console */
  },
  props: {
      pin: Number
  },
  data () {
      return {
          socket: null,
          active: false,
          touches: 0,
          connectedToHost: false,
          saveEndOrientation: false,
          timeInterval: 0,
          intervalStart: null,
          motionEvents: [],
          startOrientation: null,
          endOrientation: null,
          touches: 0,
          output: null
      }
  },
  methods: {
      initializeSocket: function () {
          this.socket = io(process.env.ROUTING_SERVER + '/socket')
          //io('http://localhost:8000')
          this.socket.on('connect', () => {
              this.socket.emit('connectRemote', {
                  pin: this.pin
              })

              this.socket.on('successfulPairing', function () {
                  /* eslint-disable no-console */
                  console.log("SUCCESSFUL PAIRING")
                  console.log(this.connectedToHost)
                  this.connectedToHost = true;
                  console.log(this.connectedToHost)
                  /* eslint-ENABLE no-console */
              })

            this.socket.on('processedGesture', function () {

            })
          })
      },
      initializeListeners: function () {
          var el = document.getElementsByTagName("canvas")[0];
          el.addEventListener("touchstart", this.handleStart, false);
          el.addEventListener("touchend", this.handleEnd, false);
          //el.addEventListener("touchcancel", this.handleCancel, false);
          //el.addEventListener("touchmove", this.handleMove, false);
          window.addEventListener("deviceorientation", this.handleOrientation, true);
          window.addEventListener("devicemotion", this.handleMotion, true);
      },
      handleStart: function (event) {
          event.preventDefault()
          this.active = true
          this.touches += 1
      },
      handleEnd: function (event) {
          event.preventDefault();
          this.active = false
          this.timeInterval = Date.now() - this.intervalStart
          /* eslint-disable no-console */
          this.output = {
              'timeInterval': this.timeInterval,
              'startOrientation': this.startOrientation,
              'endOrientation': this.endOrientation,
              'motionEvents': this.motionEvents
          }
          console.log(this.output)
          /* eslint-enable no-console */
          this.socket.emit('newGesture', {
              pin: this.pin,
              timeInterval: this.timeInterval,
              startOrientation: this.startOrientation,
              endOrientation: this.endOrientation,
              motionEvents: this.motionEvents
          })
          
          axios.post(this.host + '/gesturedata/' + this.pin, {
              'timeInterval': this.timeInterval,
              'startOrientation': this.startOrientation,
              'endOrientation': this.endOrientation,
              'motionEvents': this.motionEvents
          })
          .then((response) => {
              if (response.status == 200) {
                  this.intervalStart = 0
                  this.startOrientation = null
                  this.endOrientation = null
                  this.motionEvents = []
              }
          })
          .catch((error) => {
              /* eslint-disable no-console */
              console.log(error)
              /* eslint-enable no-console */
          })
      },
      /*
      handleCancel: function (event) {

      },
      handleMove: function (event) {
          
      }
      */
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
