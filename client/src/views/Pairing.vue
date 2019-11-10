<template>
    <div id="pairing">
        <label>Enter your 4-digit PIN: </label>
        <input v-model="pin" name="number" id="txt" placeholder="4 digit number">
        <button id="pairingButton" @click="getClientIP()">Pair</button>
        <div id="error-div">
            <h3 v-if="invalidPin">There is no valid session with that PIN. Please try again.</h3>
        </div>
    </div>
</template>

<script>

import router from '../router'

export default {
  name: "Pairing",
  components: {
      
  },
  data () {
      return {
          pin: "",
          invalidPin: false
      }
  },
  methods: {
      getClientIP() {
          this.$http.get('/getip/' + this.pin)
          .then((response) => {
              if (response.status == 200) {
                  /* eslint-disable no-console */
                  console.log(response.data)
                  /* eslint-enable no-console */
                  router.push({ name: 'Remote', params: { host: response.data, pin: this.pin } })
              }
          })
          .catch((error) => {
              /* eslint-disable no-console */
              console.log(error)
              /* eslint-enable no-console */
              this.pin = ""
              this.invalidPin = true
          })
      }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#pairing {
    font-size: 36px;
}

#txt{
padding : 4px;
font-size: 50px;
border-width: 2px;
border-color: #cccccc;
background-color: #ffffff;
color: #000000;
border-radius: 20px;
box-shadow: 0px 0px 5px rgba(66,66,66,0.75);
}

#pairingButton {
    height: 6vw;
    width: 7vw;
    color: white;
    font-size: 30px;
    font-style: initial;
    border-style: none;
    border-radius: 15%;
}

</style>
