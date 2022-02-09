<template>
  <v-card>
    <v-toolbar
        dark
    >
      <v-btn
          icon
          dark
          @click="$emit('close')"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Статистика</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-card-text>
      добавить статистику сайта
    </v-card-text>
    <v-card-actions>
      Добавить кнопки обновить статистику
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "SiteStat",

  props: {
    site: null
  },
}
</script>

<style scoped>

</style>