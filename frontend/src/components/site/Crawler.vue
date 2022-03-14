<template>
  <v-container>
    <loading-bar
        v-if="loading"
    >
      Загрузка настроек crawler...
    </loading-bar>
    <v-container v-else-if="crawler">

      <v-list three-line>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Название</v-list-item-title>
            <v-list-item-subtitle>{{ crawler.presetName }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Описание</v-list-item-title>
            <v-list-item-subtitle>{{ crawler.description }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Задержка</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field v-model="crawler.delay">
              </v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Параллельность</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field v-model="crawler.parallelism">
              </v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Попыток переподключения</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field v-model="crawler.reconnect">
              </v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Timeout</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field v-model="crawler.timeout">
              </v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Referrer</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field v-model="crawler.referrer">
              </v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>UserAgent</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field v-model="crawler.userAgent">
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
          Сбросить
        </v-btn>
        <v-btn
            @click="update"
            text
        >
          Сохранить изменения
        </v-btn>
      </v-card-actions>
    </v-container>
  </v-container>
</template>

<script>
import LoadingBar from "@/components/ui/loading-bar";

export default {
  name: "Crawler",

  components: {LoadingBar},

  props: {
    loading: null,
    crawler: null
  },

  methods: {
    update() {
      this.$emit('update', this.crawler)
    },

    reset() {
      this.$emit('reset')
    }
  }
}
</script>