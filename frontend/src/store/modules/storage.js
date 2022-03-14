const state = () => ({
    tabs_key: 'open_tabs',
    tabs: [
        {
            link: '/',
            title: 'Главное меню',
            icon: 'mdi-home',
        },
        {
            link: '/summary',
            title: "Статистика",
            icon: 'mdi-chart-box',
        },
        {
            link: '/crawler',
            title: "Настройки поискового алгоритма",
            icon: 'mdi-cloud-search-outline',
        },
        {
            link: '/indexer',
            title: "Настройки индексации",
            icon: 'mdi-clipboard-alert-outline',
        }]
})

const getters = {
    getTabs: (state) => {
        return state.tabs
    }
}

const mutations = {
    addTab(state, tab) {
        const storage = localStorage.getItem(state.tabs_key)
        if (storage) {
            const dataInStorage = JSON.parse(storage)
            const storageIndex = dataInStorage.findIndex((obj => obj.link === tab.link))
            const stateIndex = state.tabs.findIndex((obj => obj.link === tab.link))
            if (storageIndex === -1 && stateIndex === -1) {
                dataInStorage.push(tab)
                state.tabs.push(tab)
                localStorage.setItem(state.tabs_key, JSON.stringify(dataInStorage))
            }
        }
    },

    deleteTab(state, link) {
        const storage = localStorage.getItem(state.tabs_key)
        if (storage) {
            const dataInStorage = JSON.parse(storage)
            const storageIndex = dataInStorage.findIndex(obj => obj.link === link)
            dataInStorage.splice(storageIndex, 1)
            const stateIndex = state.tabs.findIndex(obj => obj.link === link)
            state.tabs.splice(stateIndex, 1)
            localStorage.setItem(state.tabs_key, JSON.stringify(dataInStorage))
        }

    },

    initTabs(state) {
        const storage = localStorage.getItem(state.tabs_key)
        if (storage) {
            const dataInStorage = JSON.parse(storage)
            if (dataInStorage.length > 2){
                state.tabs = dataInStorage
            }
        } else {
            localStorage.setItem(state.tabs_key, JSON.stringify(state.tabs))
        }
    },

    updateTab(state, tab) {
        const storage = localStorage.getItem(state.tabs_key)
        if (storage) {
            const dataInStorage = JSON.parse(storage)
            const storageIndex = dataInStorage.findIndex(obj => obj.link === tab.link)
            dataInStorage[storageIndex] = tab
            localStorage.setItem(state.tabs_key, JSON.stringify(dataInStorage))

            const stateIndex = state.tabs.findIndex(obj => obj.link === tab.link)
            if (stateIndex !== -1) {
                state.tabs[stateIndex] = tab
            }
        }
    }
}

const actions = {
    addTab({commit}, tab) {
        commit('addTab', tab)
    },

    deleteTab({commit}, link) {
        commit('deleteTab', link)
    },

    initTabs({commit}) {
        commit('initTabs')
    },

    updateTab({commit}, tab) {
        commit('updateTab', tab)
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}