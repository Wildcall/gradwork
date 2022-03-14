import Vue from 'vue'
import VueRouter from 'vue-router'

import List from "@/pages/List";
import Summary from "@/pages/Summary";
import Crawler from "@/pages/Crawler";
import Indexer from "@/pages/Indexer";
import Site from "@/pages/Site";

import SiteEdit from "@/components/site/SiteEdit";
import SiteStatistics from "@/components/site/SiteStatistics";
import SiteCrawler from "@/components/site/SiteCrawler";
import SiteIndexer from "@/components/site/SiteIndexer";
import SiteErrors from "@/components/site/SiteErrors";
import SitePages from "@/components/site/SitePages";

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