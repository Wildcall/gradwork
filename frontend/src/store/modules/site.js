import axios from "axios";
import {baseURL} from "@/const";

const state = () => ({
    sites: [],
    site: null,
    errors: [],
    loading: false
})

const getters = {
    getSites: (state) => {
        return state.sites
    },
    getSite: (state) => {
        return state.site
    },
    getLoading: (state) => {
        return state.loading
    },
    getErrors: (state) => {
        return state.errors
    }
}

const mutations = {
    setSites(state, sites) {
        state.sites = sites
    },
    setSite(state, site) {
        state.site = site
    },
    setLoading(state, bool) {
        state.loading = bool
    },
    setErrors(state, errors) {
        state.errors = errors
    },
    push(state, site) {
        state.sites.push(site)
    },
    update(state, site) {
        const index = state.sites.findIndex((obj => obj.id === site.id))
        state.sites.splice(index, 1)
        state.sites.push(site)
    },
    delete(state, site) {
        state.sites.splice(state.sites.indexOf(site), 1)
    }
}

// Standard CRUD Operations
const actions = {
    // CREATE
    async createSite({commit}, site) {
        try {
            const response = await axios.post(baseURL + '/site', site)
            commit('push', response.data)
        } catch (e) {
            console.log(e)
        }
    },
    // READ
    async readSites({ commit }) {
        try {
            commit('setLoading', true)
            const response = await axios.get(baseURL + '/site')
            commit('setSites', response.data)
        } catch(error) {
            console.log(error)
        } finally {
            commit('setLoading', false)
        }
    },
    async readSite({ commit}, id) {
        try {
            const response = await axios.get(baseURL + '/site/' + id)
            commit('setSite', response.data)
        } catch(e) {
            console.log(e)
        }
    },
    async readSiteErrors({commit}, id) {
        try {
            const response = await axios.get(baseURL + '/error/' + id)
            commit('setErrors', response.data)
        } catch (e) {
            console.log(e)
        }
    },
    // UPDATE
    async updateSite({commit}, site) {
        try {
            const data = {
                id: site.id,
                path: site.path,
                name: site.name
            }
            const response = await axios.put(baseURL + '/site/' + site.id, data)
            commit('update', response.data)
        } catch (e) {
            console.log(e)
        }
    },
    // DELETE
    async deleteSite({commit}, site) {
        try {
            await axios.delete(baseURL + '/site/' + site.id)
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