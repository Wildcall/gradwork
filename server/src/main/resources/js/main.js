import Vue from 'vue'
import App from './App.vue'
import store from './store'
import vuetify from './plugin/vuetify'
import router from "./router/router";

new Vue({
    store,
    vuetify,
    router,
    render: a => a(App)
}).$mount('#app')