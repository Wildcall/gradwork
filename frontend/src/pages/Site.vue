<template>
  <v-card
      class="ma-5"
      elevation="5"
  >
    <v-container
          v-if="site"
          class="d-flex justify-space-between ml-0"
      >
        <v-col
            cols="2"
            class="flex-grow-0 flex-shrink-0"
        >
          <site-menu
              :title="site.name"
              :subtext="site.path"
              v-on:close="closeTab"
          />
        </v-col>
        <v-col
            cols="0"
            class="flex-grow-1 flex-shrink-0"
        >
          <router-view></router-view>
        </v-col>
      </v-container>
  </v-card>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

import siteMenu from "@/components/ui/site-menu";

export default {
  name: "Site",

  components: {
    siteMenu,
  },

  data() {
    return {
      site: null
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites'
    })
  },

  methods: {
    ...mapActions({
      storageDeleteTab: 'storage/deleteTab',
    }),

    closeTab() {
      this.storageDeleteTab(this.$route.path)
      this.$router.push({path: '/'})
    },
  },

  beforeRouteUpdate(to, from, next) {
    this.site = this.sites.find(obj => obj.id === Number(to.params.id))
    next()
  },

  mounted() {
    this.site = this.sites.find(obj => obj.id === Number(this.$route.params.id))
  },
}

</script>
