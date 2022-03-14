import axios from "axios";
import {crawlerAPI, indexerAPI} from "@/const";

const state = () => ({
    crawlers: [],
    indexers: [],
    loading: false,
})

const getters = {
    getCrawlers: (state) => {
        return state.crawlers
    },
    getIndexer: (state) => {
        return state.indexers
    },
    getLoading: (state) => {
        return state.loading
    },
}

const mutations = {
    setLoading(state, bool) {
        state.loading = bool
    },
    pushCrawler(state, crawler) {
        const index = state.crawlers.findIndex((obj => obj.id === crawler.id))
        if (index !== -1)
            state.crawlers.splice(index, 1)
        state.crawlers.push(crawler)
    },
    pushIndexer(state, indexer) {
        const index = state.indexers.findIndex((obj => obj.id === indexer.id))
        if (index !== -1)
            state.indexers.splice(index, 1)
        state.indexers.push(indexer)
    },
    deleteCrawler(state, crawler) {
        state.crawlers.splice(state.sites.indexOf(crawler), 1)
    },
    deleteIndexer(state, indexer) {
        state.indexers.splice(state.sites.indexOf(indexer), 1)
    },
}

const actions = {
    async fetchCrawler({ commit}, id) {
        try {
            commit('setLoading', true)
            const response = await axios.get(crawlerAPI + id)
            commit('pushCrawler', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },

    async fetchIndexer({ commit }, id) {
        try {
            commit('setLoading', true)
            const response = await axios.get(indexerAPI + id)
            commit('pushIndexer', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
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