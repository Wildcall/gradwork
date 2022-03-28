<template>
  <v-container>
    <loading-bar
        v-if="loading"
    >
      Загрузка страниц...
    </loading-bar>
    <v-container v-else-if="pages">
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
        <v-btn
            text
            @click="update"
        >
          Обновить
        </v-btn>
      </v-toolbar>
      <v-data-table
          :headers="headers"
          :items="pages"
          :search="search"
          item-key="id"
          @click:row="open"
      >
        <template v-slot:item.hasIndex="{ item }">
          <v-simple-checkbox
              v-model="item.hasIndex"
              disabled/>
        </template>
        <template v-slot:item.blacklist="{ item }">
          <v-simple-checkbox
              v-model="item.blacklist"
              disabled/>
        </template>
      </v-data-table>
    </v-container>
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import LoadingBar from "../../component/ui/loading-bar.vue";

export default {
  name: "SitePages",
  components: {LoadingBar},
  data() {
    return {
      search: '',
      headers:[
        { text: 'Номер', value: 'id' },
        { text: 'Путь', value: 'path' },
        { text: 'Индекс', value: 'hasIndex' },
        { text: 'Пропускать?', value: 'blacklist' }
      ],
      pages: null
    }
  },

  computed: {
    ...mapGetters({
      loading: 'page/getLoading',
      sitePages: 'page/getSitePages',
    })
  },

  methods: {
    ...mapActions({
      fetchPages: 'page/fetchPages',
    }),

    update() {
      const id = Number(this.$route.params.id)
      this.fetchPages(id).then(() => {
        this.pages = this.sitePages(id).pages
      })
    },

    open(item) {
      console.log('Open page - ' + item.id)
    }
  },

  mounted() {
    const id = Number(this.$route.params.id)
    if (this.sitePages(id) === undefined){
      this.fetchPages(id).then(() => {
        this.pages = this.sitePages(id).pages
      })
    } else {
      this.pages = this.sitePages(id).pages
    }
  }
}
</script>