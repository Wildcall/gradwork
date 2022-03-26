<template>
  <v-card elevation="5" class="ma-5">
    <v-toolbar flat>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          flat
          label="Поиск"
          hide-details
          solo-inverted
      />
      <v-spacer/>
      <site-new v-on:open-tab="open">
        Добавить сайт
      </site-new>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="sites"
        :loading="loading"
        :search="search"
        item-key="id"
        @click:row="open"
    />
  </v-card>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import SiteNew from "@/components/site/SiteNew";

export default {
  name: "List",

  components: {SiteNew},

  data() {
    return {
      search: '',
      headers:[
        { text: 'Id', align: 'start', value: 'id' },
        { text: 'Path', value: 'path' },
        { text: 'Name', value: 'name' },
        { text: 'Status', value: 'status' }
      ]
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites',
      loading: 'site/getLoading',
    })
  },

  methods: {
    ...mapActions({
      fetchSites: 'site/fetchSites',

      storageInitTabs: 'storage/initTabs',
      storageAddTab: 'storage/addTab'
    }),

    open(item) {
      this.storageAddTab({ link: '/site/' + item.id + '/edit', title: item.name })
    }
  },

  mounted() {
    if (this.sites.length === 0){
      this.fetchSites().then(() => {
        this.storageInitTabs()
      })
    }
  }
}
</script>