<template>
  <v-app>
    <v-app-bar app dark>
        <v-toolbar-title>
          {{ storageTabs[tab].title }}
        </v-toolbar-title>
        <v-spacer/>
        <SiteForm v-on:open-tab="openTab"/>
        <template v-slot:extension>
          <v-tabs
              v-model="tab"
              align-with-title
              color="yellow"
              slider-color="yellow"
              slider-size="5"
              show-arrows
          >
            <v-tab
                v-for="item in storageTabs"
                :key="item.id"
            >
              <div>
                <tooltip-icon
                    :title="item.title"
                    :icon="item.icon"
                />
              </div>
            </v-tab>
          </v-tabs>
        </template>
    </v-app-bar>
    <v-main>
      <v-tabs-items v-model="tab">
        <v-tab-item
            v-for="item in storageTabs"
            :key="item.id"
        >
          <List
              v-if="item.id === -2"
              :sites="sites"
              :loading="loading"
              v-on:open-tab="openTab"
          />
          <Statistics
              v-else-if="item.id === -1"
          />
          <Site
              v-else
              :tab="item"
              v-on:close="closeTab"
              v-on:update="updateTab"
          />
        </v-tab-item>
      </v-tabs-items>
      <router-view></router-view>
    </v-main>
    <v-footer app dark>
    </v-footer>
  </v-app>
</template>

<script>
import Site from "@/pages/Site";
import SiteForm from "@/components/site/New";
import Statistics from "@/pages/Summary";
import TooltipIcon from "@/components/ui/tooltip-icon";
import List from "@/components/site/List";
import {mapActions, mapGetters} from "vuex";

export default {
  components: {Site, SiteForm, Statistics, TooltipIcon, List},

  data() {
    return {
      tab: 0,
      hover: false
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites',
      views: 'site/getViews',
      loading: 'site/getLoading',

      storageTabs: 'storage/getTabs'
    })
  },

  methods: {
    ...mapActions({
      fetchSites: 'site/fetchSites',

      storageUpdateTab: 'storage/updateTab',
      storageInitTabs: 'storage/initTabs',
      storageAddTab: 'storage/addTab',
      storageDeleteTab: 'storage/deleteTab',
    }),

    openTab(tab) {
      if (this.storageTabs.findIndex((obj => obj.id === tab.id)) === -1) {
        this.storageAddTab(tab)
      }
    },

    updateTab(tab) {
      this.storageUpdateTab(tab)
    },

    closeTab(tab) {
      this.storageDeleteTab({id: tab.id, title: tab.name})
      this.deleteView(tab.id)
      this.tab = 0
    },
  },

  mounted() {
    this.fetchSites().then(() => {
      this.storageInitTabs()
    })
  }
}
</script>