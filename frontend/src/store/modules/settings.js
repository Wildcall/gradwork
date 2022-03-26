import axios from "axios";
import {crawlerAPI, indexerAPI} from "@/const";

const state = () => ({
    crawlers: [],
    indexers: [],
    loading: false,
})

const getters = {
    getCrawlers: (state, getters, rootState) => {
        return state.crawlers.map((item) => {
            item.siteCount = rootState.site.sites.filter( site => site.crawlerId === item.id).length
            return item
        })
    },

    getIndexers: (state, getters, rootState) => {
        return state.indexers.map((item) => {
            item.siteCount = rootState.site.sites.filter( site => site.indexerId === item.id).length
            return item
        })
    },

    getLoading: (state) => {
        return state.loading
    },
}

const mutations = {
    setLoading(state, bool) {
        state.loading = bool
    },

    setCrawlers(state, crawlers) {
        state.crawlers = crawlers
    },

    setIndexers(state, indexers) {
        state.indexers = indexers
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

    deleteCrawler(state, id) {
        const index = state.crawlers.findIndex(obj => obj.id === id)
        state.crawlers.splice(index, 1)
    },

    deleteIndexer(state, id) {
        const index = state.indexers.findIndex(obj => obj.id === id)
        state.indexers.splice(index, 1)
    },
}

const actions = {
    // Get list of crawlers settings
    async fetchCrawlers({ commit}) {
        try {
            commit('setLoading', true)
            const response = await axios.get(crawlerAPI)
            commit('setCrawlers', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Get list of indexers settings
    async fetchIndexers({ commit }) {
        try {
            commit('setLoading', true)
            const response = await axios.get(indexerAPI)
            commit('setIndexers', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Get a specific crawler settings
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
    //Get a specific indexer settings
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
    },
    //Create a new crawler settings
    async createCrawler({commit}, crawler) {
        try {
            commit('setLoading', true)
            const response = await axios.post(crawlerAPI, crawler)
            commit('pushCrawler', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Create a new indexer settings
    async createIndexer({commit}, indexer) {
        try {
            commit('setLoading', true)
            const response = await axios.post(indexerAPI, indexer)
            commit('pushIndexer', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Create a new crawler settings
    async updateCrawler({commit}, crawler) {
        try {
            commit('setLoading', true)
            const id = crawler.id
            crawler.id = null
            const response = await axios.put(crawlerAPI + id, crawler)
            commit('pushCrawler', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Create a new indexer settings
    async updateIndexer({commit}, indexer) {
        try {
            commit('setLoading', true)
            const id = indexer.id
            indexer.id = null
            const response = await axios.put(indexerAPI + id, indexer)
            commit('pushIndexer', response.data)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Delete crawler settings
    async deleteCrawler({commit}, id) {
        try {
            commit('setLoading', true)
            await axios.delete(crawlerAPI + id)
            commit('deleteCrawler', id)
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    //Delete indexer settings
    async deleteIndexer({commit}, id) {
        try {
            commit('setLoading', true)
            await axios.delete(indexerAPI + id)
            commit('deleteIndexer', id)
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