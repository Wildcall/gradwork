<template>
  <v-card flat class="mx-auto">
    <v-card-title>
      {{ title }}
    </v-card-title>
    <v-card-subtitle>
      {{ subtext }}
    </v-card-subtitle>

    <v-divider class="mt-5 mb-5"/>

    <v-list dense>
      <v-list-item-group mandatory>

        <v-list-item @click="push(items[0].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[0].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="push(items[1].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[1].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="push(items[2].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[2].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="push(items[3].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[3].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="push(items[4].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[4].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="push(items[5].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[5].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider class="mt-5 mb-5"/>

        <v-list-item @click="$emit(items[6].path)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[6].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "site-menu",

  data() {
    return {
      tab: null,
      items: [
        { title: 'Редактирование', path: 'edit' },
        { title: 'Статистика', path: 'stat' },
        { title: 'Crawler', path: 'crawler' },
        { title: 'Indexer', path: 'presetCrawler' },
        { title: 'Страницы', path: 'pages' },
        { title: 'Ошибки', path: 'errors' },
        { title: 'Закрыть', path: 'close' },
      ],
    }
  },

  props: {
    title: String,
    subtext: String,
  },

  methods:{
    push(path){
      this.$router.push(path)
    }
  }
}
</script>