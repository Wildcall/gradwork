<template>
  <v-card>
    <v-toolbar
    color="#546E7A"
    >
      <v-toolbar-title>Список сайтов</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          flat
          label="Поиск"
          hide-details
          solo-inverted
      ></v-text-field>
       <v-dialog
            v-model="dialog"
            persistent
            max-width="600px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
                color="#FFEE58"
                v-bind="attrs"
                v-on="on"
                class="ml-5 #546E7A--text"
            >
              Добавить сайт
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">Добавление сайта</span>
            </v-card-title>
            <v-card-text>
              <v-col cols="12">
                <v-text-field
                    v-model="site.path"
                    label="Адрес сайта"
                    required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                    v-model="site.name"
                    label="Название сайта"
                    required
                ></v-text-field>
              </v-col>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  text
                  @click="close"
              >
                Закрыть
              </v-btn>
              <v-btn
                  text
                  @click="save"
              >
                Добавить
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="sites"
        :loading="loading"
        :search="search"
        item-key="id"
    >
      <template v-slot:item.actions="{ item }">
        <tooltip-icon
            icon="mdi-poll"
            v-on:click="$emit('open-stat', item.id)"
        >
          Статистика
        </tooltip-icon>
        <tooltip-icon
            icon="mdi-pencil"
            v-on:click="$emit('open-edit', item.id)"
        >
          Редактирование
        </tooltip-icon>
        <tooltip-icon
            icon="mdi-alert-circle"
            v-on:click="$emit('open-error', item.id)"
        >
          Ошибки
        </tooltip-icon>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import TooltipIcon from "@/components/ui/tooltip-icon"

export default {
  name: "SiteList",

  components: {TooltipIcon},

  data() {
    return {
      search: '',
      headers:[
        { text: 'Id', align: 'start', value: 'id' },
        { text: 'Path', value: 'path' },
        { text: 'Name', value: 'name' },
        { text: 'Status', value: 'status' },
        { text: '', value: 'actions', sortable: false },
      ],
      dialog: false,
      site: {
        path: '',
        name: ''
      }
    }
  },

  props: {
    sites: [],
    loading: Boolean
  },

  methods: {
    close() {
      this.site.name = ''
      this.site.path = ''
      this.dialog = false
    },

    save() {
      this.$emit('add-site', {
        path: this.site.path,
        name: this.site.name
      })
      this.site.name = ''
      this.site.path = ''
      this.dialog = false
    }
  }
}
</script>