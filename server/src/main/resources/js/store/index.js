import Vuex from 'vuex'
import Vue from 'vue'
import site from './module/site'
import actions from "./module/actions";
import storage from "./module/storage";
import settings from "./module/settings";
import page from "./module/page";
import error from "./module/error";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        site,
        actions,
        storage,
        settings,
        page,
        error,
    }
})