import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

axios.defaults.headers.post['Content-Type'] ='application/x-www-form-urlencoded';

Vue.prototype.$http = axios.create({
  baseURL: process.env.ROUTING_SERVER + ':' + process.env.AJAX_PORT + '/',
  headers: {'Access-Control-Allow-Origin': '*'}
})

Vue.prototype.$socket = null;

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
