<template>
    <div id="remote-view">
        <h4>Tap and hold the screen to record a gesture. Release to stop</h4>
        <span>touches: {{ touches }}</span>
        <br/>
        <span>active: {{ active }}</span>
        <div>props: {{ this.host }}</div>
        <div>interval: {{ this.timeInterval }}</div>
        <div>data: {{ this.output }}</div>
        <canvas id="canvas" width="600" height="600">
            Your browser does not support canvas element.
        </canvas>
    </div>
</template>

<script>

import axios from 'axios'

export default {
  name: "Remote",
  components: {
      
  },
  mounted: function () {
      this.initializeListeners();
  },
  props: ['host', 'pin'],
  data () {
      return {
          active: false,
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
      initializeListeners: function () {
          var el = document.getElementsByTagName("canvas")[0];
          el.addEventListener("touchstart", this.handleStart, false);
          el.addEventListener("touchend", this.handleEnd, false);
          //el.addEventListener("touchcancel", this.handleCancel, false);
          //el.addEventListener("touchmove", this.handleMove, false);
          window.addEventListener("deviceorientation", this.handleOrientation, true);
          window.addEventListener("devicemotion", this.handleMotion, true);
          //console.log("initialized.");
      },
      handleStart: function (event) {
          event.preventDefault()
          this.active = true
          this.intervalStart = Date.now()
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
    handleOrientation(event) {
        if (this.active && this.startOrientation == null) {
            this.startOrientation = {
                'alpha': event.alpha,
                'beta': event.beta,
                'gamma': event.gamma
            }
        }
        if (!this.active && this.startOrientation != null && this.endOrientation == null) {
            this.endOrientation = {
                'alpha': event.alpha,
                'beta': event.beta,
                'gamma': event.gamma
            }
        }
    },

    handleMotion(event) {
        if (this.active) {
            this.motionEvents.push({
                'acceleration': {
                    'x': event.acceleration.x,
                    'y': event.acceleration.y,
                    'z': event.acceleration.z
                },
                'rotationRate': {
                    'alpha': event.rotationRate.alpha,
                    'beta': event.rotationRate.beta,
                    'gamma': event.rotationRate.gamma
                }
            })
        }
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
