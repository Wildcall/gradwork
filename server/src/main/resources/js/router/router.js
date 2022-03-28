import Vue from 'vue'
import VueRouter from 'vue-router'

import List from "../pages/List.vue";
import Summary from "../pages/Summary.vue";
import Crawler from "../pages/Crawler.vue";
import Indexer from "../pages/Indexer.vue";
import Site from "../pages/Site.vue";

import SiteEdit from "../component/site/SiteEdit.vue";
import SiteStatistics from "../component/site/SiteStatistics.vue";
import SiteCrawler from "../component/site/SiteCrawler.vue";
import SiteIndexer from "../component/site/SiteIndexer.vue";
import SiteErrors from "../component/site/SiteErrors.vue";
import SitePages from "../component/site/SitePages.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', name: 'list', component: List },
    { path: '/summary', name: 'summary', component: Summary },
    { path: '/crawler', name: 'crawler', component: Crawler },
    { path: '/indexer', name: 'indexer', component: Indexer },
    { path: '/site/:id', name: 'site', component: Site,
        children:
            [{
                path: '',
                component: SiteEdit
            },{
                path: 'edit',
                component: SiteEdit
            },{
                path: 'stat',
                component: SiteStatistics
            },{
                path: 'crawler',
                component: SiteCrawler
            },{
                path: 'indexer',
                component: SiteIndexer
            },{
                path: 'errors',
                component: SiteErrors
            },{
                path: 'pages',
                component: SitePages
            }]
    },
]

export default new VueRouter({
    mode: 'history',
    routes
})