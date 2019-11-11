<template>
    <div id="home">
        <img src='../assets/logo.png' alt="">
        <h1>Hawking</h1>
        <h2>An Accessible Web Browser</h2>
        <router-link :to="{name: 'Pair'}">
            <button id="pair"> Pair</button>
        </router-link>
    </div>
</template>

<script>
    export default {
        name: "Home",
        components: {}
    };
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #home {
    }
    @media only screen and (max-width: 768px) {
        img {
            width: 50%;
            height: 50%;
        }
    }
    h1 {
        font-size: 7.3vh;
        -webkit-animation: fadein 3s;
        animation: fadein 3s;
    }
    h2 {
        font-size: 5.5vh;
        -webkit-animation: fadein 3s;
        animation: fadein 3s;
    }
    @keyframes fadein {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
    #pair {
        height: 8vh;
        width: 8rem;
        color: white;
        background-color: #223843;
        font-size: 28px;
        font-style: initial;
        border-style: none;
        border-radius: 8px;
    }
    #pair:active {
        transform: translate(0px, 5px);
        -webkit-transform: translate(0px, 5px);
        border-bottom: 1px solid;
        transition: all 0.1s;
        -webkit-transition: all 0.1s;
    }
</style>