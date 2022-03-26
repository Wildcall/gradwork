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
          Карточка с действиями
        </v-card-title>
        <v-card-actions>
          <v-spacer/>
          <v-btn @click="startIndexer"
                 text
          >
            Старт
          </v-btn>
        </v-card-actions>
      </v-card>
      <!--   Форма для редактирования   -->
      <indexer-form class="mb-5"
                    v-if="!presetList"
                    :indexer="indexer"
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
        <v-btn @click="openIndexerPresets"
               text>
          Выбрать из шаблона
        </v-btn>
      </indexer-form>
      <!--   Список всех шаблонов   -->
      <v-card v-if="presetList"
              class="mb-5">
        <v-card-title>
          Список шаблонов
          <v-spacer/>
          <v-btn @click="closeIndexerPresets"
                 text>
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
                <tr v-for="(item, i) in indexers"
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
                  <v-btn @click="changeIndexer"
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
import LoadingBar from "@/components/ui/loading-bar";
import {mapActions, mapGetters} from "vuex";
import IndexerForm from "@/components/ui/indexer-form";

export default {
  name: "SiteIndexer",

  components: {IndexerForm, LoadingBar},

  computed:{
    ...mapGetters({
      indexers: 'settings/getIndexers',
      loadingSettings: 'settings/getLoading',
      loadingSites: 'site/getLoading',
      sites: 'site/getSites'
    })
  },

  data() {
    return {
      info: false,
      infoText: '',
      editDisable: true,
      presetList: false,
      indexer: null,
      selectedPreset: null,
      site: null
    }
  },

  methods: {
    ...mapActions({
      fetchIndexers: 'settings/fetchIndexers',
      createIndexer: 'settings/createIndexer',
      updateIndexer: 'settings/updateIndexer',
      updateSite: 'site/updateSite'
    }),

    startIndexer() {
      console.log('start for site - ' + this.$route.params.id)
    },

    submitForm(item) {
      this.editDisable = true
      if (item.preset) {
        if (item.presetName.toLowerCase() === this.indexer.presetName.toLowerCase()) {
          const existIndexer = this.indexers.find(obj => obj.presetName.toLowerCase() === this.site.path.toLowerCase())
          if (existIndexer) {
            const text = 'Невозможно изменить шаблон ' + this.indexer.presetName + '. Будет изменена настройка с именем ' + this.site.path
            this.updateExistIndexer(item, existIndexer, text)
          } else {
            const text = 'Невозможно изменить шаблон ' + this.indexer.presetName + '. Будет создана новая запись с именем ' + this.site.path
            item.presetName = this.site.path.toLowerCase()
            this.createNewIndexer(item, text)
          }
        } else {
          const text = 'Настройка ' + item.presetName + ' создана и сохранена.'
          this.createNewCrawler(item, text)
        }
      } else {
        const existIndexer = this.indexers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
        if (existIndexer) {
          const text = 'Настройка ' + item.presetName + ' успешно обновлена.'
          this.updateExistIndexer(item, existIndexer, text)
        } else {
          const text = 'Настройка ' + item.presetName + ' создана и сохранена.'
          this.createNewIndexer(item, text)
        }
      }
    },

    updateExistIndexer(item, exist, infoText) {
      this.info = true
      this.infoText = infoText
      item.id = exist.id
      item.preset = exist.preset
      item.presetName = exist.presetName
      this.updateIndexer(item).then(() => {
        const indexer = this.indexers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
        this.updateSiteCrawler(indexer)
      })
    },

    createNewIndexer(item, infoText) {
      this.info = true
      this.infoText = infoText
      item.id = null
      item.preset = false
      this.createIndexer(item).then(() => {
        const indexer = this.indexers.find(obj => obj.presetName.toLowerCase() === item.presetName.toLowerCase())
        this.updateSiteIndexer(indexer)
      })
    },

    openIndexerPresets() {
      this.presetList = true
      this.selectedPreset = null
    },

    closeIndexerPresets() {
      this.presetList = false
      this.editDisable = true
      this.selectedPreset = null
    },

    openPreset(item) {
      this.selectedPreset = item
    },

    changeIndexer() {
      this.site.indexerId = this.selectedPreset.id
      this.updateSite(this.site).then(() => {
        this.indexer = this.selectedPreset
        this.closeIndexerPresets()
      })
    },

    updateSiteIndexer(indexer) {
      this.site.indexerId = indexer.id
      this.updateSite(this.site).then(() => {
        this.indexer = indexer
      })
    }
  },

  mounted() {
    const id = Number(this.$route.params.id)
    this.site = this.sites.find(obj => obj.id === id)
    const indexerId = this.site.indexerId
    if (this.indexers.length === 0) {
      this.fetchIndexers().then(() => {
        this.indexer = this.indexers.find(obj => obj.id === indexerId)
      })
    } else {
      this.indexer = this.indexers.find(obj => obj.id === indexerId)
    }
  }
}
</script>