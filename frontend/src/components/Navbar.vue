<template>
  <v-navigation-drawer
      app
      permanent
  >
    <v-list nav dense>
      <v-list-item link href="/">
        <v-list-item-icon>
          <v-icon>mdi-home</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Главная</v-list-item-title>
      </v-list-item>

      <v-list-item link href="/sites">
        <v-list-item-icon>
          <v-icon>mdi-earth</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Сайты</v-list-item-title>
      </v-list-item>

      <v-list-item link href="/page">
        <v-list-item-icon>
          <v-icon>mdi-file</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Страницы</v-list-item-title>
      </v-list-item>

      <v-list-item link href="/indexing">
        <v-list-item-icon>
          <v-icon>mdi-spider</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Индексация</v-list-item-title>
      </v-list-item>

      <v-list-item link href="/statistics">
        <v-list-item-icon>
          <v-icon>mdi-chart-bar</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Статистика</v-list-item-title>
      </v-list-item>

    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "Navbar"
}
</script>