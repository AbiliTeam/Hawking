<template>
    <div id="pairing">
        <label>Enter your 4-digit PIN: </label>
        <input v-model="pin" name="number" id="txt" placeholder="">
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
        components: {},
        data() {
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
                            router.push({name: 'Remote', params: {host: response.data, pin: this.pin}})
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
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh;
    }

    #txt {
        font-size: 5.5vh;
        border-width: 2px;
        border-color: #cccccc;
        background-color: #ffffff;
        color: #000000;
        width: 20%;
        border-radius: 8px;
        box-shadow: 0px 0px 5px rgba(66, 66, 66, 0.75);
        margin-left: 2vw;
        margin-right: 2vw;
    }

    #pairingButton {
        height: 7vh;
        width: 8rem;
        color: white;
        background-color: #223843;
        font-size: 28px;
        font-style: initial;
        border-style: none;
        border-radius: 8px;
    }

    #pairingButton:active {
        transform: translate(0px, 5px);
        -webkit-transform: translate(0px, 5px);
        border-bottom: 1px solid;
        transition: all 0.1s;
        -webkit-transition: all 0.1s;
    }
    #error-div {
        margin-top: 10vh;
        position: absolute;
    }

</style>
