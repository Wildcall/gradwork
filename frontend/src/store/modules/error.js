import axios from "axios";
import {siteErrorsAPI} from "@/const";

const state = () => ({
    data: [],
    loading: false
})

const getters = {
    getSiteErrors: (state) => (id) => {
        return state.data.find(errors => errors.siteId === id)
    },

    getLoading: (state) => {
        return state.loading
    },
}

const mutations = {
    setLoading(state, bool) {
        state.loading = bool
    },

    addErrors(state, siteErrors) {
        const index = state.data.findIndex(obj => obj.siteId === siteErrors.siteId)
        if (index !== -1)
            state.data.splice(index, 1)
        state.data.push(siteErrors)
    },

    deleteErrors(state, {siteId}) {
        const index = state.data.findIndex(obj => obj.siteId === siteId)
        if (index !== -1)
            state.data.splice(index, 1)
    }
}

const actions = {
    // Get list of pages for specific site
    async fetchErrors({commit}, siteId) {
        try {
            commit('setLoading', true)
            const response = await axios.get(siteErrorsAPI + siteId)
            const obj = {
                siteId: Number(siteId),
                pages: response.data
            }
            commit('addErrors', obj)
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