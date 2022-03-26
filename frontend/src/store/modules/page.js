import axios from "axios";
import {sitePagesAPI} from "@/const";

const state = () => ({
    data: [],
    loading: false
})

const getters = {
    getSitePages: (state) => (id) => {
        return state.data.find(pages => pages.siteId === id)
    },

    getLoading: (state) => {
        return state.loading
    },
}

const mutations = {
    setLoading(state, bool) {
        state.loading = bool
    },

    addPages(state, sitePages) {
        const index = state.data.findIndex(obj => obj.siteId === sitePages.siteId)
        if (index !== -1)
            state.data.splice(index, 1)
        state.data.push(sitePages)
    },

    deletePages(state, {siteId}) {
        const index = state.data.findIndex(obj => obj.siteId === siteId)
        if (index !== -1)
            state.data.splice(index, 1)
    }
}

const actions = {
    // Get list of pages for specific site
    async fetchPages({commit}, siteId) {
        try {
            commit('setLoading', true)
            const response = await axios.get(sitePagesAPI + siteId)
            const sitePages = {
                siteId: Number(siteId),
                pages: response.data
            }
            commit('addPages', sitePages)
        } catch(e) {
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