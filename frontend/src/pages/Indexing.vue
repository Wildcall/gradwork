<template>
  <v-card>
    <v-toolbar dark>
      <v-toolbar-title>Индексация</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="sites"
        :loading="loading"
        item-key="id"
    >
      <template v-slot:item.actions="{ item }">
        <tooltip-icon
            icon="mdi-play"
            v-on:click="start(item)"
        >
          Start
        </tooltip-icon>
        <tooltip-icon
            icon="mdi-stop"
            v-on:click="stop(item)"
        >
          Stop
        </tooltip-icon>
      </template>
    </v-data-table>
    <v-card-text>
      <v-list>
        <v-list-item v-for="item in status" v-bind:key="item.id">
          {{item.id}}
        </v-list-item>
      </v-list>
    </v-card-text>
  </v-card>
</template>

<script>
import TooltipIcon from "@/components/ui/tooltip-icon";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "Indexing",

  components: {TooltipIcon},

  data() {
    return {
      search: '',
      headers:[
        { text: 'Id', align: 'start', value: 'id' },
        { text: 'Path', value: 'path' },
        { text: 'Name', value: 'name' },
        { text: 'Status', value: 'status' },
        { text: '', value: 'actions', sortable: false },
      ],
      dialog: false,
      site: {
        path: '',
        name: ''
      }
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites',
      loading: 'site/getLoading',
      status: 'indexing/getStatus'
    })
  },

  methods: {
    ...mapActions({
      startIndexing: 'indexing/start',
      stopIndexing: 'indexing/stop',
      readSites: 'site/readSites',
    }),
    start(item) {
      this.startIndexing(item)
    },
    stop(item) {
      this.stopIndexing(item)
    }
  },

  mounted() {
    this.readSites()
  }
}
</script>

<style scoped>

</style>