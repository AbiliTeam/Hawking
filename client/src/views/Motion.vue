<template>
    <div id="motion">
        Output:
        <div>absolute: {{ this.absolute }}</div>
        <div>z: {{ this.alpha }}</div>
        <div>x: {{ this.beta }}</div>
        <div>y: {{ this.gamma }}</div>

        Motion:
        <div>accel: {{ this.acceleration }}</div>
        <div>z: {{ this.accelX }}</div>
        <div>x: {{ this.accelY }}</div>
        <div>y: {{ this.accelZ }}</div>
    </div>
</template>

<script>
export default {
  name: "Motion",
  data () {
      return {
        absolute: null,
        alpha: null,
        beta: null,
        gamma: null,

        accelX: null,
        accelY: null,
        accelZ: null
      }
  },
  components: {
      
  },
  methods: {
    handleOrientation(event) {
        this.absolute = event.absolute
        this.alpha = event.alpha;
        this.beta = event.beta;
        this.gamma = event.gamma;
    },

    handleMotion(event) {
        this.accelX = event.acceleration.x
        this.accelY = event.acceleration.y
        this.accelZ = event.acceleration.z

        
    }


  },
  created() {
      window.addEventListener("deviceorientation", this.handleOrientation, true);
      window.addEventListener("devicemotion", this.handleMotion, true);
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
