import axios from "axios";
import {baseURL} from "@/const";

const state = () => ({
    indexing: []
})

const getters = {
    getStatus: (state) => {
        return state.indexing
    }
}

const mutations = {
    push(state, site) {
        state.indexing.push(site)
    },
    delete(state, site) {
        state.indexing.splice(state.indexing.indexOf(site), 1)
    }
}

const actions = {
    async start({commit}, site) {
        try {
            commit('push', site)
            await axios.get(baseURL + '/indexing/start/' + site.id)
            commit('delete', site)
        } catch (e) {
            console.log(e)
        }
    },
    async stop({commit}, site) {
        try {
            console.log("stop for site " + site.id)
            await axios.get(baseURL + '/indexing/stop/' + site.id)
            commit('delete', site)
        } catch (e) {
            console.log(e)
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}