<template>
  <v-container>
    <loading-bar
        v-if="loading"
    >
      Загрузка ошибок...
    </loading-bar>
    <v-container v-else-if="errors">
      <v-toolbar flat>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            flat
            label="Поиск"
            hide-details
            solo-inverted
        />
      </v-toolbar>
      <v-data-table
          :headers="headers"
          :items="errors"
          :search="search"
          item-key="id"
          @click:row="open"
      />
    </v-container>
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import LoadingBar from "../../component/ui/loading-bar.vue";

export default {
  name: "SiteErrors",
  components: {LoadingBar},
  data() {
    return {
      search: '',
      headers:[
        { text: 'Id', value: 'id' },
        { text: 'Text', value: 'text' },
        { text: 'Error time', value: 'errorTime' },
        { text: 'Page', value: 'pagePath' }
      ],
      errors: null
    }
  },

  computed: {
    ...mapGetters({
      loading: 'error/getLoading',
      siteErrors: 'error/getSiteErrors',
    })
  },

  methods: {
    ...mapActions({
      fetchErrors: 'error/fetchErrors',
    }),

    update() {
      const id = Number(this.$route.params.id)
      this.fetchErrors(id).then(() => {
        this.pages = this.siteErrors(id)
      })
    },

    open(item) {
      console.log('Open page - ' + item.id)
    }
  },

  mounted() {
    const id = Number(this.$route.params.id)
    if (this.siteErrors(id) === undefined){
      this.fetchErrors(id).then(() => {
        this.errors = this.siteErrors(id).pages
      })
    } else {
      this.errors = this.siteErrors(id).pages
    }
  }
}
</script>

<style scoped>

</style>