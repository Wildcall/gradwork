<template>
  <v-app-bar
      dark
      extended
      app
  >
    <v-toolbar-title>
      {{ storageTabs[tab].title }}
    </v-toolbar-title>
    <v-spacer/>
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
            :key="item.link"
            @click="$route.path !== item.link ? $router.push(item.link) : true"
        >
          <tooltip-icon
              :title="item.title"
              :icon="item.icon"
          />
        </v-tab>
      </v-tabs>
    </template>
  </v-app-bar>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import tooltipIcon from "@/components/ui/tooltip-icon";

export default {
  name: "NavigationBar",

  components: {tooltipIcon},

  data() {
    return {
      tab: 0,
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites',
      loading: 'site/getLoading',
      storageTabs: 'storage/getTabs'
    })
  },

  methods: {
    ...mapActions({
      fetchSites: 'site/fetchSites',

      storageAddTab: 'storage/addTab',
    }),
  },

  beforeMount() {
    if (this.$route.path !== '/')
      this.$router.push({path: '/'})
  },

  beforeUpdate() {
    if (this.$route.path === '/'){
      this.tab = 0
    }

  }
}
</script>