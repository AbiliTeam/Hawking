<template>
    <div id="pairing">
        <router-link :to="{name: 'Home'}">
            <div id="back-button"><img src='../assets/left-arrow.png' alt="Back"></div>
        </router-link>
        <div id="pin-label">
            <label>Enter your 4-digit PIN: </label>
        </div>
        <div id="input-and-button">
            <input v-model="pin" name="number" id="pin-input" placeholder="">
            <button id="pairing-button" @click="getClientIP()">Pair</button>
        </div>
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
                        this.pin = "";
                        this.invalidPin = true;
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
        height: 80vh;
        position: relative;
        flex-direction: column;
    }
    #pin-input {
        font-size: 5.5vh;
        border-width: 2px;
        border-color: #cccccc;
        background-color: #ffffff;
        color: #000000;
        width: 8rem;
        border-radius: 8px;
        box-shadow: 0px 0px 5px rgba(66, 66, 66, 0.75);
        margin-left: 2vw;
        margin-right: 2vw;
    }
    #pairing-button {
        height: 7vh;
        width: 8rem;
        color: white;
        background-color: #223843;
        font-size: 5.5vh;
        font-style: initial;
        border-style: none;
        border-radius: 8px;
        margin-top: 2vh;
    }
    #pairing-button:active {
        transform: translate(0px, 5px);
        -webkit-transform: translate(0px, 5px);
        border-bottom: 1px solid;
        transition: all 0.1s;
        -webkit-transition: all 0.1s;
    }
    #error-div {
        margin-top: 20vh;
        position: absolute;
    }
    #back-button {
        position: absolute;
        top: 0;
        left: 0;
        float: left;
        margin-left: 2px;
    }
    #pin-label {
        margin-bottom: 2vh;
    }

</style>