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

export default {
  name: "List",

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

  props: {
    sites: [],
    loading: Boolean,
  },

  methods: {
    open(item) {
      this.$emit('open-tab', {id: item.id, title: item.name})
    }
  }
}
</script>