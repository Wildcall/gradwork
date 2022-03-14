<template>
  <v-card>
    <v-toolbar dark>
      <v-btn
          icon
          dark
          @click="$emit('close')"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Ошибки</v-toolbar-title>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="errors"
        item-key="id"
    />
  </v-card>
</template>

<script>
export default {
  name: "SitePages",

  data() {
    return {
      headers:[
        { text: 'Id', value: 'id' },
        { text: 'Text', value: 'text' },
        { text: 'Error time', value: 'errorTime' },
        { text: 'Page', value: 'pagePath' }
      ]
    }
  },

  props: {
    errors: null
  },
}
</script>

<style scoped>

</style>