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
      <v-list-item-group
        mandatory
        v-model="tab"
      >
        <v-list-item
            v-for="(item, i) in items"
            :key="i"
            @click="item.path === 'close' ? $emit(item.path) :push(item.path)"
        >
          <v-list-item-content>
            <v-list-item-title>
              {{item.title }}
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
      tab: 0,
      items: [
        { title: 'Редактирование', path: 'edit' },
        { title: 'Статистика', path: 'stat' },
        { title: 'Crawler', path: 'crawler' },
        { title: 'Indexer', path: 'indexer' },
        { title: 'Страницы', path: 'pages' },
        { title: 'Ошибки', path: 'errors' },
        { title: 'Закрыть', path: 'close' },
      ],
    }
  },

  props: {
    reset: Boolean,
    title: String,
    subtext: String,
  },

  methods:{
    push(path){
      if (!this.$route.path.endsWith(path))
        this.$router.push(path)
    },
  },

  updated() {
    if (this.reset){
      this.tab = 0
      this.$emit('update:reset', false)
    }

  }
}
</script>