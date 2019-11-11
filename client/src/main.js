import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

axios.defaults.headers.post['Content-Type'] ='application/x-www-form-urlencoded';

Vue.prototype.$http = axios.create({
  baseURL: 'http://192.168.43.137:7000/',
  headers: {'Access-Control-Allow-Origin': '*'}
})

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
