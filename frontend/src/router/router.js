import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from "@/pages/Main";
import Sites from "@/pages/Sites";
import Page from "@/pages/Page";
import Indexing from "@/pages/Indexing";
import Statistics from "@/pages/Statistics";

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'Main', component: Main },
  { path: '/sites', name: 'Sites', component: Sites },
  { path: '/page', name: 'Page', component: Page },
  { path: '/indexing', name: 'Indexing', component: Indexing },
  { path: '/statistics', name: 'Statistics', component: Statistics }
]

export default new VueRouter({
  mode: 'history',
  routes
})
