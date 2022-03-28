<template>
  <v-container>
    <loading-bar
        v-if="loadingSettings || loadingSites"
    >
      Загрузка...
    </loading-bar>
    <v-container v-else>
      <!--   Карточка с действиями   -->
      <v-card class="mb-5">
        <v-card-title>
          <span v-if="crawlingWorking">Работа алгоритма</span>
          <span v-else>Карточка с действиями</span>
        </v-card-title>
        <v-card-actions>
          <v-spacer/>
          <v-btn @click="start"
                 text
          >
            Старт
          </v-btn>
          <v-btn @click="stop"
                 text
          >
            Стоп
          </v-btn>
        </v-card-actions>
      </v-card>
      <!--   Форма для редактирования   -->
      <crawler-form class="mb-5"
                    v-if="!presetList"
                    :crawler="crawler"
                    :disabled="editDisable"
                    v-on:submit="submitForm"
                    v-on:cancel="editDisable = true"
      >
        Текущие настройки
        <v-spacer/>
        <v-btn @click="editDisable = !editDisable"
               text>
          Редактировать
        </v-btn>
        <v-btn @click="openCrawlerPresets"
               text>
          Выбрать из шаблона
        </v-btn>
      </crawler-form>
      <!--   Список всех шаблонов   -->
      <v-card v-if="presetList"
              class="mb-5">
        <v-card-title>
          Список шаблонов
          <v-spacer/>
          <v-btn @click="closeCrawlerPresets"
                 text
          >
            Закрыть
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-row>
            <v-col>
              <v-simple-table>
                <thead>
                <tr>
                  <th>Номер</th>
                  <th>Название</th>
                  <th>Описание</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item, i) in crawlers"
                    :class="item === selectedPreset ? 'grey lighten-3' : 'white'"
                    :key="i"
                    @click="openPreset(item)">
                  <td>{{ item.id }}</td>
                  <td>{{ item.presetName }}</td>
                  <td>{{ item.description }}</td>
                </tr>
                </tbody>
              </v-simple-table>
            </v-col>
            <v-col v-if="selectedPreset">
              <v-card flat>
                <v-card-title>
                  Расширенная информация
                </v-card-title>
                <v-card-text>
                  <v-simple-table>
                    <tbody>
                    <tr v-for="(attr, i) of Object.entries(selectedPreset)"
                        :key="i"
                    >
                      <td>{{ attr[0] }}</td>
                      <td>{{ attr[1] }}</td>
                    </tr>
                    </tbody>
                  </v-simple-table>
                </v-card-text>
                <v-card-actions>
                  <v-spacer/>
                  <v-btn @click="changeCrawler"
                         :loading="loadingSites"
                         text
                  >
                    Применить
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-container>
    <v-snackbar v-model="info"
                vertical>
      {{ infoText }}
      <template v-slot:action="{ attrs }">
        <v-btn @click="info = false"
            text
            v-bind="attrs">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
import LoadingBar from "../../component/ui/loading-bar.vue";
import CrawlerForm from "../../component/ui/crawler-form.vue";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "SiteCrawler",

  components: {CrawlerForm, LoadingBar},

  computed:{
    ...mapGetters({
      loadingCrawling: 'actions/getLoadingCrawler',
      crawling: 'actions/getCrawling',
      crawlers: 'settings/getCrawlers',
      loadingSettings: 'settings/getLoading',
      loadingSites: 'site/getLoading',
      sites: 'site/getSites'
    })
  },

  data() {
    return {
      crawlingWorking: false,
      info: false,
      infoText: '',
      editDisable: true,
      presetList: false,
      crawler: null,
      selectedPreset: null,
      site: null
    }
  },

  methods: {
    ...mapActions({
      startCrawler: 'actions/startCrawler',
      stopCrawler: 'actions/stopCrawler',
      fetchCrawlers: 'settings/fetchCrawlers',
      createCrawler: 'settings/createCrawler',
      updateCrawler: 'settings/updateCrawler',
      updateSite: 'site/updateSite'
    }),

    start() {
      console.log('start for site - ' + this.$route.params.id)
      this.startCrawler(this.site).then(() => {
      })
    },

    stop() {
      console.log('stop for site - ' + this.$route.params.id)
      this.stopCrawler(this.site).then(() => {
      })
    },

    submitForm(item) {
      this.editDisable = true
      if (item.preset) {
        if (item.presetName.toLowerCase() === this.crawler.presetName.toLowerCase()) {
          const existCrawler = this.crawlers.find(obj => obj.presetName.toLowerCase() === this.site.path.toLowerCase())
          if (existCrawler) {
            const text = 'Невозможно изменить шаблон ' + this.crawler.presetName + '. Будет изменена настройка с именем ' + this.site.path
            this.updateExistCrawler(item, existCrawler, text)
          } else {
            const text = 'Невозможно изменить шаблон ' + this.crawler.presetName + '. Будет создана новая запись с именем ' + this.site.path
            item.presetName = this.site.path.toLowerCase()
            this.createNewCrawler(item, text)
          }
        } else {
          const text = 'Настройка ' + item.presetName + ' создана и сохранена.'
          this.createNewCrawler(item, text)
        }
      } else {
        const existCrawler = this.crawlers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
        if (existCrawler) {
          const text = 'Настройка ' + item.presetName + ' успешно обновлена.'
          this.updateExistCrawler(item, existCrawler, text)
        } else {
          const text = 'Настройка ' + item.presetName + ' создана и сохранена.'
          this.createNewCrawler(item, text)
        }
      }
    },

    updateExistCrawler(item, exist, infoText) {
      this.info = true
      this.infoText = infoText
      item.id = exist.id
      item.preset = exist.preset
      item.presetName = exist.presetName
      this.updateCrawler(item).then(() => {
        const crawler = this.crawlers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
        this.updateSiteCrawler(crawler)
      })
    },

    createNewCrawler(item, infoText) {
      this.info = true
      this.infoText = infoText
      item.id = null
      item.preset = false
      this.createCrawler(item).then(() => {
        const crawler = this.crawlers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
        this.updateSiteCrawler(crawler)
      })
    },

    openCrawlerPresets() {
      this.presetList = true
      this.selectedPreset = null
    },

    closeCrawlerPresets() {
      this.presetList = false
      this.editDisable = true
      this.selectedPreset = null
    },

    openPreset(item) {
      this.selectedPreset = item
    },

    changeCrawler() {
      this.site.crawlerId = this.selectedPreset.id
      this.updateSite(this.site).then(() => {
        this.crawler = this.selectedPreset
        this.closeCrawlerPresets()
      })
    },

    updateSiteCrawler(crawler) {
      this.site.crawlerId = crawler.id
      this.updateSite(this.site).then(() => {
        this.crawler = crawler
      })
    }
  },

  watch: {
    crawling: {
      handler(newValue, oldValue) {
        console.log(newValue + ' ' + oldValue)
      },
      deep: true
    },
  },

  mounted() {
    const id = Number(this.$route.params.id)
    this.site = this.sites.find(obj => obj.id === id)
    const crawlerId = this.site.crawlerId
    if (this.crawlers.length === 0) {
      this.fetchCrawlers().then(() => {
        this.crawler = this.crawlers.find(obj => obj.id === crawlerId)
      })
    } else {
      this.crawler = this.crawlers.find(obj => obj.id === crawlerId)
    }
  }
}
</script>