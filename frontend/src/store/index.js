import Vue from 'vue'
import Vuex from 'vuex'
import site from './modules/site'
import indexing from "./modules/indexing";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        site,
        indexing
    }
})