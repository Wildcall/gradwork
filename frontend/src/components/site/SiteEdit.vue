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
      <v-toolbar-title>Редактирование</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn
            dark
            text
            @click="save"
        >
          Save
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-list three-line>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>ID</v-list-item-title>
          <v-list-item-subtitle>{{ site.id }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Адрес сайта</v-list-item-title>
          <v-list-item-subtitle>
            <v-text-field
                v-model="site.path"
            >
            </v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Название</v-list-item-title>
          <v-list-item-subtitle>
            <v-text-field
                v-model="site.name"
            >
            </v-text-field>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Статус</v-list-item-title>
          <v-list-item-subtitle>{{ site.status }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>Последнее изменение</v-list-item-title>
          <v-list-item-subtitle>{{ site.statusTime}}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

    </v-list>
    <v-card-actions class="justify-end">
      <v-dialog
          v-model="confirm"
          width="500"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              text
              v-bind="attrs"
              v-on="on"
          >
            Удалить
          </v-btn>
        </template>

        <v-card>
          <v-card-title>
            Подтверждение удаления
          </v-card-title>

          <v-card-text>
            В случае удаления сайта будут удалены все сохраненные страницы сайта, индексы и леммы. Вы уверены что хотите удалить сайт?
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn text @click="confirm=false">
              Отмена
            </v-btn>
            <v-btn text @click="confirmDeleting">
              Удалить
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "SiteEdit",

  props: {
    site: null
  },

  data() {
    return {
      confirm: false
    }
  },

  methods: {
    confirmDeleting() {
      this.confirm = false
      this.$emit('delete', this.site)
    },
    save() {
      //this.$emit('close')
      this.$emit('update', this.site)
    }
  }
}
</script>

<style scoped>

</style>