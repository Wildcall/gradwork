import Vue from 'vue'
import Vuex from 'vuex'
import site from '@/store/modules/site'
import indexing from "@/store/modules/indexing";
import storage from "@/store/modules/storage";
import settings from "@/store/modules/settings";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        site,
        indexing,
        storage,
        settings,
    }
})