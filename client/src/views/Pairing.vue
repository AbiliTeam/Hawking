<template>
    <div id="home">
        <label>INPUT</label>
        <input type="text" v-model="this.pin" name="number" placeholder="4 digit number">
        <div>pin: {{this.pin}}</div>
    </div>
</template>

<script>
export default {
  name: "Pairing",
  components: {
      
  },
  data () {
      return {
          pin: null
      }
  },
  methods: {
      
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
