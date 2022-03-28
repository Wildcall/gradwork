<template>
  <v-card class="ma-5" elevation="5">
    <v-toolbar flat>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          flat
          label="Поиск"
          hide-details
          solo-inverted
      />
      <v-spacer/>
      <v-btn @click="createFrom"
             text
      >
        Добавить шаблон
      </v-btn>
    </v-toolbar>
    <v-card-text>
      <v-row>
        <v-col>
          <v-data-table
                :headers="headers"
                :items="crawlers"
                :loading="loading"
                :search="search"
                item-key="id"
            >
              <template v-slot:item="{ item }">
                <tr :class="item.id === selected ? 'grey lighten-3' : 'white'"
                    @click="editForm(item)">
                  <td>{{ item.id }}</td>
                  <td>{{ item.presetName }}</td>
                  <td>{{ item.description }}</td>
                  <td>
                    <v-simple-checkbox
                        v-model="item.preset"
                        disabled
                    />
                  </td>
                  <td>{{ item.siteCount }}</td>
                </tr>
              </template>
            </v-data-table>
        </v-col>
        <v-col class="d-flex align-stretch">
          <crawler-form v-show="showEditForm"
                        class="flex-grow-1 flex-shrink-0"
                        :disabled="false"
                        :submit-btn-text="submitBtnText"
                        :delete-btn="showDeleteBtn"
                        :crawler="this.crawler"
                        v-on:submit="submitForm"
                        v-on:cancel="closeForm"
                        v-on:remove="confirmRemove = true"
          >
            Текущая настройка
          </crawler-form>
          <v-card v-show="!showEditForm"
                  class="flex-grow-1 flex-shrink-0">
            <v-card-title>
              Редактирование шаблона
            </v-card-title>
            <v-card-text>
              Выберите шаблон для редактирования или создайте новый
            </v-card-text>
          </v-card>
          <v-dialog v-model="confirmRemove"
                    width="500"
          >
            <v-card>
              <v-card-title>
                Подтверждение удаления
              </v-card-title>
              <v-card-text>
                Удаление шаблона настроек поискового алгоритма. В случае если шаблон используется сайтом, он не может быть удален. Уверены что хотите удалить шаблон?
              </v-card-text>
              <v-divider/>
              <v-card-actions>
                <v-spacer/>
                <v-btn @click="confirmRemove = false"
                       text
                >
                  Отмена
                </v-btn>
                <v-btn @click="remove"
                       :loading="loading"
                       text
                >
                  Удалить
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-snackbar
              color="red lighten-2"
              v-model="warningSnackbar"
              :timeout="warningTimeout"
          >
            {{ warningMessage }}
            <template v-slot:action="{ attrs }">
              <v-btn text
                     v-bind="attrs"
                     @click="warningSnackbar = false"
              >
                Закрыть
              </v-btn>
            </template>
          </v-snackbar>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import CrawlerForm from "../component/ui/crawler-form.vue";

export default {
  name: "Crawler",

  components: {CrawlerForm},

  data() {
    return {
      search: '',
      headers:[
        { text: 'Номер', align: 'start', value: 'id' },
        { text: 'Название', value: 'presetName' },
        { text: 'Описание', value: 'description' },
        { text: 'Шаблон', value: 'preset' },
        { text: 'Сайты', value: 'siteCount' },
      ],
      selected: null,

      showEditForm: false,
      crawler: null,

      confirmRemove: false,
      showDeleteBtn: false,
      submitBtnText: '',

      warningSnackbar: false,
      warningMessage: '',
      warningTimeout: 4000,
    }
  },

  computed: {
    ...mapGetters({
      crawlers: 'settings/getCrawlers',
      loading: 'settings/getLoading',
    })
  },

  methods: {
    ...mapActions({
      fetchCrawlers: 'settings/fetchCrawlers',
      deleteCrawler: 'settings/deleteCrawler',
      createCrawler: 'settings/createCrawler',
      updateCrawler: 'settings/updateCrawler',
    }),

    submitForm(item) {
      if (item) {
        if (item.id) {
          if (this.crawler.presetName === 'default') {
            item.presetName = this.crawler.presetName
          }
          this.updateCrawler(item).then(() => {
            this.crawler = this.crawlers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
          })
        } else {
          item.preset = true
          this.createCrawler(item).then(() => {
            this.crawler = this.crawlers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
          })
        }
      }
    },

    remove() {
      if (this.crawler.id === 1) {
        this.warningMessage = 'Невозможно удалить шаблон по умолчанию'
        this.warningSnackbar = true
        this.confirmRemove = false
        return
      }

      if (this.crawler.siteCount === 0) {
        this.deleteCrawler(this.crawler.id).then(() => {
          this.closeForm()
        })
      } else {
        this.warningMessage = 'Невозможно удалить шаблон который используется в качестве настроек'
        this.confirmRemove = false
        this.warningSnackbar = true
      }
    },

    createFrom() {
      this.selected = null
      this.crawler = {}
      this.showEditForm = true
      this.showDeleteBtn = false
      this.submitBtnText = 'Сохранить'

      const tmp = this.crawlers.find(obj => obj.presetName === 'default');
      this.crawler.id = null
      this.crawler.presetName = ''
      this.crawler.description = ''
      this.crawler.parallelism = tmp.parallelism
      this.crawler.timeout = tmp.timeout
      this.crawler.delay = tmp.delay
      this.crawler.reconnect = tmp.reconnect
      this.crawler.referrer = tmp.referrer
      this.crawler.userAgent = tmp.userAgent
    },

    editForm(item) {
      this.crawler = item
      this.selected = item.id
      this.showDeleteBtn = true
      this.submitBtnText = 'Обновить'
      this.showEditForm = true
    },

    closeForm() {
      this.confirmRemove = false
      this.selected = null
      this.crawler = null
      this.showEditForm = false
    },
  },

  mounted() {
    if (this.crawlers.length === 0){
      this.fetchCrawlers().then(() => {
      })
    }
  }
}
</script>