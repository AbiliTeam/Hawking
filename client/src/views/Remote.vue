<template>
    <div id="remote-view">
        <h4>Tap and hold the screen to record a gesture. Release to stop</h4>
        <span>touches: {{ touches }}</span>
        <br/>
        <span>active: {{ active }}</span>
        <div>props: {{ this.pin }}</div>
        <canvas id="canvas" width="600" height="600">
            Your browser does not support canvas element.
        </canvas>
    </div>
</template>

<script>
export default {
  name: "Remote",
  components: {
      
  },
  mounted: function () {
      this.initializeListeners();
      /* eslint-disable no-console */
      console.log(this.$props.hostIP)
      console.log(this.$props.pin)
      /* eslint-enable no-console */
  },
  props: ['host', 'pin'],
  data () {
      return {
          active: false,
          touches: 0
      }
  },
  methods: {
      initializeListeners: function () {
          var el = document.getElementsByTagName("canvas")[0];
          el.addEventListener("touchstart", this.handleStart, false);
          el.addEventListener("touchend", this.handleEnd, false);
          //el.addEventListener("touchcancel", this.handleCancel, false);
          //el.addEventListener("touchmove", this.handleMove, false);
          //console.log("initialized.");
      },
      handleStart: function (event) {
          event.preventDefault()
          this.active = true
          this.touches += 1
      },
      handleEnd: function (event) {
          event.preventDefault();
          this.active = false
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
