import axios from "axios";
import {crawlerAPI} from "../../const";

const state = () => ({
    crawling: [],
    loadingCrawling: false
})

const getters = {
    getCrawling: (state) => {
        return state.crawling
    },

    getLoadingCrawler: (state) => {
        return state.loadingCrawling
    }
}

const mutations = {
    push(state, site) {
        state.crawling.push(site)
        state.loadingCrawling = true
    },

    delete(state, site) {
        state.crawling.splice(state.crawling.indexOf(site), 1)
        if (state.crawling.length === 0)
            state.loadingCrawling = false
    },

}

const actions = {
    async startCrawler({commit}, site) {
        try {
            commit('push', site)
            await axios.get(crawlerAPI + 'start/' + site.id)
            commit('delete', site)
        } catch (e) {
            console.log(e)
        }
    },

    async stopCrawler({commit}, site) {
        try {
            await axios.get(crawlerAPI + 'stop/' + site.id)
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