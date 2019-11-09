<template>
        <div id="home">
            <div id="title">
                <h1>Hawking</h1>
                <h2 id=""> Accessible Browser </h2>
                <router-link :to="{name: 'Pair'}">
                    <button onclick="Pair()" id="Pair"> Pair </button>
                </router-link>
            </div>
        </div>
</template>

<script>
export default {
  name: "Home",
  components: {
      
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #home {
    }

    #title {
        margin: 0;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    h1 {
        font-size: 50px;
    }

    h2 {
        font-size: 40px;
    }

    #Pair {
        height: 5vw;
        width: 7vw;
        color: white;
        background-color: cornflowerblue;
        font-size: 30px;
        font-style: initial;
        border-style: none;
        border-radius: 15%;
    }

</style>
