<template>
  <v-container>
    <loading-bar
        v-if="loading"
    >
      Загрузка настроек indexer...
    </loading-bar>
    <v-list
        v-else-if="indexer"
        three-line>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Название</v-list-item-title>
          <v-list-item-subtitle>{{ indexer.presetName }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Описание</v-list-item-title>
          <v-list-item-subtitle>{{ indexer.description }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Selector weight</v-list-item-title>
          <v-list-item-subtitle>
            <v-text-field v-model="indexer.selectorWeight">
            </v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

    </v-list>
    <v-card-actions class="justify-end">
      <v-btn
          @click="update"
          text
      >
        Сохранить изменения
      </v-btn>
    </v-card-actions>
  </v-container>
</template>

<script>
import LoadingBar from "@/components/ui/loading-bar";
export default {
  name: "SiteIndexer",

  components: {LoadingBar},

  props: {
    loading: null,
    indexer: null
  },

  methods: {
    update() {
      this.$emit('update', this.indexer)
    },
  }
}
</script>