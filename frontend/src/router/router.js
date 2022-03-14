import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from "@/pages/Main";
import Summary from "@/pages/Summary";
import List from "@/components/site/List";

Vue.use(VueRouter)

const routes = [
    { path: '/', name: 'Main', component: Main },
    { path: '/sites', name: 'Sites', component: List },
    { path: '/summary', name: 'Summary', component: Summary },
]

export default new VueRouter({
    mode: 'history',
    routes
})