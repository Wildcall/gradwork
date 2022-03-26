import Vue from 'vue'
import Vuex from 'vuex'
import site from '@/store/modules/site'
import actions from "@/store/modules/actions";
import storage from "@/store/modules/storage";
import settings from "@/store/modules/settings";
import page from "@/store/modules/page";
import error from "@/store/modules/error";

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