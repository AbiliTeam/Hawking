import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import Home from './views/Home.vue'
import Motion from './views/Motion.vue'
import Pairing from './views/Pairing.vue'
import Remote from './views/Remote.vue'

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
        },
        {
            path: "/remote",
            name: 'Remote',
            component: Remote,
            props: true
        }
    ]
})