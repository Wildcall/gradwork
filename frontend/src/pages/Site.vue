<template>
  <v-card
      class="ma-5"
      elevation="5"
  >
    <loading-bar
        v-if="loadingSite"
    >
      Загрузка сайта...
    </loading-bar>
    <v-container
          v-else-if="site"
          class="d-flex justify-space-between ml-0"
      >
        <v-col
            cols="2"
            class="flex-grow-0 flex-shrink-0"
        >
          <site-menu
              :title="site.name"
              :subtext="site.path"
              :items="items"
              v-on:action="getAction"
          />
        </v-col>
        <v-col
            cols="0"
            class="flex-grow-1 flex-shrink-0"
        >
          <v-card
              elevation="5"
          >
            <Edit v-if="currentPage === items[0].action"
                :site="site"
                v-on:delete="deleteSite"
                v-on:update="updateSite"
            />
            <Statistics v-else-if="currentPage === items[1].action"
            />
            <Crawler v-else-if="currentPage === items[2].action"
                :loading="loadingSettings"
                :crawler="crawler"
                v-on:reset="resetCrawler"
                v-on:update="updateCrawler"
            />
            <Indexer v-else-if="currentPage === items[4].action"
                :loading="loadingSettings"
                :indexer="indexer"
                v-on:update="updateIndexer"
            />
            <Pages v-else-if="currentPage === items[6].action"
            />
            <Errors v-else-if="currentPage === items[7].action"
            />
          </v-card>
        </v-col>
      </v-container>
  </v-card>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

import loadingBar from "@/components/ui/loading-bar";
import siteMenu from "@/components/ui/site-menu";
import Edit from "@/components/site/Edit";
import Crawler from "@/components/site/Crawler";
import Indexer from "@/components/site/Indexer";
import Errors from "@/components/site/Errors";
import Statistics from "@/components/site/Statistics";
import Pages from "@/components/site/Pages";

export default {
  name: "Site",

  data() {
    return {
      confirm: false,
      site: null,
      crawler: null,
      indexer: null,
      items: [
        { title: 'Редактирование', action: 'edit' },
        { title: 'Статистика', action: 'stat' },
        { title: 'Текущий', action: 'crawler', group: 'Настройки', subgroup: 'Crawler' },
        { title: 'Шаблоны', action: 'presetCrawler', group: 'Настройки', subgroup: 'Crawler' },
        { title: 'Текущий', action: 'indexer', group: 'Настройки', subgroup: 'Indexer' },
        { title: 'Шаблоны', action: 'presetIndexer', group: 'Настройки', subgroup: 'Indexer' },
        { title: 'Страницы', action: 'pages' },
        { title: 'Ошибки', action: 'errors' },
        { title: 'Закрыть', action: 'close' },
      ],
      currentPage: 'edit',
    }
  },

  components: {
    Statistics,
    Errors,
    Indexer,
    Crawler,
    Edit,
    Pages,
    siteMenu,
    loadingBar
  },

  props: {
    tab: null,
  },

  computed: {
    ...mapGetters({
      loadingSite: 'site/getLoading',
      loadingSettings: 'settings/getLoading',
      crawlers: 'settings/getCrawlers',
      indexers: 'settings/getIndexer',
      views: 'site/getViews'
    })
  },

  methods: {
    ...mapActions({
      fetch_site: 'site/fetchSite',
      delete_site: 'site/deleteSite',
      update_site: 'site/updateSite',

      fetch_crawler: 'settings/fetchCrawler',
      fetch_indexer: 'settings/fetchIndexer',
    }),

    getAction(action) {
      if (this.currentPage === action)
        return
      this.currentPage = action
      switch (action) {
        case 'edit':
          break
        case 'stat':
          break
        case 'crawler':
          this.fetchCrawler()
          break
        case 'indexer':
          this.fetchIndexer()
          break
        case 'pages':
          this.fetchPages()
          break
        case 'errors':
          this.fetchErrors()
          break
        case 'close':
          this.closeTab()
          break
      }
    },

    closeTab(){
      this.$emit('close', this.tab)
    },

    updateSite(site) {
      this.update_site(site).then(() => {
        this.site = this.views.find(obj => obj.id === this.site.id)
        this.tab.title = this.site.name
        this.$emit('update', this.tab)
      })
    },

    deleteSite() {
      this.delete_site(this.site.id).then(() => {
        this.closeTab()
      })
    },

    fetchCrawler() {
      const id = this.site.crawlerId
      if (!this.loadingSettings) {
        if (!this.crawler) {
          this.fetch_crawler(id).then(() => {
            this.crawler = this.crawlers.find(obj => obj.id === id)
          })
        }
      }
    },

    fetchIndexer() {
      const id = this.site.indexerId
      if (!this.loadingSettings) {
        if (!this.indexer) {
          this.fetch_indexer(id).then(() => {
            this.indexer = this.indexers.find(obj => obj.id === id)
          })
        }
      }
    },

    fetchPages() {},

    fetchErrors() {},

    updateCrawler(crawler) {
      console.log(crawler)
    },

    updateIndexer(indexer) {
      console.log(indexer)
    },

    resetCrawler() {

    }
  },

  mounted() {
    const id = this.tab.id
    if (id >= 0) {
      if (!this.site)
        this.fetch_site(id).then(() => {
          this.site = this.views.find(obj => obj.id === id)
        })
    }
  },
}

</script>
