import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import Home from './views/Home.vue'
import Motion from './views/Motion.vue'
import Pairing from './views/Pairing.vue'

export default new VueRouter({
    routes: [
        {
            path: "/",
            name: 'Home',
            component: Home
        },
        {
            path: "/motiontest",
            name: 'Motion',
            component: Motion
        },
        {
            path: "/pair",
            name: 'Pair',
            component: Pairing
        }
    ]
})