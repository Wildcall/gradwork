import axios from "axios";
import {siteAPI} from "@/const";

const state = () => ({
    sites: [],
    loading: false
})

const getters = {
    getSites: (state) => {
        return state.sites
    },

    getLoading: (state) => {
        return state.loading
    },
}

const mutations = {
    setLoading(state, bool) {
        state.loading = bool
    },

    setSites(state, sites) {
        state.sites = sites
    },

    pushSite(state, site) {
        state.sites.push(site)
    },

    updateSite(state, site) {
        const index = state.sites.findIndex((obj => obj.id === site.id))
        state.sites.splice(index, 1)
        state.sites.push(site)
    },

    deleteSite(state, id) {
        const index = state.sites.findIndex(obj => obj.id === id)
        state.sites.splice(index, 1)
    },
}

const actions = {
    // Get list of sites
    async fetchSites({commit}) {
        try {
            commit('setLoading', true)
            const response = await axios.get(siteAPI)
            commit('setSites', response.data)
            return response.data
        } catch(error) {
            console.log(error)
        } finally {
            commit('setLoading', false)
        }
    },
    // Get a specific site
    async fetchSite({commit}, id) {
        try {
            commit('setLoading', true)
            const response = await axios.get(siteAPI + id)
            commit('updateSite', response.data)
            return response.data
        } catch(e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    // Create a new site
    async createSite({commit}, site) {
        try {
            commit('setLoading', true)
            const response = await axios.post(siteAPI, site)
            commit('pushSite', response.data)
            return response.data
        }
        catch(error) {
            console.log(error)
        } finally {
            commit('setLoading', false)
        }
    },
    // Change a site
    async updateSite({commit}, site) {
        try {
            commit('setLoading', true)
            const data = {
                name: site.name,
                crawlerId: site.crawlerId,
                indexerId: site.indexerId
            }
            const response = await axios.put(siteAPI + site.id, data)
            commit('updateSite', response.data)
            return response.data
        } catch (e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
    // Delete a site
    async deleteSite({commit}, id) {
        try {
            commit('setLoading', true)
            await axios.delete(siteAPI + id)
            commit('deleteSite', id)
        } catch (e) {
            console.log(e)
        } finally {
            commit('setLoading', false)
        }
    },
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}